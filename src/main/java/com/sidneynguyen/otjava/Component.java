package com.sidneynguyen.otjava;

public class Component {
	private Type type;
	private int length;
	private Data data;
	
	public Component(Type type, int length, Data data) {
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
	
	public Data getData() {
		return data;
	}

	public enum Type {
		RETAIN,
		INSERT,
		DELETE
	}
}
