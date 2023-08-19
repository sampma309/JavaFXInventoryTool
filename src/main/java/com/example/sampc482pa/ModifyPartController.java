package com.example.sampc482pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {
    @FXML
    Label partIDLabel, partNameLabel, partInvLabel, partPriceOrCostLabel,
            partInvMinLabel, partInvMaxLabel, partSourceLabel;
    @FXML
    TextField partIDText, partNameText, partInvText, partPriceOrCostText,
            partInvMaxText, partInvMinText, partSourceText;
    @FXML
    private RadioButton inHousePartButton, outsourcedPartButton;
    @FXML
    private Button createPartButton, cancelButton;

    Stage stage;

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
