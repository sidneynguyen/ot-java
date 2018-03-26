package com.sidneynguyen.otjava;

public class Component {
	private Type type;
	private int length;
	private Edit data;
	
	public Component(Type type, int length, Edit data) {
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
	
	public Edit getData() {
		return data;
	}

	public enum Type {
		RETAIN,
		INSERT,
		DELETE
	}
}
