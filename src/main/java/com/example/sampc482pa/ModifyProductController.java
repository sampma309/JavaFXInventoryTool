package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ModifyProductController {

    @FXML
    private TextField productIDText, productNameText, productInvText, productPriceOrCostText, productInvMaxText, productInvMinText, partsSearchBox;
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableView<Part> associatedPartsTable;

    private Product modifiedProduct;
    private int productIndex;


    public void initForm(Product productToModify, int productIdx) {
        productIDText.setText(Integer.toString(productToModify.getId()));
        productNameText.setText(productToModify.getName());
        productInvText.setText(Integer.toString(productToModify.getStock()));
        productPriceOrCostText.setText(Double.toString(productToModify.getPrice()));
        productInvMaxText.setText(Integer.toString(productToModify.getMax()));
        productInvMinText.setText(Integer.toString(productToModify.getMin()));

        loadAvailablePartsTable(Inventory.getAllParts());
        loadAssociatedPartsTable(productToModify.getAllAssociatedParts());

        modifiedProduct = Utilities.copyProduct(productToModify);
        productIndex = productIdx;
    }

    private void loadAvailablePartsTable(ObservableList<Part> availableParts) {
        availablePartsTable.setItems(availableParts);
        Utilities.formatTable(availablePartsTable);
    }

    private void loadAssociatedPartsTable(ObservableList<Part> associatedParts) {
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
        modifiedProduct.addAssociatedPart(associatedPart);
        loadAssociatedPartsTable(modifiedProduct.getAllAssociatedParts());
    }

    public void removeAssociatedPart() {
        Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();
        modifiedProduct.deleteAssociatedPart(partToRemove);
        loadAssociatedPartsTable(modifiedProduct.getAllAssociatedParts());
    }

    public void modifyProduct(ActionEvent event) {
        try {
            modifiedProduct.setName(productNameText.getText());
            modifiedProduct.setStock(Integer.parseInt(productInvText.getText()));
            modifiedProduct.setPrice(Double.parseDouble(productPriceOrCostText.getText()));
            modifiedProduct.setMax(Integer.parseInt(productInvMaxText.getText()));
            modifiedProduct.setMin(Integer.parseInt(productInvMinText.getText()));

            Utilities.validateProductInventory(modifiedProduct);

            Inventory.updateProduct(productIndex, modifiedProduct);
            Utilities.navigateToNewPage(event, "main-view.fxml");
        }
        catch (NumberFormatException e) {
            Exceptions.displayNumberFormattingErrorAlert(e);
        }
        catch (Exception e) {
            Exceptions.displayErrorAlert(e);
        }
    }

    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
