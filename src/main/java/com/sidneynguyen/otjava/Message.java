package com.sidneynguyen.otjava;

public class Message {
    
    private String key;
    private String parentKey;
    private Operation operation;

    public Message(String key, String parentKey, Operation operation) {
        this.key = key;
        this.parentKey = parentKey;
        this.operation = operation;
    }

	public String getKey() {
		return key;
    }
    
    public String getParentKey() {
        return parentKey;
    }

	public Operation getOperation() {
		return operation;
    }
}