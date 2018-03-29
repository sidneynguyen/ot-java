package com.sidneynguyen.otjava;

import java.util.LinkedList;
import java.util.Queue;

public class Server {

    private String document;
    private Graph graph;
    private Queue<Message> receiveQueue;
    private Queue<Message> sendQueue;

    public Server(String document, String rootKey) {
        this.document = document;
        graph = new Graph(rootKey, new SliceTransformer(),
                new SliceComposer(), new UUIDGenerator());
        receiveQueue = new LinkedList<>();
        sendQueue = new LinkedList<>();
    }

    public void enqueueReceivedOp(Message message) {
        receiveQueue.add(message);
    }

    public void enqueueOpToSend(Message message) {
        sendQueue.add(message);
    }

    public void applyReceivedOp() {
        Message message = receiveQueue.remove();
        String parentKey = graph.getLocalStateKey();
        Operation operation = graph.serverInsert(message.getKey(), message.getParentKey(), message.getOperation());
        String key = graph.getLocalStateKey();
        Message messageToSend = new Message(key, parentKey, operation);
        enqueueOpToSend(messageToSend);
        document = Operation.applyOperation(document, operation);
    }

    public String getDocument() {
        return document;
    }
}