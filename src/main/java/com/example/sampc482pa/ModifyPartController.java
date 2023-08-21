package com.example.sampc482pa;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {
    @FXML
    private Label partIDLabel, partNameLabel, partInvLabel, partPriceOrCostLabel,
            partInvMinLabel, partInvMaxLabel, partSourceLabel;
    @FXML
    private TextField partIDText, partNameText, partInvText, partPriceOrCostText,
            partInvMaxText, partInvMinText, partSourceText;
    @FXML
    private RadioButton inHousePartButton, outsourcedPartButton;
    @FXML
    private Button createPartButton, cancelButton;

    private int partIndex;

    Stage stage;

//    void initialize() {
//        partIDText = new TextField();
//        partNameText = new TextField();
//        partInvText = new TextField();
//        partPriceOrCostText = new TextField();
//        partInvMaxText = new TextField();
//        partInvMinText = new TextField();
//        partSourceText = new TextField();
//    }

    public <T extends Part> void initForm(T modifiedPart, int partIdx) {
        partIDText.setText(Integer.toString(modifiedPart.getId()));
        partNameText.setText(modifiedPart.getName());
        partInvText.setText(Integer.toString(modifiedPart.getStock()));
        partPriceOrCostText.setText(Double.toString(modifiedPart.getPrice()));
        partInvMaxText.setText(Integer.toString(modifiedPart.getMax()));
        partInvMinText.setText(Integer.toString(modifiedPart.getMin()));

        // Set form version based on part type
        if (modifiedPart instanceof InHouse) {
            partSourceText.setText(Integer.toString(((InHouse) modifiedPart).getMachineId()));
            inHousePartButton.setSelected(true);
        } else if (modifiedPart instanceof Outsourced) {
            partSourceText.setText(((Outsourced) modifiedPart).getCompanyName());
            outsourcedPartButton.setSelected(true);
        }

        partIndex = partIdx;
    }

    public void updatePart() {

        String updatedName = partNameText.getText();
        int updatedStock = Integer.parseInt(partInvText.getText());
        double updatedPriceOrCost = Double.parseDouble((partPriceOrCostText.getText()));
        int updatedInvMax = Integer.parseInt(partInvMaxText.getText());
        int updateInvMin = Integer.parseInt(partInvMinText.getText());

        if (inHousePartButton.isSelected()) {
            int updatedMachineId = Integer.parseInt(partSourceText.getText());
            InHouse updatedPart = new InHouse(-1, updatedName, updatedPriceOrCost, updatedStock, updateInvMin, updatedInvMax, updatedMachineId);
            try {
                Inventory.updatePart(partIndex, updatedPart);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String updatedCompanyName = partSourceText.getText();
            Outsourced updatedPart = new Outsourced(-1, updatedName, updatedPriceOrCost, updatedStock, updateInvMin, updatedInvMax, updatedCompanyName);
            try {
                Inventory.updatePart(partIndex, updatedPart);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        returnToMainPage();
    }

    public void changePartSource(ActionEvent event) {
        RadioButton eventSrc = (RadioButton) event.getSource();
        if (eventSrc == inHousePartButton) {
            partSourceLabel.setText("Machine ID");
        } else if (event.getSource() == outsourcedPartButton) {
            partSourceLabel.setText("Company Name");
        }
    }

    public void returnToMainPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Scene addPartForm = new Scene(fxmlLoader.load());
            stage = (Stage) createPartButton.getScene().getWindow();
            stage.setScene(addPartForm);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
