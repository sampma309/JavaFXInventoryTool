package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;


public class AddProductController {

    @FXML
    private Button cancelButton, createProductButton;
    @FXML
    private TableView<Part> availablePartsTable;

    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TextField productNameText, productStockText, productPriceOrCostText, productStockMaxText, productStockMinText;


    Stage stage;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public void initialize() {
        availablePartsTable.setItems(Inventory.getAllParts());
        Utilities.formatTable(availablePartsTable);

        associatedPartsTable.setItems(associatedParts);
        Utilities.formatTable(associatedPartsTable);
    }


    public void addAssociatedPart() {
        Part associatedPart = availablePartsTable.getSelectionModel().getSelectedItem();

        associatedParts.add(associatedPart);
        associatedPartsTable.setItems(associatedParts);
    }

    public void createProduct(ActionEvent event) {
        boolean productCreated = productFactory();

        if (productCreated) {
            returnToMainPage(event);
        }
    }

    private boolean productFactory() {
        String newProductName;
        int newProductStock;
        double newProductPriceOrCost;
        int newProductInvMin;
        int newProductInvMax;

        try {
            newProductName = productNameText.getText();
            newProductStock = Integer.parseInt(productStockText.getText());
            newProductPriceOrCost = Double.parseDouble(productPriceOrCostText.getText());
            newProductInvMax = Integer.parseInt(productStockMaxText.getText());
            newProductInvMin = Integer.parseInt(productStockMinText.getText());
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid field entry: " + e.getMessage());
            alert.showAndWait();
            return false;
        }

        if (associatedParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must associated at least one part with this product.");
            alert.showAndWait();
            return false;
        }

        Product newProduct = new Product(-1, newProductName, newProductPriceOrCost, newProductStock, newProductInvMin, newProductInvMax);

        for (Part p : associatedParts) {
            newProduct.addAssociatedPart(p);
        }

        Inventory.addProduct(newProduct);
        return true;
    }

    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}