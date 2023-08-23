package com.example.sampc482pa;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Optional;


/**
 * A Class containing several utility functions used throughout the application
 *
 * @author Michael Samp
 */
public abstract class Utilities {

    /**
     * Formats any table in the application since they all have nearly identical column
     * data types.
     *
     * @param table The table to format
     * @param <T> A generic class representing Product or Part based on the table
     */
    public static <T> void formatTable(TableView<T> table) {
        ObservableList<TableColumn<T, ?>> tableColumns = table.getColumns();

        TableColumn<T, Integer> idCol = (TableColumn<T, Integer>) tableColumns.get(0);
        TableColumn<T, String> nameCol = (TableColumn<T, String>) tableColumns.get(1);
        TableColumn<T, Integer> stockCol = (TableColumn<T, Integer>) tableColumns.get(2);
        TableColumn<T, Number> priceCol = (TableColumn<T, Number>) tableColumns.get(3);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        priceCol.setCellFactory(cell -> new TableCell<>() {
            @Override
            protected void updateItem(Number price, boolean empty) {
                super.updateItem(price, empty);
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                if (empty) {
                    setText(null);
                } else {
                    setText(formatter.format(price));
                }
            }
        });

    }

    /**
     * Navigates to a new page within the application specified by the given FXML file.
     *
     * @param event The event that called this method
     * @param fxmlFile The FXML file of the new page
     */
    public static void navigateToNewPage(ActionEvent event, String fxmlFile) {

        Stage stage;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Utilities.class.getResource(fxmlFile));
            Scene addPartForm = new Scene(fxmlLoader.load());
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(addPartForm);
            stage.show();
        }
        catch (IOException e) {
            Exceptions.displayIOExceptionAlert(e);
        }
    }

    /**
     * Creates a shallow copy of a given product, including its associated parts
     *
     * @param productToCopy A Product to copy
     * @return A shallow copy of the Product
     */
    public static Product copyProduct(Product productToCopy) {

        Product newProduct = new Product(
                productToCopy.getId(),
                productToCopy.getName(),
                productToCopy.getPrice(),
                productToCopy.getStock(),
                productToCopy.getMin(),
                productToCopy.getMax()
        );

        for (Part p : productToCopy.getAllAssociatedParts()) {
            newProduct.addAssociatedPart(p);
        }

        return newProduct;
    }

    /**
     * Displays a general information alert with a provided message
     *
     * @param message The displayed message
     */
    public static void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Verifies that a given part has a minimum stock greater than zero and a current stock
     * greater than the minimum stock, but less than the maximum stock.
     *
     * @param part The part
     * @throws Exception Thrown if the part violates either condition
     */
    public static void validatePartInventory(Part part) throws Exception {
        if (part.getStock() < part.getMin() || part.getStock() > part.getMax()) {
            throw new Exception("Part stock must be between the inventory bounds");
        }

        if (part.getMin() < 0) {
            throw new Exception("Minimum stock must be at least 0");
        }
    }

    /**
     * Verifies that the given form entries are consistent with a valid part inventory. Meaning that
     * the maximum stock is greater than the minimum stock, the current stock is between the minimum
     * and maximum stock, and the minimum stock is at least zero.
     *
     * @param stock The current stock
     * @param min The minimum stock
     * @param max The maximum stock
     * @throws Exception Thrown if any of the three conditions are violated
     */
    public static void validatePartInventory(int stock, int min, int max) throws Exception {
        if (max < min) {
            throw new Exception("Maximum stock must be greater than minimum stock");
        }

        if (stock < min || stock > max) {
            throw new Exception("Part stock must be between the inventory bounds");
        }

        if (min < 0) {
            throw new Exception("Minimum stock must be at least 0");
        }
    }

    /**
     * Verifies that a given product has a minimum stock greater than zero and a current stock
     * greater than the minimum stock, but less than the maximum stock.
     *
     * @param product The product
     * @throws Exception Thrown if the product violates either condition
     */
    public static void validateProductInventory(Product product) throws Exception {
        if (product.getStock() < product.getMin() || product.getStock() > product.getMax()) {
            throw new Exception("Part stock must be between the inventory bounds");
        }

        if (product.getMin() < 0) {
            throw new Exception("Minimum stock must be at least 0");
        }

        if (product.getMax() < product.getMin()) {
            throw new Exception("Maximum stock must be greater than minimum stock");
        }
    }

    /**
     * Verifies that the given form entries are consistent with a valid product inventory. Meaning that
     * the maximum stock is greater than the minimum stock, the current stock is between the minimum
     * and maximum stock, and the minimum stock is at least zero.
     *
     * @param stock The current stock
     * @param min The minimum stock
     * @param max The maximum stock
     * @throws Exception Thrown if any of the three conditions are violated
     */
    public static void validateProductInventory(int stock, int min, int max) throws Exception {
        if (max < min) {
            throw new Exception("Maximum stock must be greater than minimum stock");
        }

        if (stock < min || stock > max) {
            throw new Exception("Product stock must be between the inventory bounds");
        }

        if (min < 0) {
            throw new Exception("Minimum stock must be at least 0");
        }
    }

    /**
     * Displays a confirmation box for the user to confirm their previous action
     *
     * @param message String specifying the previous action
     * @return True if the user confirms, otherwise False
     */
    public static boolean confirmUserAction(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm " + message);
        Optional<ButtonType> option = alert.showAndWait();

        return option.isPresent() && option.get() == ButtonType.OK;
    }

}
