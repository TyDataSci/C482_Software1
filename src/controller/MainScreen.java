package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import model.Part;

public class MainScreen implements Initializable {
    public Label partsLabel;
    public TableView<Part> partsTable;
    public TableView productTable;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLvlColumn;
    public TableColumn partPriceColumn;
    public TableColumn productIDColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryLvlColumn;
    public TableColumn productPriceColumn;

    //private ObservableList<Part> partObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized.");

        ObservableList<Part> partObservableList = FXCollections.observableArrayList();

        partsTable.setItems(partObservableList);

        /* Parts Table Setter */
        partIDColumn.setCellFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellFactory(new PropertyValueFactory<>("name"));
        partInventoryLvlColumn.setCellFactory(new PropertyValueFactory<>("inventory"));
        partPriceColumn.setCellFactory(new PropertyValueFactory<>("price"));

       // partObservableList.add(new Part(1,"Screw",5,1));
    }
}
