package com.example.sampc482pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Button exitButton;
    @FXML
    private Button addPartButton;

    private Stage stage;

    @FXML
    public void initialize() {
        for (Part p : Inventory.getAllParts()) {
            System.out.println("Part Name: " + p.getName());
        }
    }

    public void exit(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void loadAddPartForm(ActionEvent event) throws IOException {
        addPartButton = (Button) event.getSource();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-part-view.fxml"));
        Scene addPartForm = new Scene(fxmlLoader.load());
        stage = (Stage) addPartButton.getScene().getWindow();
        stage.setScene(addPartForm);
        stage.show();
    }
}
