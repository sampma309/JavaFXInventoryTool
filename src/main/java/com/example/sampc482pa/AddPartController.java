package com.example.sampc482pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Controller for Add Part form.
 *
 * @author Michael Samp
 */
public class AddPartController {
    @FXML
    Label partIDLabel, partNameLabel, partInvLabel, partPriceOrCostLabel,
            partInvMinLabel, partInvMaxLabel, partSourceLabel;
    @FXML
    TextField partIDText, partNameText, partInvText, partPriceOrCostText,
            partInvMaxText, partInvMinText, partSourceText;
    @FXML
    private RadioButton inHousePartButton, outsourcedPartButton;


    /**
     * Toggle the form between its OutSourced and InHouse versions
     *
     * @param event The event that called this method
     */
    public void changePartSource(ActionEvent event) {
        RadioButton eventSrc = (RadioButton) event.getSource();
        if (eventSrc == inHousePartButton) {
            partSourceLabel.setText("Machine ID");
        } else if (event.getSource() == outsourcedPartButton) {
            partSourceLabel.setText("Company Name");
        }
    }

    /**
     * Gets the data from the form, checks that the data is valid, creates a new part based on the given
     * form data, and adds the part to the inventory.
     *
     * @param event The event that called this method
     */
    public void createPart(ActionEvent event) {
        try {
            String partName = partNameText.getText();
            int partStock = Integer.parseInt(partInvText.getText());
            double partPriceOrCost = Double.parseDouble(partPriceOrCostText.getText());
            int partInvMax = Integer.parseInt(partInvMaxText.getText());
            int partInvMin = Integer.parseInt(partInvMinText.getText());

            Utilities.validatePartInventory(partStock, partInvMin, partInvMax);

            if (inHousePartButton.isSelected()) {
                int partSource = Integer.parseInt(partSourceText.getText());

                InHouse newPart = new InHouse(IDCounters.getNextAvailablePartID(), partName, partPriceOrCost, partStock, partInvMin, partInvMax, partSource);
                Inventory.addPart(newPart);
            } else if (outsourcedPartButton.isSelected()) {
                String partSource = partSourceText.getText();
                Outsourced newPart = new Outsourced(IDCounters.getNextAvailablePartID(), partName, partPriceOrCost, partStock, partInvMin, partInvMax, partSource);
                Inventory.addPart(newPart);
            }

            returnToMainPage(event);
        }
        catch (NumberFormatException e) {
            Exceptions.displayNumberFormattingErrorAlert(e);
        }
        catch (Exception e) {
            Exceptions.displayErrorAlert(e);
        }
    }

    /**
     * Changes the scene back to the main page.
     *
     * @param event The event that called this method.
     */
    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
