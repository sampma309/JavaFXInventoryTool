package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainController {
    @FXML
    private TextField partsSearchBox, productsSearchBox;
    @FXML
    private TableView<Part> partsInventory;
    @FXML
    private TableView<Product> productsInventory;

    private Stage stage;

    @FXML
    public void initialize() {
        partsInventory.setItems(Inventory.getAllParts());
        Utilities.formatTable(partsInventory);

        productsInventory.setItems(Inventory.getAllProducts());
        Utilities.formatTable(productsInventory);
    }

    public void exit(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void loadAddPartForm(ActionEvent event) {
        Utilities.navigateToNewPage(event, "add-part-view.fxml");
    }

    public void loadAddProductForm(ActionEvent event) {
        Utilities.navigateToNewPage(event, "add-product-view.fxml");
    }

    /**
     * RUNTIME ERROR: I got a NullPointerException error when the "modify" button was pressed without a
     * row selected. I am now catching that error and displaying an alert that a row must be selected.
     * Opens the modify part form, and initializes it with the information from the selected row.
     *
     * @param event The event that triggers this method
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
