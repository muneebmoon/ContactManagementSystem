package com.example.contactmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {
    @FXML
    private TextField SrNoTxt ;
    @FXML
    private TextField emailTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField phoneNoTxt;

    @FXML
    public void Save(ActionEvent event) {
        if (nameTxt.getText().isEmpty() || phoneNoTxt.getText().isEmpty() || emailTxt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("All Fields are required");
            alert.showAndWait();
        }else {
            if (PhoneNoValidation(phoneNoTxt.getText())) {
                boolean flag = DBConnection.editData(Integer.parseInt(SrNoTxt.getText()), nameTxt.getText(), (Long.parseLong(phoneNoTxt.getText())), emailTxt.getText());
                if (flag) {
                    Stage stage = (Stage) nameTxt.getScene().getWindow();
                    stage.close();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Phone Number ");
                alert.showAndWait();
            }
        }
    }

    private boolean PhoneNoValidation(String input){
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public  void getData(int id, String name, long PhoneNo, String email){
        SrNoTxt.setText(String.valueOf(id));
        nameTxt.setText(name);
        phoneNoTxt.setText(String.valueOf(PhoneNo));
        emailTxt.setText(email);
    }
}
