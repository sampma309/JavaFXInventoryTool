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

public abstract class Utilities {

    public static <T> void formatTable(TableColumn<T, Integer> idCol,
                                TableColumn<T, String> nameCol,
                                TableColumn<T, Integer> stockCol,
                                TableColumn<T, Number> priceCol) {


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

    public static void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void validatePartInventory(Part part) throws Exception {
        if (part.getStock() < part.getMin() || part.getStock() > part.getMax()) {
            throw new Exception("Part stock must be between the inventory bounds");
        }

        if (part.getMin() < 0) {
            throw new Exception("Minimum stock must be at least 0");
        }
    }

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

    public static boolean confirmUserAction(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm " + message);
        Optional<ButtonType> option = alert.showAndWait();

        return option.isPresent() && option.get() == ButtonType.OK;
    }

    public static void main(String[] args){

    }
}
