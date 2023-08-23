package com.example.sampc482pa;

import javafx.scene.control.Alert;
import java.io.IOException;


/**
 * A class which displays alerts associated with various Exceptions thrown throughout
 * the application
 *
 * @author Michael Samp
 */
public abstract class Exceptions {

    /**
     * Displays alerts related to IOExceptions
     *
     * @param e The IOException
     */
    public static void displayIOExceptionAlert(IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Displays alerts related to attempts at performing actions without first selecting
     * a table row
     */
    public static void displayMissingRowSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You must select a row");
        alert.showAndWait();
    }

    /**
     * Displays general information alerts
     *
     * @param e The Exception
     */
    public static void displayInformationAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Displays general error alerts
     *
     * @param e The Exception
     */
    public static void displayErrorAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Displays alerts related to errors in expected data types in form fields
     *
     * @param e The Exception
     */
    public static void displayNumberFormattingErrorAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Number formatting error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
