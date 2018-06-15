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
    private Node serverState;
    private Node localState;
	
	public Graph(String rootKey, Transformer transformer, Composer composer, KeyGenerator keyGenerator) {
		nodeMap = new HashMap<>();
		rootNode = new Node(rootKey);
        nodeMap.put(rootKey, rootNode);
        serverState = rootNode;
        localState = rootNode;
		
		this.transformer = transformer;
		this.composer = composer;
		this.keyGenerator = keyGenerator;
    }
    
    public Operation generateOperation() {
        List<Operation> operationList = new ArrayList<>();
        Node working = serverState;
        while (working.getLeftChild() != null) {
            operationList.add(working.getLeftOperation());
            working = working.getLeftChild();
        }
        return composer.compose(operationList);
    }

    public Operation serverInsert(String key, String parentKey, Operation operation) {
        if (!nodeMap.containsKey(parentKey)) {
            throw new RuntimeException("parentKey not found");
        }
        Node parentNode = nodeMap.get(parentKey);
        if (parentNode.getLeftChild() == null) {
            Node node = new Node(key);
            nodeMap.put(node.getKey(), node);
            parentNode.setLeftChild(node);
            parentNode.setLeftOperation(operation);
            node.setRightParent(parentNode);
            localState = node;
            return operation;
        }

        Node working = parentNode;
        Node temp = new Node(keyGenerator.generateKey());
        working.setRightChild(temp);
        working.setRightOperation(operation);
        temp.setLeftParent(working);
        while (working.getLeftChild() != null) {
            Pair pair = transformer.transform(working.getLeftOperation(), working.getRightOperation());
            Operation leftPrime = pair.getLeftPrime();
            Operation rightPrime = pair.getRightPrime();

            Node converged = new Node(keyGenerator.generateKey());

            Node left = working.getLeftChild();
            Node right = working.getRightChild();

            left.setRightChild(converged);
            left.setRightOperation(leftPrime);
            converged.setLeftParent(left);

            right.setLeftChild(converged);
            right.setLeftOperation(rightPrime);
            converged.setRightParent(right);

            working = left;
        }

        Node node = working.getRightChild();
        node.setKey(key);
        nodeMap.put(node.getKey(), node);

        localState = node;

        return working.getRightOperation();
    }
	
	public void insertLeft(String key, String parentKey, Operation operation) {
        if (!nodeMap.containsKey(parentKey)) {
            throw new RuntimeException("parentKey not found");
        }
        Node parentNode = nodeMap.get(parentKey);
        if (parentNode.getLeftChild() != null) {
            throw new RuntimeException("operation outdated");
        }

        // insert new node
        Node node = new Node(key);
        nodeMap.put(key, node);
        node.setRightParent(parentNode);
        parentNode.setLeftChild(node);
        parentNode.setLeftOperation(operation);
        localState = node;
	}

	public Operation insertRight(String key, String parentKey, Operation operation) {
        if (nodeMap.containsKey(key)) {
            // update curr
            Node node = nodeMap.get(key);
            Node working = node;
            while (working.getRightChild() != null) {
                working = working.getRightChild();
            }
            nodeMap.remove(key);
            nodeMap.remove(working.getKey());
            node.setKey(working.getKey());
            working.setKey(key);
            if (localState == node) {
                localState = working;
            }
            nodeMap.put(working.getKey(), working);
            nodeMap.put(node.getKey(), node);
            serverState = working;
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
        serverState = node;

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
            left.setRightOperation(leftPrime);
            right.setLeftChild(converged);
            right.setLeftOperation(rightPrime);
            converged.setLeftParent(left);
            converged.setRightParent(right);

            working = left;
        }
        localState = working.getRightChild();
        return working.getRightOperation();
    }
    
    public String getLocalStateKey() {
        return localState.getKey();
    }

    public String getServerStateKey() {
        return serverState.getKey();
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
