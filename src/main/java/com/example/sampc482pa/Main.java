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

        InHouse wheel = new InHouse(-1, "Wheel", 72.00, 152, 0, 500, 1);
        Outsourced engine = new Outsourced(-1, "Engine", 2300.00, 7, 0, 25, "Cummins");
        InHouse windshield = new InHouse(-1, "Windshield", 540.00, 12, 0, 50, 2);
        Outsourced handles = new Outsourced(-1, "Handlebars", 30.00, 23, 0, 90, "Huffy");
        InHouse paint = new InHouse(-1, "Paint (green)", 15.00, 50, 0, 420, 3);
        Inventory.addPart(wheel);
        Inventory.addPart(engine);
        Inventory.addPart(windshield);
        Inventory.addPart(handles);
        Inventory.addPart(paint);
        ObservableList<Part> parts = Inventory.getAllParts();
        Product car = new Product(-1, "Car", 4000.00, 2, 1, 40);
        Product bike = new Product(-1, "Bike", 400.00, 13, 0, 35);
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
            e.printStackTrace();
        }
        catch (Throwable thrwObj) {
            thrwObj.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
