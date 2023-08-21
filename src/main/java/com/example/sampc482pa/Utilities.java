package com.example.sampc482pa;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;

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
            e.printStackTrace();
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

    public static void main(String[] args){

    }
}
