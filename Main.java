package application;
	
import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.*;



public class Main extends Application {
	
	//Declare Variables
	public Button button;
	public TextField enterNumber;
	private TextArea textArea;

	
	//Construct GUI
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Prime Numbers");
		
		button = new Button("Submit");
		button.setOnAction(buttonHandler);
		
		Label number = new Label("Enter a number:");
		
		enterNumber = new TextField();
		
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPrefHeight(20);
		
		Label instructions = new Label("Please enter shutdown when finished.");
		
		//create a tile pane
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10,20,20,20));
		vbox.setSpacing(5);
		
		//add nodes
		vbox.getChildren().addAll(number, enterNumber, button, textArea, instructions);
		
		Scene scene = new Scene(vbox);
		
		//set the scene
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	//Button Click!
	EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
	@Override
	public void handle(ActionEvent event) {
	
		//1. clear fields
		textArea.setText("");
		
		try {
			//read from textfield
			String userString = enterNumber.getText();
			
			//3.open a socket to the server
			Socket connection = new Socket("127.0.0.1",1288);
			//InputStream input = connection.getInputStream();
			OutputStream output = connection.getOutputStream();
			
			//4. send the string to the server
			output.write(userString.length());
			output.write(userString.getBytes());
			
			//5. read the results from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String serverResponse = "";
			while (serverResponse != null) {
				serverResponse = reader.readLine();
				if(serverResponse != null) {
					textArea.appendText(serverResponse);
				}
			}
			
			if(!connection.isClosed())
				connection.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	};
	
	
	//let's go
	public static void main(String[] args) {
		launch(args);
	}
}