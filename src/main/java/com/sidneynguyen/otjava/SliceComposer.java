package com.sidneynguyen.otjava;

import java.util.Iterator;
import java.util.List;

public class SliceComposer extends Composer {

    public Operation compose(List<Operation> operationList) {
        Operation result = operationList.get(0);
        for (int i = 1; i < operationList.size(); i++) {
            result = subCompose(result, operationList.get(i));
        }
        return result;
    }

    private Operation subCompose(Operation firstOp, Operation secondOp) {
        // slice components
        Operation firstSlice = Operation.sliceOperation(firstOp);
        Operation secondSlice = Operation.sliceOperation(secondOp);

        Iterator<Component> firstIt = firstSlice.iterator();
        Iterator<Component> secondIt = secondSlice.iterator();
        Component firstComp = null;
        Component secondComp = null;
        boolean firstProceed = true;
        boolean secondProceed = true;
        Operation result = new Operation();
        while (firstIt.hasNext() && secondIt.hasNext()) {
            if (firstProceed) {
                firstComp = (Component)firstIt.next();
                firstProceed = false;
            }
            if (secondProceed) {
                secondComp = (Component)secondIt.next();
                secondProceed = false;
            }
            switch (firstComp.getType()) {
                case RETAIN:
                    switch (secondComp.getType()) {
                        case RETAIN:
                            result.add(firstComp);
                            firstProceed = secondProceed = true;
                            break;
                        case INSERT:
                            result.add(secondComp);
                            firstProceed = secondProceed = true;
                            break;
                        case DELETE:
                            result.add(secondComp);
                            firstProceed = secondProceed = true;
                            break;
                    }
                    break;
                case INSERT:
                    switch (secondComp.getType()) {
                        case RETAIN:
                            result.add(firstComp);
                            firstProceed = secondProceed = true;
                            break;
                        case INSERT:
                            result.add(secondComp);
                            secondProceed = true;
                            break;
                        case DELETE:
                            firstProceed = secondProceed = true;
                            break;
                    }
                    break;
                case DELETE:
                    switch (secondComp.getType()) {
                        case RETAIN:
                            result.add(firstComp);
                            firstProceed = true;
                            break;
                        case INSERT:
                            result.add(firstComp);
                            firstProceed = true;
                            break;
                        case DELETE:
                            result.add(firstComp);
                            result.add(secondComp);
                            firstProceed = secondProceed = true;
                            break;
                    }
                    break;
            }
        }

        // add leftover components
        while (firstIt.hasNext()) {
            result.add((Component)firstIt.next());
        }
        while (secondIt.hasNext()) {
            result.add((Component)secondIt.next());
        }
        return result;
    }
}
