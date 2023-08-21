package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;

public class MainController {
    @FXML
    private Button exitButton;
    @FXML
    private Button addPartButton, modifyPartButton, addProductButton, modifyProductButton;
    @FXML
    private TextField partsSearchBox, productsSearchBox;

    @FXML
    private TableView<Part> partsInventory;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Number> partPriceOrCostCol;

    @FXML
    private TableView<Product> productsInventory;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Number> productPriceOrCostCol;


    private Stage stage;

    @FXML
    public void initialize() {
        try {
            // Set parts table
            partsInventory.setItems(Inventory.getAllParts());

            partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            partPriceOrCostCol.setCellFactory(cell -> new TableCell<>() {
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

            partsInventory.getColumns().setAll(partIDCol, partNameCol, partInvCol, partPriceOrCostCol);

            // Set products table
            productsInventory.setItems(Inventory.getAllProducts());

            productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            productPriceOrCostCol.setCellFactory(cell -> new TableCell<>() {
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

            productsInventory.getColumns().setAll(productIDCol, productNameCol, productInvCol, productPriceOrCostCol);
    }
        catch (Exception e) {
            System.out.println("Failed to display table rows");
            e.printStackTrace();
        }
    }

    public void exit(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void loadAddPartForm(ActionEvent event) throws IOException {
        addPartButton = (Button) event.getSource();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-part-view.fxml"));
        Scene addPartForm = new Scene(fxmlLoader.load());
        stage = (Stage) addPartButton.getScene().getWindow();
        stage.setScene(addPartForm);
        stage.show();
    }

    public void loadAddProductForm(ActionEvent event) throws IOException {
        addProductButton = (Button) event.getSource();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-product-view.fxml"));
        Scene addProductForm = new Scene(fxmlLoader.load());
        stage = (Stage) addProductButton.getScene().getWindow();
        stage.setScene(addProductForm);
        stage.show();
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
            modifyPartButton = (Button) event.getSource();
            stage = (Stage) modifyPartButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modify-part-view.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));

            Part partToModify = partsInventory.getSelectionModel().getSelectedItem();
            int partIdx = partsInventory.getSelectionModel().getSelectedIndex();

            ModifyPartController modifyPartController = fxmlLoader.getController();
            modifyPartController.initForm(partToModify, partIdx);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Runtime Error: a row must be selected.");
            alert.showAndWait();
        }

    }

    public void loadModifyProductForm(ActionEvent event) {

        try {
            modifyProductButton = (Button) event.getSource();
            stage = (Stage) modifyProductButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modify-product-view.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));

            Product productToModify = productsInventory.getSelectionModel().getSelectedItem();
            int productIdx = productsInventory.getSelectionModel().getSelectedIndex();

            ModifyProductController modifyProductController = fxmlLoader.getController();
            modifyProductController.initForm(productToModify, productIdx);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Runtime Error: a row must be selected.");
            alert.showAndWait();
        }
    }

    public void deletePart(ActionEvent event) {
        try {
            Part partToDelete = partsInventory.getSelectionModel().getSelectedItem();
            if (Inventory.deletePart(partToDelete)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Part deleted successfully.");
                alert.showAndWait();
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Runtime Error: a row must be selected.");
            alert.showAndWait();
        }
    }

    public void deleteProduct(ActionEvent event) {
        try {
            Product productToDelete = productsInventory.getSelectionModel().getSelectedItem();
            if (Inventory.deleteProduct(productToDelete)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Product deleted successfully.");
                alert.showAndWait();
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Runtime Error: a row must be selected.");
            alert.showAndWait();
        }
    }

    public void filterParts(ActionEvent event) {
        String searchText = partsSearchBox.getText();

        try {
            int searchID = Integer.parseInt(searchText);
            filterPartsByID(searchID);
        }
        catch (NumberFormatException e) {
            filterPartsByName(searchText);
        }
    }

    public void filterProducts(ActionEvent event) {
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

            partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            partsInventory.getColumns().setAll(partIDCol, partNameCol, partInvCol, partPriceOrCostCol);

        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void filterProductsByName(String searchName) {

        ObservableList<Product> foundProducts;
        try {
            foundProducts = Inventory.lookupProduct(searchName);
            productsInventory.setItems(foundProducts);

            productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            productsInventory.getColumns().setAll(productIDCol, productNameCol, productInvCol, productPriceOrCostCol);

        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    private void filterPartsByID(int searchID) {
        Part foundPart;
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        try {
            foundPart = Inventory.lookupPart(searchID);
            foundParts.add(foundPart);
            partsInventory.setItems(foundParts);

            partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            partsInventory.getColumns().setAll(partIDCol, partNameCol, partInvCol, partPriceOrCostCol);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void filterProductsByID(int searchID) {
        Product foundProduct;
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        try {
            foundProduct = Inventory.lookupProduct(searchID);
            foundProducts.add(foundProduct);
            productsInventory.setItems(foundProducts);

            productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            productsInventory.getColumns().setAll(productIDCol, productNameCol, productInvCol, productPriceOrCostCol);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
