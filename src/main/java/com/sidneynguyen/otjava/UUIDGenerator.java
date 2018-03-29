package com.sidneynguyen.otjava;

import java.util.UUID;

public class UUIDGenerator extends KeyGenerator {

	@Override
	String generateKey() {
		return UUID.randomUUID().toString();
	}
}