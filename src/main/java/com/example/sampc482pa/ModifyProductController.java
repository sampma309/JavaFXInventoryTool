package com.example.sampc482pa;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ModifyProductController {

    @FXML
    private TextField productIDText, productNameText, productInvText, productPriceOrCostText, productInvMaxText, productInvMinText;
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableColumn<Part, Integer> availablePartIDCol;
    @FXML
    private TableColumn<Part, String> availablePartNameCol;
    @FXML
    private TableColumn<Part, Integer> availablePartInvCol;
    @FXML
    private TableColumn<Part, Number> availablePartPriceOrCostCol;

    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIDCol;
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;
    @FXML
    private TableColumn<Part, Number> associatedPartPriceOrCostCol;

    private Product currentProduct;
    private Product modifiedProduct;


    public void initForm(Product productToModify) {
        productIDText.setText(Integer.toString(productToModify.getId()));
        productNameText.setText(productToModify.getName());
        productInvText.setText(Integer.toString(productToModify.getStock()));
        productPriceOrCostText.setText(Double.toString(productToModify.getPrice()));
        productInvMaxText.setText(Integer.toString(productToModify.getMax()));
        productInvMinText.setText(Integer.toString(productToModify.getMin()));

        loadAvailablePartsTable(Inventory.getAllParts());
        loadAssociatedPartsTable(productToModify.getAllAssociatedParts());

        currentProduct = productToModify;
        modifiedProduct = Utilities.copyProduct(productToModify);
    }

    private void loadAvailablePartsTable(ObservableList<Part> availableParts) {

        try {
            // Set available parts table
            availablePartsTable.setItems(availableParts);
            Utilities.formatTable(availablePartIDCol, availablePartNameCol, availablePartInvCol, availablePartPriceOrCostCol);
            availablePartsTable.getColumns().setAll(availablePartIDCol, availablePartNameCol, availablePartInvCol, availablePartPriceOrCostCol);
        }
        catch (Exception e) {
            System.out.println("Failed to display table rows");
            e.printStackTrace();
        }
    }

    private void loadAssociatedPartsTable(ObservableList<Part> associatedParts) {

        // Set associated parts table
        associatedPartsTable.setItems(associatedParts);
        Utilities.formatTable(associatedPartIDCol, associatedPartNameCol, associatedPartInvCol, associatedPartPriceOrCostCol);
        associatedPartsTable.getColumns().setAll(associatedPartIDCol, associatedPartNameCol, associatedPartInvCol, associatedPartPriceOrCostCol);
    }

    public void addAssociatedPart() {
        for (Part p : modifiedProduct.getAllAssociatedParts()) {
            System.out.println(p.getName());
        }

        Part associatedPart = availablePartsTable.getSelectionModel().getSelectedItem();


        modifiedProduct.addAssociatedPart(associatedPart);
        for (Part p : modifiedProduct.getAllAssociatedParts()) {
            System.out.println(p.getName());
        }
        loadAssociatedPartsTable(modifiedProduct.getAllAssociatedParts());
    }

    public void modifyProduct(ActionEvent event) {
        Inventory.deleteProduct(currentProduct);
        Inventory.addProduct(modifiedProduct);

        Utilities.navigateToNewPage(event, "main-view.fxml");
    }

    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
