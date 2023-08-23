package com.example.sampc482pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Controller for Modify Part form.
 */
public class ModifyPartController {
    @FXML
    private Label partSourceLabel;
    @FXML
    private TextField partIDText, partNameText, partInvText, partPriceOrCostText,
            partInvMaxText, partInvMinText, partSourceText;
    @FXML
    private RadioButton inHousePartButton, outsourcedPartButton;

    private Part updatedPart;
    private int partIndex;


    /**
     * Initializes the form fields and sets the form version based on the data in partToModify. Creates a
     * shallow copy of the part which will replace the original part when the form is saved.
     *
     * @param partToModify The part to modify
     * @param partIdx The index of the part in Inventory's allParts list
     * @param <T> A generic type which can be either of Part's subclasses
     */
    public <T extends Part> void initForm(T partToModify, int partIdx) {
        partIDText.setText(Integer.toString(partToModify.getId()));
        partNameText.setText(partToModify.getName());
        partInvText.setText(Integer.toString(partToModify.getStock()));
        partPriceOrCostText.setText(Double.toString(partToModify.getPrice()));
        partInvMaxText.setText(Integer.toString(partToModify.getMax()));
        partInvMinText.setText(Integer.toString(partToModify.getMin()));

        /*
         Set form version based on part type (InHouse/Outsourced) and make a shallow copy
         of the Part to be modified. This allows updating of the copy and still being able to
         exit out of the form without saving any changes.
        */
        if (partToModify instanceof InHouse) {
            partSourceText.setText(Integer.toString(((InHouse) partToModify).getMachineId()));
            inHousePartButton.setSelected(true);
            updatedPart = new InHouse(
                    partToModify.getId(),
                    partToModify.getName(),
                    partToModify.getPrice(),
                    partToModify.getStock(),
                    partToModify.getMin(),
                    partToModify.getMax(),
                    ((InHouse) partToModify).getMachineId()
            );
        } else if (partToModify instanceof Outsourced) {
            partSourceText.setText(((Outsourced) partToModify).getCompanyName());
            outsourcedPartButton.setSelected(true);
            updatedPart = new Outsourced(
                    partToModify.getId(),
                    partToModify.getName(),
                    partToModify.getPrice(),
                    partToModify.getStock(),
                    partToModify.getMin(),
                    partToModify.getMax(),
                    ((Outsourced) partToModify).getCompanyName()
            );
        }

        partIndex = partIdx;
    }

    /**
     * Sets the part copy's fields based on the form data, verifies the form data is valid, and
     * replaces the original part with the copy.
     *
     * @param event The event that called this method
     */
    public void updatePart(ActionEvent event) {
        try {
            updatedPart.setName(partNameText.getText());
            updatedPart.setStock(Integer.parseInt(partInvText.getText()));
            updatedPart.setPrice(Double.parseDouble((partPriceOrCostText.getText())));
            updatedPart.setMin(Integer.parseInt(partInvMinText.getText()));
            updatedPart.setMax(Integer.parseInt(partInvMaxText.getText()));

            Utilities.validatePartInventory(updatedPart);

            if (inHousePartButton.isSelected()) {
                ((InHouse) updatedPart).setMachineId(Integer.parseInt(partSourceText.getText()));
            } else {
                ((Outsourced) updatedPart).setCompanyName(partSourceText.getText());
            }

            Inventory.updatePart(partIndex, updatedPart);

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
     * Changes the scene back to the main page.
     *
     * @param event The event that called this method.
     */
    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
