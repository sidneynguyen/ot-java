package com.sidneynguyen.otjava;

import java.util.HashMap;
import java.util.Map;

public class Graph {
	private Map<String, Node> nodeMap;
	private Transformer transformer;
	private KeyGenerator keyGenerator;
	private Node oldLeft;
	private Node currLeft;
	private Node oldRight;
	private Node currRight;
	
	public Graph(String rootKey, Transformer transformer, KeyGenerator keyGenerator) {
		nodeMap = new HashMap<String, Node>();
		Node rootNode = new Node(rootKey);
		nodeMap.put(rootKey, rootNode);
		oldLeft = rootNode;
		currLeft = rootNode;
		oldRight = rootNode;
		currRight = rootNode;
		
		this.transformer = transformer;
		this.keyGenerator = keyGenerator;
	}
	
	public void insertLeft(String key, String parentKey, Operation operation) {
		if (nodeMap.containsKey(key)) {
			// TODO
		}
		
		if (!nodeMap.containsKey(parentKey)) {
			// TODO
		}
		
		Node parentNode = nodeMap.get(parentKey);
		if (parentNode.getLeftChild() != null || parentNode.getLeftOperation() != null) {
			//TODO
		}
		
		Node node = new Node(key);
		// TODO
	}
	
	public void insertRight(String key, String parentKey, Operation operation) {
		if (nodeMap.containsKey(key)) {
			// TODO
		}
		
		if (!nodeMap.containsKey(parentKey)) {
			// TODO
		}
		
		Node parentNode = nodeMap.get(parentKey);
		if (parentNode.getRightChild() != null || parentNode.getRightOperation() != null) {
			//TODO
		}
		
		Node node = new Node(key);
		node.setLeftParent(parentNode);
		parentNode.setRightChild(node);
		parentNode.setRightOperation(operation);

		Node working = parentNode;
		while (parentNode != null) {
			Pair pair = transformer.transform(working.getLeftOperation(), working.getRightOperation());
			Operation leftPrime = pair.getLeftPrime();
			Operation rightPrime = pair.getRightPrime();
			
			Node converged = new Node(keyGenerator.generateKey());
			Node left = working.getLeftChild();
			Node right = working.getRightChild();
			converged.setLeftParent(left);
			converged.setRightParent(right);
			left.setRightChild(converged);
			left.setRightOperation(rightPrime);
			right.setLeftChild(converged);
			right.setLeftOperation(leftPrime);
			
			working = working.getLeftChild();
		}
	}
	
	public Operation applyRight() {
		return null;
	}
}
