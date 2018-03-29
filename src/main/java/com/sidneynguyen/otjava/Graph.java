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
    private Node rootNode;
    private Node curr;
	
	public Graph(String rootKey, Transformer transformer, Composer composer, KeyGenerator keyGenerator) {
		nodeMap = new HashMap<>();
		rootNode = new Node(rootKey);
        nodeMap.put(rootKey, rootNode);
        this.curr = rootNode;
		
		this.transformer = transformer;
		this.composer = composer;
		this.keyGenerator = keyGenerator;
    }
    
    public Operation generateOperation() {
        List<Operation> operationList = new ArrayList<>();
        Node working = curr;
        while (working.getLeftChild() != null) {
            operationList.add(working.getLeftOperation());
            working = working.getLeftChild();
        }
        return composer.compose(operationList);
    }
	
	public void insertLeft(String key, String parentKey, Operation operation) {
        if (!nodeMap.containsKey(parentKey)) {
            throw new RuntimeException("parentKey not found");
        }
        Node parentNode = nodeMap.get(parentKey);
        if (parentNode.getRightChild() != null) {
            throw new RuntimeException("operation outdated");
        }

        // insert new node
        Node node = new Node(key);
        nodeMap.put(key, node);
        node.setRightParent(parentNode);
        parentNode.setLeftChild(node);
        parentNode.setLeftOperation(operation);
	}

	public Operation insertRight(String key, String parentKey, Operation operation) {
        if (nodeMap.containsKey(key)) {
            // update curr
            Node working = nodeMap.get(key);
            while (working.getRightChild() != null) {
                working = working.getRightChild();
            }
            curr = working;
            return null;
        }

        if (!nodeMap.containsKey(parentKey)) {
            throw new RuntimeException("parentKey not found");
        }
        Node parentNode = nodeMap.get(parentKey);
        if (parentNode.getRightChild() != null) {
            throw new RuntimeException("operation outdated");
        }

        // insert new node
        Node node = new Node(key);
        nodeMap.put(key, node);
        node.setLeftParent(parentNode);
        parentNode.setRightChild(node);
        parentNode.setRightOperation(operation);
        curr = node;

        // generate intermediate nodes
        Node working = parentNode;
        while (working.getLeftChild() != null) {
            Pair pair = transformer.transform(working.getLeftOperation(), working.getRightOperation());
            Operation leftPrime = pair.getLeftPrime();
            Operation rightPrime = pair.getRightPrime();

            Node converged = new Node(keyGenerator.generateKey());
            nodeMap.put(converged.getKey(), converged);

            Node left = working.getLeftChild();
            Node right = working.getRightChild();
            left.setRightChild(converged);
            left.setRightOperation(rightPrime);
            right.setLeftChild(converged);
            right.setLeftOperation(leftPrime);
            converged.setLeftParent(left);
            converged.setRightParent(right);

            working = left;
        }
        return working.getRightOperation();
	}

	@Override
	public String toString() {
	    StringBuffer output = new StringBuffer();
	    Node row = rootNode;
        while (row != null) {
            Node col = row;
            while (col != null) {
                output.append(col.getKey()).append('\t');
                col = col.getRightChild();
            }
            row = row.getLeftChild();
            output.append('\n');
        }
        return output.toString();
	}
}
