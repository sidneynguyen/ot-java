package com.sidneynguyen.otjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private Map<String, Node> nodeMap;
	private Transformer transformer;
	private Composer composer;
	private KeyGenerator keyGenerator;
	private Node oldLeft;
	private Node currLeft;
	private Node oldRight;
	private Node currRight;
	private Node rootNode;
	
	public Graph(String rootKey, Transformer transformer, Composer composer, KeyGenerator keyGenerator) {
		nodeMap = new HashMap<>();
		rootNode = new Node(rootKey);
		nodeMap.put(rootKey, rootNode);
		oldLeft = rootNode;
		currLeft = rootNode;
		oldRight = rootNode;
		currRight = rootNode;
		
		this.transformer = transformer;
		this.composer = composer;
		this.keyGenerator = keyGenerator;
	}
	
	public void insertLeft(String key, String parentKey, Operation operation) {
		Node parentNode;
		if (currLeft.getKey().equals(parentKey)) {
			parentNode = currLeft;
		} else if (nodeMap.containsKey(parentKey)) {
			parentNode = nodeMap.get(parentKey);
		} else {
			// TODO
			return;
		}
		
		if (parentNode.getLeftChild() != null || parentNode.getLeftOperation() != null) {
			//TODO
		}
		
		Node node = new Node(key);
		node.setRightParent(parentNode);
		parentNode.setLeftChild(node);
		parentNode.setLeftOperation(operation);
		
		currLeft = node;
	}
	
	public void insertRight(String key, String parentKey, Operation operation) {
		Node parentNode;
		if (currRight.getKey().equals(parentKey)) {
			parentNode = currRight;
		} else if (nodeMap.containsKey(parentKey)) {
			parentNode = nodeMap.get(parentKey);
		} else {
			// TODO
			return;
		}
		
		if (parentNode.getRightChild() != null || parentNode.getRightOperation() != null) {
			//TODO
		}
		
		Node node = new Node(key);
		node.setLeftParent(parentNode);
		parentNode.setRightChild(node);
		parentNode.setRightOperation(operation);

		currRight = node;
	}

	public Pair applyRight() {
		List<Operation> rightOperationList = new ArrayList<>();
		Node rightWorking = oldRight;
		while (rightWorking != currRight) {
			rightOperationList.add(rightWorking.getRightOperation());
			rightWorking = rightWorking.getRightChild();
		}
		Operation leftPrime = null;
		Operation rightPrime = composer.compose(rightOperationList);
		Node leftWorking = oldRight;
		List<Operation> leftOperationList = new ArrayList<>();
		while (leftWorking.getLeftChild() != null) {
			Pair workingPair = transformer.transform(leftWorking.getLeftOperation(), rightPrime);
			leftPrime = workingPair.getLeftPrime();
			rightPrime = workingPair.getRightPrime();
			leftOperationList.add(leftPrime);
			
			Node converged = new Node(keyGenerator.generateKey());
			converged.setRightParent(rightWorking);
			rightWorking.setLeftChild(converged);
			rightWorking.setLeftOperation(leftPrime);
			
			leftWorking = leftWorking.getLeftChild();
			rightWorking = converged;
		}
		oldRight = currRight;
		
		Pair resultPair = new Pair(leftPrime, composer.compose(leftOperationList));
		
		return resultPair;
	}
	
	@Override
	public String toString() {
		ArrayList<ArrayList<String>> cols = new ArrayList<>();
		Node right = rootNode;
		while (right != null) {
		    ArrayList<String> col = new ArrayList<>();
			Node left = right;
			while (left != null) {
			    col.add(left.getKey());
				left = left.getLeftChild();
			}
			right = right.getRightChild();
			cols.add(col);
		}
        StringBuffer output = new StringBuffer();
		for (int i = 0; i < cols.get(0).size(); i++) {
		    for (int j = 0; j < cols.size(); j++) {
		        ArrayList<String> col = cols.get(j);
		        if (col.size() >= i) {
		            output.append(col.get(i)).append('\t');
                }
            }
            output.append('\n');
        }
		return output.toString();
	}
}
