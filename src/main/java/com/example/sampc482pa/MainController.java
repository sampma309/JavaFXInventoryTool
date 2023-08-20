package com.example.sampc482pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Button exitButton;
    @FXML
    private Button addPartButton;
    @FXML
    private TableView<Part> partsInventory;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceOrCostCol;

    private Stage stage;

    @FXML
    public void initialize() {
        try {
            partsInventory.setItems(Inventory.getAllParts());

            partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceOrCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            partsInventory.getColumns().setAll(partIDCol, partNameCol, partInvCol, partPriceOrCostCol);
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
     * RUNTIME ERROR: I got a NullPointerException error when the "modify" button was pressed without a
     * row selected. I am now catching that error and displaying an alert that a row must be selected.
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

    /**
     * Opens the modify part form, and initializes it with the information from the selected row.
     *
     * @param event The event that triggers this method
     */
    public void loadModifyPartForm(ActionEvent event) {

        try {
            addPartButton = (Button) event.getSource();
            stage = (Stage) addPartButton.getScene().getWindow();

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

    public void deletePart(ActionEvent event) {
        try {
            Part partToDelete = partsInventory.getSelectionModel().getSelectedItem();
            Inventory.deletePart(partToDelete);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Runtime Error: a row must be selected.");
            alert.showAndWait();
        }
    }

}
