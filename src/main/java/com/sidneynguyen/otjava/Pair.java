package com.sidneynguyen.otjava;

public class Pair {
	private Operation leftPrime;
	private Operation rightPrime;
	
	public Pair(Operation leftPrime, Operation rightPrime) {
		this.leftPrime = leftPrime;
		this.rightPrime = rightPrime;
	}
	
	public Operation getLeftPrime() {
		return leftPrime;
	}
	
	public Operation getRightPrime() {
		return rightPrime;
	}
}
