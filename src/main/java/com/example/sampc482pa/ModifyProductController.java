package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


/**
 * Controller for the Modify Product form
 *
 * @author Michael Samp
 */
public class ModifyProductController {

    @FXML
    private TextField productIDText, productNameText, productInvText, productPriceOrCostText, productInvMaxText, productInvMinText, partsSearchBox;
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableView<Part> associatedPartsTable;

    private Product modifiedProduct;
    private int productIndex;


    /**
     * Initializes the form fields and the associated/available parts tables based on the
     * Product passed in. Creates a shallow copy of the Product which will replace the original
     * Product when the form is saved.
     *
     * @param productToModify The Product to modify
     * @param productIdx The index of the Product in Inventory's allProducts list
     */
    public void initForm(Product productToModify, int productIdx) {
        productIDText.setText(Integer.toString(productToModify.getId()));
        productNameText.setText(productToModify.getName());
        productInvText.setText(Integer.toString(productToModify.getStock()));
        productPriceOrCostText.setText(Double.toString(productToModify.getPrice()));
        productInvMaxText.setText(Integer.toString(productToModify.getMax()));
        productInvMinText.setText(Integer.toString(productToModify.getMin()));

        availablePartsTable.setItems(Inventory.getAllParts());
        Utilities.formatTable(availablePartsTable);
        associatedPartsTable.setItems(productToModify.getAllAssociatedParts());
        Utilities.formatTable(associatedPartsTable);

        modifiedProduct = Utilities.copyProduct(productToModify);
        productIndex = productIdx;
    }

     /**
     * Takes the text from the search box and determines whether to search by ID number or by part name
     * based on whether the text can be parsed as an integer.
     */
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

    /**
     * Filters available parts by name, updating the table to include only parts which contain
     * the given text.
     *
     * @param searchName Partial or complete name of the part(s) to be filtered (case-sensitive)
     */
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

    /**
     * Filters available parts by ID, updating the table to include only part with a matching ID
     *
     * @param searchID Part ID number
     */
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

    /**
     * Adds selected part to the associated parts table.
     */
    public void addAssociatedPart() {
        Part associatedPart = availablePartsTable.getSelectionModel().getSelectedItem();
        modifiedProduct.addAssociatedPart(associatedPart);
        associatedPartsTable.setItems(modifiedProduct.getAllAssociatedParts());
    }

    /**
     * Removes the selected part from the associated parts table.
     */
    public void removeAssociatedPart() {
        Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();

        if (Utilities.confirmUserAction("part disassociation")) {
            modifiedProduct.deleteAssociatedPart(partToRemove);
            associatedPartsTable.setItems(modifiedProduct.getAllAssociatedParts());
        }
    }

    /**
     * Verifies the data in the form and tables are valid, updates the Product in Inventory.
     *
     * @param event The event that called this method
     */
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

    /**
     * Changes the scene back to the main page.
     *
     * @param event The event that called this method.
     */
    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
