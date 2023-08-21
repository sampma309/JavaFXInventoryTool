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
    @FXML
    private TextField productNameText, productStockText, productPriceOrCostText, productStockMaxText, productStockMinText;


    Stage stage;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private String newProductName;
    private int newProductStock;
    private double newProductPriceOrCost;
    private int newProductInvMax;
    private int newProductInvMin;

    public void initialize() {
        try {
            // Set available parts table
            availablePartsTable.setItems(Inventory.getAllParts());

            availablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            availablePartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            availablePartPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            availablePartPriceOrCostCol.setCellFactory(cell -> new TableCell<>() {
                @Override
                protected void updateItem(Number price, boolean empty) {
                    super.updateItem(price, empty);
                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
                    if (empty) {
                        setText(null);
                    } else {
                        setText(formatter.format(price));
                    }
                }
            });

            availablePartsTable.getColumns().setAll(availablePartIDCol, availablePartNameCol, availablePartInvCol, availablePartPriceOrCostCol);
        }
        catch (Exception e) {
            System.out.println("Failed to display table rows");
            e.printStackTrace();
        }
    }

    public void addAssociatedPart() {
        Part associatedPart = availablePartsTable.getSelectionModel().getSelectedItem();

        associatedParts.add(associatedPart);
        updateAssociatedPartsTable();
    }

    private void updateAssociatedPartsTable() {
        try {
            associatedPartsTable.setItems(associatedParts);

            associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedPartPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            associatedPartPriceOrCostCol.setCellFactory(cell -> new TableCell<>() {
                @Override
                protected void updateItem(Number price, boolean empty) {
                    super.updateItem(price, empty);
                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
                    if (empty) {
                        setText(null);
                    } else {
                        setText(formatter.format(price));
                    }
                }
            });

            associatedPartsTable.getColumns().setAll(associatedPartIDCol, associatedPartNameCol, associatedPartInvCol, associatedPartPriceOrCostCol);
        }
        catch (Exception e) {
        System.out.println("Failed to display table rows");
        e.printStackTrace();
        }

    }

    public void createProduct(ActionEvent event) {
        boolean productCreated = productFactory();

        if (productCreated) {
            returnToMainPage();
        }
    }

    private boolean productFactory() {

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

    public void returnToMainPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Scene addPartForm = new Scene(fxmlLoader.load());
            stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(addPartForm);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
