package com.sidneynguyen.otjava;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.sidneynguyen.otjava.Component.Type.*;

public class SliceTransformer extends Transformer {

	@Override
	Pair transform(Operation leftOperation, Operation rightOperation) {
        Operation slicedLeft = slice(leftOperation);
        Operation slicedRight = slice(rightOperation);
        Iterator leftIt = slicedLeft.getComponentList().iterator();
        Iterator rightIt = slicedRight.getComponentList().iterator();
        Component leftComp = null;
        Component rightComp = null;
        List<Component> leftPrime = new ArrayList<>();
        List<Component> rightPrime = new ArrayList<>();
        boolean leftProceed = true;
        boolean rightProceed = true;
        while(leftIt.hasNext() && rightIt.hasNext())
        {
            if(leftProceed){
                leftComp = (Component) leftIt.next();
                leftProceed = false;
            }
            if(rightProceed) {
                rightComp = (Component) rightIt.next();
                rightProceed = false;
            }
            switch(leftComp.getType()) {
                case RETAIN: {
                    switch(rightComp.getType()) {
                        case RETAIN: {
                            // (R, R) => (R, R) -> (->, ->)
                            leftPrime.add(new Component(RETAIN, 1, null));
                            rightPrime.add(new Component(RETAIN, 1, null));
                            leftProceed = true;
                            rightProceed = true;
                            break;
                        }
                        case INSERT: {
                            // (R, I(x)) => (I(x), R) -> (-, ->)
                            leftPrime.add(new Component(INSERT, 1, String.valueOf(rightComp.getData().charAt(0))));
                            rightPrime.add(new Component(RETAIN, 1, null));
                            rightProceed = true;
                            break;
                        }
                        case DELETE: {
                            // (R, D) => (D, nop) -> (->, ->)
                            leftPrime.add(new Component(DELETE, 1, null));
                            leftProceed = true;
                            rightProceed = true;
                            break;
                        }
                    }
                    break;
                }
                case INSERT: {
                    switch(rightComp.getType()) {
                        case RETAIN: {
                            // (I(x), R) => (R, I(x)) -> (->, -)
                            leftPrime.add(new Component(RETAIN, 1, null));
                            rightPrime.add(new Component(INSERT, 1, String.valueOf(leftComp.getData().charAt(0))));
                            leftProceed = true;
                            break;
                        }
                        case INSERT: {
                            char leftChar = leftComp.getData().charAt(0);
                            char rightChar = rightComp.getData().charAt(0);
                            if(leftChar < rightChar)
                            {
                                // (I(a), I(b)) => (R, I(a)) -> (->, -)
                                leftPrime.add(new Component(RETAIN, 1, null));
                                rightPrime.add(new Component(INSERT, 1, leftComp.getData().substring(0, 1)));
                                leftProceed = true;
                                if (!leftIt.hasNext() || !rightIt.hasNext()) {
                                    leftPrime.add(new Component(INSERT, 1, rightComp.getData().substring(0, 1)));
                                    rightPrime.add(new Component(RETAIN, 1, null));
                                }
                            }
                            else
                            {
                                // (I(b), I(a)) => (I(a), R) -> (-, ->)
                                leftPrime.add(new Component(INSERT, 1, rightComp.getData().substring(0, 1)));
                                rightPrime.add(new Component(RETAIN, 1, null));
                                rightProceed = true;
                                if (!leftIt.hasNext() || !rightIt.hasNext()) {
                                    leftPrime.add(new Component(RETAIN, 1, null));
                                    rightPrime.add(new Component(INSERT, 1, leftComp.getData().substring(0, 1)));
                                }
                            }
                            break;
                        }
                        case DELETE: {
                            // (I(x), D) => (R, I(x)) -> (->, -)
                            leftPrime.add(new Component(RETAIN, 1, null));
                            rightPrime.add(new Component(INSERT, 1, String.valueOf(leftComp.getData().charAt(0))));
                            leftProceed = true;
                            break;
                        }
                    }
                    break;
                }
                case DELETE: {
                    switch(rightComp.getType()) {
                        case RETAIN: {
                            // (D, R) => (nop, D) -> (->, ->)
                            rightPrime.add(new Component(DELETE, 1, null));
                            leftProceed = true;
                            rightProceed = true;
                            break;
                        }
                        case INSERT: {
                            // (D, I(x)) => (I(x), R) -> (-, ->)
                            leftPrime.add(new Component(INSERT, 1, String.valueOf(rightComp.getData().charAt(0))));
                            rightPrime.add(new Component(RETAIN, 1, null));
                            rightProceed = true;
                            break;
                        }
                        case DELETE: {
                            // (D, D) => (nop, nop) -> (->, ->)
                            leftProceed = true;
                            rightProceed = true;
                            break;
                        }
                    }
                    break;
                }
            }
        }

        /* If either of the operations end with insert operations, then flush the inserts into the other */
        while(leftIt.hasNext()) {
            leftComp = (Component) leftIt.next();
            if(leftComp.getType() == INSERT) {
                leftPrime.add(new Component(RETAIN, 1, null));
                rightPrime.add(new Component(INSERT, 1, String.valueOf(leftComp.getData().charAt(0))));
            }
        }
        while(rightIt.hasNext()) {
            rightComp = (Component) rightIt.next();
            if(rightComp.getType() == INSERT) {
                rightPrime.add(new Component(RETAIN, 1, null));
                leftPrime.add(new Component(INSERT, 1, String.valueOf(rightComp.getData().charAt(0))));
            }
        }
        return new Pair(new Operation(leftPrime), new Operation(rightPrime));
	}

    private Operation slice(Operation op) {
        List<Component> newList = new ArrayList<Component>();
        int i;
        Iterator it = op.getComponentList().iterator();
        Component currentComponent;
        int componentLength;
        com.sidneynguyen.otjava.Component.Type currentType;
        String currentData = null;
        while(it.hasNext()) {
            currentComponent = (Component) it.next();
            componentLength = currentComponent.getLength();
            currentType = currentComponent.getType();
            if(currentComponent.getData() != null) {
                currentData = currentComponent.getData();
            }
            for(i = 0; i < componentLength; ++i) {
                switch(currentType) {
                    case RETAIN: {
                        newList.add(new Component(RETAIN, 1, null));
                        break;
                    }
                    case INSERT: {
                        newList.add(new Component(INSERT, 1, String.valueOf(currentData.charAt(i))));
                        break;
                    }
                    case DELETE: {
                        newList.add(new Component(DELETE, 1, null));
                        break;
                    }
                }
            }
        }
        return new Operation(newList);
    }

}
