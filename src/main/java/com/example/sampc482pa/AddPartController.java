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

public class AddPartController {
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

    public void createPart(ActionEvent event) {

        String partName = partNameText.getText();
        int partStock = Integer.parseInt(partInvText.getText());
        double partPriceOrCost = Double.parseDouble(partPriceOrCostText.getText());
        int partInvMax = Integer.parseInt(partInvMaxText.getText());
        int partInvMin = Integer.parseInt(partInvMinText.getText());

        if (inHousePartButton.isSelected()) {
            int partSource = Integer.parseInt(partSourceText.getText());

            /* passing -1 as the ID is purely so the constructor matches the one given in the UML
            diagram. In a real project, I wouldn't do this and instead use an AtomicInteger
            to keep track of the next available part/product ID number */
            InHouse newPart = new InHouse(-1, partName, partPriceOrCost, partStock, partInvMin, partInvMax, partSource);
            Inventory.addPart(newPart);
        } else if (outsourcedPartButton.isSelected()) {
            String partSource = partSourceText.getText();
            Outsourced newPart = new Outsourced(-1, partName, partPriceOrCost, partStock, partInvMin, partInvMax, partSource);
            Inventory.addPart(newPart);
        }
        returnToMainPage();
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
