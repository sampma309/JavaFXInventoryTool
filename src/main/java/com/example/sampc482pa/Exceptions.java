package com.example.sampc482pa;

import javafx.scene.control.Alert;

import java.io.IOException;

public abstract class Exceptions {

    public static void displayIOExceptionAlert(IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void displayMissingRowSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You must select a row");
        alert.showAndWait();
    }

    public static void displayInformationAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void displayErrorAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
