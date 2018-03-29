package com.sidneynguyen.otjava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.sidneynguyen.otjava.Component.Type.DELETE;
import static com.sidneynguyen.otjava.Component.Type.INSERT;
import static com.sidneynguyen.otjava.Component.Type.RETAIN;

public class Operation implements Iterable<Component> {

	private List<Component> componentList;
	
	public Operation(List<Component> componentList) {
		this.componentList = componentList;
	}
	
	public Operation() {
		componentList = new ArrayList<>();
	}

    public void add(Operation operation) {
        componentList.addAll(operation.getComponentList());
    }

    public void add(Component component) {
        componentList.add(component);
    }

    public Component get(int index) {
        return componentList.get(index);
    }

	public int size() {
        return componentList.size();
    }

	public List<Component> getComponentList() {
		return componentList;
	}

    @Override
    public boolean equals(Object obj) {
	    if (obj == null || !(obj instanceof Operation)) {
            return false;
        }
        Operation self = sliceOperation(this);
        Operation other = sliceOperation((Operation) obj);

        if (self.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < self.size(); i++) {
            if (!self.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static Operation sliceOperation(Operation operation) {
        List<Component> newList = new ArrayList<>();
        for (Component currentComponent : operation) {
            int componentLength = currentComponent.getLength();
            Component.Type currentType = currentComponent.getType();
            String currentData = null;
            if (currentType == INSERT) {
                currentData = currentComponent.getData().getEdit();
            }
            for (int i = 0; i < componentLength; i++) {
                switch (currentType) {
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

    public static String applyOperation(String document, Operation operation) {
        int i = 0;
        StringBuffer newDoc = new StringBuffer();
        for (Component component : operation) {
            switch (component.getType()) {
                case RETAIN:
                    newDoc.append(document.substring(i, component.getLength()));
                    i += component.getLength();
                    break;
                case INSERT:
                    newDoc.append(component.getData().getEdit());
                    break;
                case DELETE:
                    i += component.getLength();
                    break;
            }
        }
        return newDoc.toString();
    }

    @Override
    public Iterator<Component> iterator() {
        return new OperationIterator();
    }

    public class OperationIterator implements Iterator<Component> {

        int index;

        OperationIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < componentList.size();
        }

        @Override
        public Component next() {
            return componentList.get(index++);
        }
    }
}
