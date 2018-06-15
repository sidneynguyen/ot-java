package com.sidneynguyen.otjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TestGuiController {

    private Client client1;
    private Client client2;
    private Server server;

    @FXML
    private Label serverLabel;

    @FXML
    private TextArea client1Text;

    @FXML
    private TextArea client2Text;

    public TestGuiController() {
        String document = "Hello";
        String key = new UUIDGenerator().generateKey();
        client1 = new Client(document, key);
        client2 = new Client(document, key);
        server = new Server(document, key);
    }

    @FXML
    protected void handleClient1Push(ActionEvent event) {
        String edit = client1Text.getText();
        client1.applyLocalEdits(edit);
        client1.enqueueOpToSend();
        Message clientMsg = client1.sendOp();

        server.enqueueReceivedOp(clientMsg);
        server.applyReceivedOp();
        serverLabel.setText(server.getDocument());
        Message serverMsg = server.sendOp();

        client1.enqueueReceivedOp(serverMsg);
        client2.enqueueReceivedOp(serverMsg);
    }

    @FXML
    protected void handleClient2Push(ActionEvent event) {
        String edit = client2Text.getText();
        client2.applyLocalEdits(edit);
        client2.enqueueOpToSend();
        Message clientMsg = client2.sendOp();

        server.enqueueReceivedOp(clientMsg);
        server.applyReceivedOp();
        serverLabel.setText(server.getDocument());
        Message serverMsg = server.sendOp();

        client1.enqueueReceivedOp(serverMsg);
        client2.enqueueReceivedOp(serverMsg);
    }

    @FXML
    protected void handleClient1Pull(ActionEvent event) {
        String edit = client1Text.getText();
        client1.applyLocalEdits(edit);

        client1.applyReceivedOp();
        client1Text.setText(client1.getDocument());
    }

    @FXML
    protected void handleClient2Pull(ActionEvent event) {
        String edit = client2Text.getText();
        client2.applyLocalEdits(edit);

        client2.applyReceivedOp();
        client2Text.setText(client2.getDocument());
    }
}
