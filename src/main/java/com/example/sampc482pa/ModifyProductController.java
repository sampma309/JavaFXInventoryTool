package com.example.sampc482pa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;

public class ModifyProductController {

    @FXML
    private TextField productIDText, productNameText, productInvText, productPriceOrCostText, productInvMaxText, productInvMinText;
    @FXML
    private Button cancelButton, modifyProductButton;

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

    Stage stage;
    private int productIndex;



    void initialize() {
        productIDText = new TextField();
        productNameText = new TextField();
        productInvText = new TextField();
        productPriceOrCostText = new TextField();
        productInvMaxText = new TextField();
        productInvMinText = new TextField();
    }

    public void initForm(Product modifiedProduct, int productIdx) {
        productIDText.setText(Integer.toString(modifiedProduct.getId()));
        productNameText.setText(modifiedProduct.getName());
        productInvText.setText(Integer.toString(modifiedProduct.getStock()));
        productPriceOrCostText.setText(Double.toString(modifiedProduct.getPrice()));
        productInvMaxText.setText(Integer.toString(modifiedProduct.getMax()));
        productInvMinText.setText(Integer.toString(modifiedProduct.getMin()));

        loadAvailablePartsTable();
        loadAssociatedPartsTable(modifiedProduct);

        productIndex = productIdx;
    }

    private void loadAvailablePartsTable() {

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

    private void loadAssociatedPartsTable(Product productToModify) {

        // Set associated parts table
        associatedPartsTable.setItems(productToModify.getAllAssociatedParts());


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

    public void addAssociatedPart() {
//        Part associatedPart = availablePartsTable.getSelectionModel().getSelectedItem();
//
//        associatedParts.add(associatedPart);
//        loadAssociatedPartsTable();
    }

    public void modifyProduct() {

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
