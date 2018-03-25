package com.sidneynguyen.otjava;

import junit.framework.TestCase;

public class SimpleKeyGeneratorTest extends TestCase {
	public void test100KeyGenerations() {
		KeyGenerator generator = new SimpleKeyGenerator();
		for (Integer i = 0; i < 100; i++) {
			assertEquals(i.toString(), generator.generateKey());
		}
	}
}
