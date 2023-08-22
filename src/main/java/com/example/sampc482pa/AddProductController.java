package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Optional;


public class AddProductController {
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TextField productNameText, productStockText, productPriceOrCostText, productStockMaxText, productStockMinText, partsSearchBox;
    final private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public void initialize() {
        availablePartsTable.setItems(Inventory.getAllParts());
        Utilities.formatTable(availablePartsTable);

        associatedPartsTable.setItems(associatedParts);
        Utilities.formatTable(associatedPartsTable);
    }


    public void filterParts() {
        String searchText = partsSearchBox.getText();

        try {
            int searchID = Integer.parseInt(searchText);
            filterPartsByID(searchID);
        }
        catch (NumberFormatException e) {
            filterPartsByName(searchText);
        }
    }

    private void filterPartsByName(String searchName) {

        ObservableList<Part> foundParts;
        try {
            foundParts = Inventory.lookupPart(searchName);
            availablePartsTable.setItems(foundParts);
        }
        catch (Exception e) {
            Exceptions.displayInformationAlert(e);
        }
    }

    private void filterPartsByID(int searchID) {
        Part foundPart;

        /*
         ObservableList necessary for a single item because TableView.setItems requires it
         as a parameter
        */
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        try {
            foundPart = Inventory.lookupPart(searchID);
            foundParts.add(foundPart);
            availablePartsTable.setItems(foundParts);
        }
        catch (Exception e) {
            Exceptions.displayInformationAlert(e);
        }
    }


    public void addAssociatedPart() {
        Part associatedPart = availablePartsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(associatedPart);
    }

    public void removeAssociatedPart() {
        Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm part disassociation");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            associatedParts.remove(partToRemove);
        }
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

            Utilities.validateProductInventory(newProductStock, newProductInvMin, newProductInvMax);

            Product newProduct = new Product(
                    IDCounters.getNextAvailableProductID(),
                    newProductName,
                    newProductPriceOrCost,
                    newProductStock,
                    newProductInvMin,
                    newProductInvMax
            );

            for (Part p : associatedParts) {
                newProduct.addAssociatedPart(p);
            }

            Inventory.addProduct(newProduct);
            return true;
        }
        catch (NumberFormatException e) {
            Exceptions.displayNumberFormattingErrorAlert(e);
            return false;
        }
        catch (Exception e) {
            Exceptions.displayErrorAlert(e);
            return false;
        }
    }

    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
