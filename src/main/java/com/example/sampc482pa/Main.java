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

        InHouse wheel = new InHouse(IDCounters.getNextAvailablePartID(), "Wheel", 72.00, 152, 0, 500, 1);
        Outsourced engine = new Outsourced(IDCounters.getNextAvailablePartID(), "Engine", 2300.00, 7, 0, 25, "Cummins");
        InHouse windshield = new InHouse(IDCounters.getNextAvailablePartID(), "Windshield", 540.00, 12, 0, 50, 2);
        Outsourced handles = new Outsourced(IDCounters.getNextAvailablePartID(), "Handlebars", 30.00, 23, 0, 90, "Huffy");
        InHouse paint = new InHouse(IDCounters.getNextAvailablePartID(), "Paint (green)", 15.00, 50, 0, 420, 3);
        Inventory.addPart(wheel);
        Inventory.addPart(engine);
        Inventory.addPart(windshield);
        Inventory.addPart(handles);
        Inventory.addPart(paint);
        ObservableList<Part> parts = Inventory.getAllParts();
        Product car = new Product(IDCounters.getNextAvailableProductID(), "Car", 4000.00, 2, 1, 40);
        Product bike = new Product(IDCounters.getNextAvailableProductID(), "Bike", 400.00, 13, 0, 35);
        car.addAssociatedPart(wheel);
        car.addAssociatedPart(engine);
        car.addAssociatedPart(paint);
        bike.addAssociatedPart(wheel);
        bike.addAssociatedPart(handles);
        bike.addAssociatedPart(paint);
        Inventory.addProduct(car);
        Inventory.addProduct(bike);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("C482 Performance Assessment Application");
            primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Logo.jpg"))));
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            Exceptions.displayIOExceptionAlert(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
