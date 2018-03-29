package com.sidneynguyen.otjava;

public class Component {

	private Type type;
	private int length;
	private String data;
	
	public Component(Type type, int length, String data) {
		this.type = type;
		this.length = length;
		this.data = data;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getLength() {
		return length;
	}
	
	public String getData() {
		return data;
	}

	public enum Type {
		RETAIN,
		INSERT,
		DELETE
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Component)) {
            return false;
        }
        Component other = (Component) obj;
        return type == other.type && length == other.length
                && (type != Type.INSERT || data.equals(other.data));
    }
}
