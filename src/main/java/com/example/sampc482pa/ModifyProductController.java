package com.example.sampc482pa;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ModifyProductController {

    @FXML
    private TextField productIDText, productNameText, productInvText, productPriceOrCostText, productInvMaxText, productInvMinText;
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
        this.productIndex = productIdx;
    }

    private void loadAvailablePartsTable(ObservableList<Part> availableParts) {
        availablePartsTable.setItems(availableParts);
        Utilities.formatTable(availablePartsTable);
    }

    private void loadAssociatedPartsTable(ObservableList<Part> associatedParts) {
        associatedPartsTable.setItems(associatedParts);
        Utilities.formatTable(associatedPartsTable);
    }

    public void addAssociatedPart() {
        Part associatedPart = availablePartsTable.getSelectionModel().getSelectedItem();

        modifiedProduct.addAssociatedPart(associatedPart);
        loadAssociatedPartsTable(modifiedProduct.getAllAssociatedParts());
    }

    public void modifyProduct(ActionEvent event) {
        try {
            modifiedProduct.setName(productNameText.getText());
            modifiedProduct.setStock(Integer.parseInt(productInvText.getText()));
            modifiedProduct.setPrice(Double.parseDouble(productPriceOrCostText.getText()));
            modifiedProduct.setMax(Integer.parseInt(productInvMaxText.getText()));
            modifiedProduct.setMin(Integer.parseInt(productInvMinText.getText()));

            Inventory.updateProduct(productIndex, modifiedProduct);
            Utilities.navigateToNewPage(event, "main-view.fxml");
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
