package com.sidneynguyen.otjava;

public class SimpleKeyGenerator extends KeyGenerator {

	private Integer key;
	
	public SimpleKeyGenerator() {
		key = 0;
	}
	
	@Override
	String generateKey() {
		return (key++).toString();
	}
	
}
