package com.sidneynguyen.otjava;

import java.util.HashMap;
import java.util.Map;

public class Graph {
	private KeyGenerator keyGenerator;
	private Map<String, Node> nodeMap;
	private Node curr;
	
	public Graph(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
		nodeMap = new HashMap<String, Node>();
		curr = new Node(this.keyGenerator.generateKey());
		nodeMap.put(curr.getKey(), curr);
	}
	
	public void insertLeft(Operation operation) {
		Node node = new Node(keyGenerator.generateKey());
		curr.setLeftChild(node);
		curr.setLeftOperation(operation);
		node.setRightParent(curr);
		curr = node;
		// TODO
	}
	
	public void insertRight(String parentKey, Operation operation) {
		if (!nodeMap.containsKey(parentKey)) {
			// TODO
		}
		Node parentNode = nodeMap.get(parentKey);
		parentNode.setRightOperation(operation);
		Node childNode = new Node(keyGenerator.generateKey());
		parentNode.setRightChild(childNode);
		childNode.setLeftParent(parentNode);
		
		if (parentNode == curr) {
			// TODO
		}

		Node working = parentNode;
		while (working.getLeftChild() != curr) {
			Pair pair = Transformer.transform(working.getLeftOperation(), working.getRightOperation());
			Operation leftPrime = pair.getLeftPrime();
			Operation rightPrime = pair.getRightPrime();
			Node combined = new Node(keyGenerator.generateKey());
			Node left = working.getLeftChild();
			left.setRightChild(combined);
			left.setRightOperation(rightPrime);
			Node right = working.getRightChild();
			right.setLeftChild(combined);
			right.setLeftOperation(leftPrime);
			combined.setLeftParent(left);
			combined.setRightParent(right);
			
			working = working.getLeftChild();
		}
	}
}
