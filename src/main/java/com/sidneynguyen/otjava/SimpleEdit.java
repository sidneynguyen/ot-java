package com.sidneynguyen.otjava;

public class SimpleEdit extends Edit {

	private String data;
	
	public SimpleEdit(String data) {
		this.data = data;
	}
	
	@Override
	public String getEdit() {
		return data;
	}
}
