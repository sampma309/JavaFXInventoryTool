package com.example.sampc482pa;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

//        Inventory inventory = new Inventory();
        InHouse p1 = new InHouse(1, "wheel", 2.00, 1, 1, 1, 1);
        Outsourced p2 = new Outsourced(2, "engine", 4.00, 1, 1, 1, "company a");
        Inventory.addPart(p1);
        Inventory.addPart(p2);
        ObservableList<Part> parts = Inventory.getAllParts();
        System.out.println(parts);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("C482 Performance Assessment Application");
            primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Logo.jpg"))));
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
