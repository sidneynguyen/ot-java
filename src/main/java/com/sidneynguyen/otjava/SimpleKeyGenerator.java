package com.sidneynguyen.otjava;

public class SimpleKeyGenerator extends KeyGenerator {

	private static final int START_VAL = 100;
	
	private Integer key;
	
	public SimpleKeyGenerator() {
		key = START_VAL;
	}
	
	@Override
	String generateKey() {
		return (key++).toString();
	}
	
}
