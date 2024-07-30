package com.example.contactmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloController {

    ObservableList<Person> data;
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, Integer> SrNo;
    @FXML
    private TableColumn<Person, String> EmailColumn;
    @FXML
    private TableColumn<Person, String> NameColumn;
    @FXML
    private TableColumn<Person, Double> PhoneColumn;

    @FXML
    public void initialize() {
        SrNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        data = DBConnection.getPersonData();
        tableView.setItems(data);
    }

    @FXML
    void Insert(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddScreen.fxml"));
            VBox addScreen = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New Record");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(addScreen));
            stage.showAndWait();
            refreshTableData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Edit(ActionEvent event) {
        Person person = tableView.getSelectionModel().getSelectedItem();
        if (person != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditScreen.fxml"));
                VBox addScreen = loader.load();
                EditController editController = loader.getController();
                editController.getData(person.getId(), person.getName(), person.getPhoneNumber(), person.getEmail());
                Stage stage = new Stage();
                stage.setTitle("Edit Record");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setScene(new Scene(addScreen));
                stage.showAndWait();
                refreshTableData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Record Not Selected");
            alert.setContentText("Select record to Edit");
            alert.showAndWait();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Person person = tableView.getSelectionModel().getSelectedItem();
        if (person == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Record Not Selected");
            alert.setContentText("Select record to delete");
            alert.showAndWait();
        } else {
            System.out.println(person.getId());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Record");
            alert.setContentText("Are you sure you want to delete selected Record?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean flag = DBConnection.deleteData(person.getId());
                if (flag){
                    data.remove(person);
                }
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("Delete operation canceled.");
            }
        }
    }
    private void refreshTableData() {
        data = FXCollections.observableArrayList(DBConnection.getPersonData());
        tableView.setItems(data);
    }
}
