package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the main page of the application.
 *
 * @author Michael Samp
 */
public class MainController {
    @FXML
    private TextField partsSearchBox, productsSearchBox;
    @FXML
    private TableView<Part> partsInventory;
    @FXML
    private TableView<Product> productsInventory;

    private Stage stage;

    /**
     * Built-in JavaFX method that runs when the page is first loaded
     */
    @FXML
    public void initialize() {
        partsInventory.setItems(Inventory.getAllParts());
        Utilities.formatTable(partsInventory);

        productsInventory.setItems(Inventory.getAllProducts());
        Utilities.formatTable(productsInventory);
    }

    /**
     * Exits the application.
     *
     * @param event The event that triggered the method
     */
    public void exit(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Loads the Add Part Form and changes the scene.
     *
     * @param event The event that triggered the method
     */
    public void loadAddPartForm(ActionEvent event) {
        Utilities.navigateToNewPage(event, "add-part-view.fxml");
    }

    /**
     * Loads the Add Product Form and changes the scene.
     *
     * @param event The event that triggered the method
     */
    public void loadAddProductForm(ActionEvent event) {
        Utilities.navigateToNewPage(event, "add-product-view.fxml");
    }

    /**
     * <p>RUNTIME ERROR: I got a NullPointerException error when the "modify" button was pressed without a
     * row selected. I am now catching that error and displaying an alert that a row must be selected.
     * Opens the modify part form, and initializes it with the information from the selected row. The
     * actual error would occur in ModifyPartController.initForm because the partToModify parameter was
     * null, but the issue originated from this method.</p>
     *
     * Loads the Modify Part Form, passes the part data to the form, and changes the scene.
     *
     * @param event The event that triggered this method
     */
    public void loadModifyPartForm(ActionEvent event) {

        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modify-part-view.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));

            Part partToModify = partsInventory.getSelectionModel().getSelectedItem();
            int partIdx = partsInventory.getSelectionModel().getSelectedIndex();

            ModifyPartController modifyPartController = fxmlLoader.getController();
            modifyPartController.initForm(partToModify, partIdx);

            stage.show();
        }
        catch (IOException e) {
            Exceptions.displayIOExceptionAlert(e);
        }
        catch (NullPointerException e) {
            Exceptions.displayMissingRowSelectionAlert();
        }
    }

    /**
     *
     * Loads the Modify Product Form, passes the product data to the form, and changes the scene.
     *
     * @param event The event that triggered this method
     */
    public void loadModifyProductForm(ActionEvent event) {

        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modify-product-view.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));

            Product productToModify = productsInventory.getSelectionModel().getSelectedItem();
            int productIndex = productsInventory.getSelectionModel().getFocusedIndex();

            ModifyProductController modifyProductController = fxmlLoader.getController();
            modifyProductController.initForm(productToModify, productIndex);

            stage.show();
        }
        catch (IOException e) {
            Exceptions.displayIOExceptionAlert(e);
        }
        catch (NullPointerException e) {
            Exceptions.displayMissingRowSelectionAlert();
        }
    }

    /**
     * Deletes the selected row from the table of available parts.
     */
    public void deletePart() {
        try {
            Part partToDelete = partsInventory.getSelectionModel().getSelectedItem();

            if (Utilities.confirmUserAction("part deletion")) {
                if (Inventory.deletePart(partToDelete)) {
                    Utilities.displayAlert("Part deleted successfully");
                }
            }

        }
        catch (NullPointerException e) {
            Exceptions.displayMissingRowSelectionAlert();
        }
    }

    /**
     * Deletes the selected row from the table of existing products.
     */
    public void deleteProduct() {
        try {
            Product productToDelete = productsInventory.getSelectionModel().getSelectedItem();

            if (Utilities.confirmUserAction("product deletion")) {
                if (Inventory.deleteProduct(productToDelete)) {
                    Utilities.displayAlert("Product deleted successfully.");
                } else {
                    Utilities.displayAlert("Product not deleted because at least one part is still associated with it");
                }
            }
        }
        catch (NullPointerException e) {
            Exceptions.displayMissingRowSelectionAlert();
        }
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
     * Takes the text from the search box and determines whether to search by ID number or by product name
     * based on whether the text can be parsed as an integer.
     */
    public void filterProducts() {
        String searchText = productsSearchBox.getText();

        try {
            int searchID = Integer.parseInt(searchText);
            filterProductsByID(searchID);
        }
        catch (NumberFormatException e) {
            filterProductsByName(searchText);
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
            partsInventory.setItems(foundParts);
        }
        catch (Exception e) {
            Exceptions.displayInformationAlert(e);
        }
    }

    /**
     *
     * Filters available products by name, updating the table to include only products which contain
     * the given text.
     *
     * @param searchName Partial or complete name of the product(s) to be filtered (case-sensitive)
     */
    private void filterProductsByName(String searchName) {

        ObservableList<Product> foundProducts;
        try {
            foundProducts = Inventory.lookupProduct(searchName);
            productsInventory.setItems(foundProducts);
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
            partsInventory.setItems(foundParts);
        }
        catch (Exception e) {
            Exceptions.displayInformationAlert(e);
        }
    }

    /**
     *
     * Filters available products by ID, updating the table to include only product with a matching ID
     *
     * @param searchID Product ID number
     */
    private void filterProductsByID(int searchID) {
        Product foundProduct;

        /*
         ObservableList necessary for a single item because TableView.setItems requires it
         as a parameter
        */
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        try {
            foundProduct = Inventory.lookupProduct(searchID);
            foundProducts.add(foundProduct);
            productsInventory.setItems(foundProducts);
        }
        catch (Exception e) {
            Exceptions.displayInformationAlert(e);
        }
    }

}
