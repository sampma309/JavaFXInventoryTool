package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class AddProductController {
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TextField productNameText, productStockText, productPriceOrCostText, productStockMaxText, productStockMinText;
    final private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public void initialize() {
        availablePartsTable.setItems(Inventory.getAllParts());
        Utilities.formatTable(availablePartsTable);

        associatedPartsTable.setItems(associatedParts);
        Utilities.formatTable(associatedPartsTable);
    }


    public void addAssociatedPart() {
        Part associatedPart = availablePartsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(associatedPart);
    }

    public void removeAssociatedPart() {
        Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();
        associatedParts.remove(partToRemove);
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
            Utilities.atLeastOneAssociatedPart(associatedParts);

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
