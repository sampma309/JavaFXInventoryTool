package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


/**
 * Controller for Add Product form.
 *
 * @author Michael Samp
 */
public class AddProductController {
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TextField productNameText, productStockText, productPriceOrCostText, productStockMaxText, productStockMinText, partsSearchBox;
    final private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

     /**
     * Built-in JavaFX method that runs when the page is first loaded
     */
    public void initialize() {
        availablePartsTable.setItems(Inventory.getAllParts());
        Utilities.formatTable(availablePartsTable);

        associatedPartsTable.setItems(associatedParts);
        Utilities.formatTable(associatedPartsTable);
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
        associatedParts.add(associatedPart);
    }

    /**
     * Removes the selected part from the associated parts table.
     */
    public void removeAssociatedPart() {
        Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();

        if (Utilities.confirmUserAction("part disassociation")) {
            associatedParts.remove(partToRemove);
        }
    }

    /**
     * Calls the productFactory method and returns to the main page if the created Product is valid
     *
     * @param event The event that called this method
     */
    public void createProduct(ActionEvent event) {
        boolean productCreated = productFactory();

        if (productCreated) {
            returnToMainPage(event);
        }
    }

    /**
     * Verifies the data in the form is valid and creates a new Product consisting of that data and
     * the parts in the associated parts table. Adds the new Product to the Inventory.
     *
     * @return A boolean indicating if the created Product is valid
     */
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

     /**
     * Changes the scene back to the main page.
     *
     * @param event The event that called this method.
     */
    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
