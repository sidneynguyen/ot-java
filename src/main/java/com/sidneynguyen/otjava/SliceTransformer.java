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
        List<Component> leftPrime = new ArrayList<Component>();
        List<Component> rightPrime = new ArrayList<Component>();
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
                            /* (R1,  R1) => (R1, R1) */
                            leftPrime.add(new Component(RETAIN, 1, null));
                            rightPrime.add(new Component(RETAIN, 1, null));
                            leftProceed = true;
                            rightProceed = true;
                            break;
                        }
                        case INSERT: {
                            /* (R1,  I(x)) => (I(x), R1 + cursor stays) */
                            leftPrime.add(new Component(INSERT, 1,
                                    new SimpleEdit(String.valueOf(rightComp.getData().getEdit().charAt(0)))));
                            rightPrime.add(new Component(RETAIN, 1, null));
                            rightProceed = true;
                            break;
                        }
                        case DELETE: {
                            /* (R1,  D1) => (D1, nop) */
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
                            /* (I(x), R1) => (R1 + cursor stays, I(x)) */
                            leftPrime.add(new Component(RETAIN, 1, null));
                            rightPrime.add(new Component(INSERT, 1,
                                    new SimpleEdit(String.valueOf(leftComp.getData().getEdit().charAt(0)))));
                            leftProceed = true;
                            break;
                        }
                        case INSERT: {
                            /* (I(x),  I(y)) => x < y ? (R1I(y), I(x)R1) : (I(y)R1, R1I(x)) */
                            char leftChar = leftComp.getData().getEdit().charAt(1);
                            char rightChar = rightComp.getData().getEdit().charAt(1);
                            if(leftChar < rightChar)
                            {
                                leftPrime.add(new Component(RETAIN, 1, null));
                                leftPrime.add(new Component(INSERT, 1,
                                        new SimpleEdit(String.valueOf(rightComp.getData().getEdit().charAt(0)))));
                                rightPrime.add(new Component(INSERT, 1,
                                        new SimpleEdit(String.valueOf(leftComp.getData().getEdit().charAt(0)))));
                                rightPrime.add(new Component(RETAIN, 1, null));
                            }
                            else
                            {
                                leftPrime.add(new Component(INSERT, 1,
                                        new SimpleEdit(String.valueOf(rightComp.getData().getEdit().charAt(0)))));
                                leftPrime.add(new Component(RETAIN, 1, null));
                                rightPrime.add(new Component(RETAIN, 1, null));
                                rightPrime.add(new Component(INSERT, 1,
                                        new SimpleEdit(String.valueOf(leftComp.getData().getEdit().charAt(0)))));
                            }
                            leftProceed = true;
                            rightProceed = true;
                            break;
                        }
                        case DELETE: {
                            /* (I(x), D1) => (R1 + cursor stays, I(x)) */
                            leftPrime.add(new Component(RETAIN, 1, null));
                            rightPrime.add(new Component(INSERT, 1,
                                    new SimpleEdit(String.valueOf(leftComp.getData().getEdit().charAt(0)))));
                            leftProceed = true;
                            break;
                        }
                    }
                    break;
                }
                case DELETE: {
                    switch(rightComp.getType()) {
                        case RETAIN: {
                            /* (D(x), R1) => (nop, D(x)) */
                            rightPrime.add(new Component(DELETE, 1, null));
                            leftProceed = true;
                            rightProceed = true;
                            break;
                        }
                        case INSERT: {
                            /* (D1, I(x)) => (I(x), R1 + cursor stays) */
                            rightPrime.add(new Component(RETAIN, 1, null));
                            leftPrime.add(new Component(INSERT, 1,
                                    new SimpleEdit(String.valueOf(rightComp.getData().getEdit().charAt(0)))));
                            rightProceed = true;
                            break;
                        }
                        case DELETE: {
                            /* (D1, D1) => (nop, nop) */
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
                rightPrime.add(new Component(INSERT, 1, new SimpleEdit(String.valueOf(leftComp.getData().getEdit().charAt(0)))));
            }
        }
        while(rightIt.hasNext()) {
            rightComp = (Component) rightIt.next();
            if(rightComp.getType() == INSERT) {
                rightPrime.add(new Component(RETAIN, 1, null));
                leftPrime.add(new Component(INSERT, 1, new SimpleEdit(String.valueOf(rightComp.getData().getEdit().charAt(0)))));
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
                currentData = currentComponent.getData().getEdit();
            }
            for(i = 0; i < componentLength; ++i) {
                switch(currentType) {
                    case RETAIN: {
                        newList.add(new Component(RETAIN, 1, null));
                        break;
                    }
                    case INSERT: {
                        newList.add(new Component(INSERT, 1, new SimpleEdit(String.valueOf(currentData.charAt(i)))));
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
