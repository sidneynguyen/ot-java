package com.sidneynguyen.otjava;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Test");
		StackPane root = new StackPane();
		primaryStage.setScene(new Scene(root, 300, 300));
		primaryStage.show();
	}
}
