package com.example.sampc482pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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

    public void changePartSource(ActionEvent event) {
        RadioButton eventSrc = (RadioButton) event.getSource();
        if (eventSrc == inHousePartButton) {
            partSourceLabel.setText("Machine ID");
        } else if (event.getSource() == outsourcedPartButton) {
            partSourceLabel.setText("Company Name");
        }
    }

    public void returnToMainPage(ActionEvent event) {
        Utilities.navigateToNewPage(event, "main-view.fxml");
    }
}
