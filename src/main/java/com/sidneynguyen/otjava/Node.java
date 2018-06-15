package com.sidneynguyen.otjava;

public class Node {

	private String key;
	private Node leftParent;
	private Node rightParent;
	private Node leftChild;
	private Node rightChild;
	private Operation leftOperation;
	private Operation rightOperation;
	
	public Node(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Node getLeftParent() {
		return leftParent;
	}

	public void setLeftParent(Node leftParent) {
		this.leftParent = leftParent;
	}

	public Node getRightParent() {
		return rightParent;
	}

	public void setRightParent(Node rightParent) {
		this.rightParent = rightParent;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public Operation getLeftOperation() {
		return leftOperation;
	}

	public void setLeftOperation(Operation leftOperation) {
		this.leftOperation = leftOperation;
	}

	public Operation getRightOperation() {
		return rightOperation;
	}

	public void setRightOperation(Operation rightOperation) {
		this.rightOperation = rightOperation;
	}
}
