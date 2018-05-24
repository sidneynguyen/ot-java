package com.sidneynguyen.otjava;

import java.util.LinkedList;
import java.util.Queue;

public class Client {

    private String document;
    private Graph graph;
    private Differ differ;
    private Queue<Message> receiveQueue;
    private Queue<Message> sendQueue;
    private KeyGenerator keyGenerator;

    public Client(String document, String rootKey) {
        this.document = document;
        keyGenerator = new UUIDGenerator();
        graph = new Graph(rootKey, new SliceTransformer(),
                new SliceComposer(), keyGenerator);
        differ = new DynamicDiffer();
        receiveQueue = new LinkedList<>();
        sendQueue = new LinkedList<>();
    }

    public void enqueueReceivedOp(Message message) {
        receiveQueue.add(message);
    }

    public void enqueueOpToSend() {
        String key = graph.getLocalStateKey();
        String parentKey = graph.getServerStateKey();
        Operation operation = graph.generateOperation();
        Message message = new Message(key, parentKey, operation);
        sendQueue.add(message);
    }

    public Message sendOp() {
        return sendQueue.remove();
    }

    public void applyReceivedOp() {
        Message message = receiveQueue.remove();
        Operation operation = graph.insertRight(message.getKey(), message.getParentKey(), message.getOperation());
        if (operation != null) {
            document = Operation.applyOperation(document, operation);
        }
    }

    public void applyLocalEdits(String edit) {
        Operation operation = differ.diff(document, edit);
        String parentKey = graph.getLocalStateKey();
        graph.insertLeft(keyGenerator.generateKey(), parentKey, operation);
        document = edit;
    }

    public String getDocument() {
        return document;
    }
}