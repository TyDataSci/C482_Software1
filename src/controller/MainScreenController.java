package controller;

/**
 *
 * @author Tyler Sanders
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

public class MainScreenController implements Initializable {
    public TableView<Part> partsTable;
    public TableView<Product> productTable;
    public TableColumn<Part, Integer> partIDColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Integer> partInventoryLvlColumn;
    public TableColumn<Part, Double> partPriceColumn;
    public TableColumn<Product, Integer> productIDColumn;
    public TableColumn<Product, String> productNameColumn;
    public TableColumn<Product, Integer> productInventoryLvlColumn;
    public TableColumn<Product, Double> productPriceColumn;
    public Button delPart;
    public Button modPart;
    public Button addPart;
    public Button delProduct;
    public Button modProduct;
    public Button addProduct;
    public TextField productSearchBox;
    public TextField partSearchBox;
    private static Part selectedPart;
    private static Product selectedProduct;
    public Label partErrorLabel;
    public Label productErrorLabel;
    public Button exitProgram;

    /**
     * @return the Selected Part to Modify Part Screen
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     * @return the Selected Product to Modify Product Screen
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /** Button Actions **/

    /**
     *
     * @param actionEvent onDelPart will raise a dialog box to confirm deletion
     *                    for the selected part or prompt for a part
     *                    to be selected if none is given
     *
     *  <p>
     *
     *  RUNTIME ERROR Null Pointer Exception is handled by alert isPresent to determine if
     *                               an alert window exists without the need to check for null
     *
     */
    public void onDelPart(ActionEvent actionEvent) {
        partErrorLabel.setText("");
        productErrorLabel.setText("");
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (null != selectedPart) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete");
            alert.setTitle("Parts");
            alert.setContentText("Are you sure you want to delete "+ selectedPart.getName() + " from parts?");
            Optional<ButtonType> confirmation = alert.showAndWait();

            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
               boolean success;
                success = Inventory.deletePart(selectedPart);
                if(!success){
                    partErrorLabel.setText("Part is associated with a product and cannot be deleted");
                }
            }
        }
        else {
            partErrorLabel.setText("Please select a part");
        }
}

    /**
     *
     * @param actionEvent onModPart will load Modify Part Screen
     *                    for the selected Product or prompt for a product
     *                    to be selected if none is given
     * @throws java.io.IOException if fxml file not found
     */
    public void onModPart(ActionEvent actionEvent) throws IOException {
        partErrorLabel.setText("");
        productErrorLabel.setText("");
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (null != selectedPart) {
        Parent root = FXMLLoader.load((getClass().getResource("/view/ModifyPartScreen.fxml")));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Modify Part");
        window.setScene(scene);
        window.show();
        }
        else {
            partErrorLabel.setText("Please select a part");
        }
    }

    /**
     *
     * @param actionEvent onAddPart will load Add Part Screen
     *
     * @throws IOException if fxml file not found
     */

    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/AddPartScreen.fxml")));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Add Part");
        window.setScene(scene);
        window.show();

    }

    /**
     *
     * @param actionEvent onDelProduct will raise a dialog box to confirm deletion
     *                    for the selected Product or prompt for a product
     *                    to be selected if none is given
     *
     */
    public void onDelProduct(ActionEvent actionEvent) {
        partErrorLabel.setText("");
        productErrorLabel.setText("");
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (null != selectedProduct) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete");
            alert.setTitle("Products");
            alert.setContentText("Are you sure you want to delete "+ selectedProduct.getName() +" from products?");
            Optional<ButtonType> confirmation = alert.showAndWait();

            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                if (!Inventory.deleteProduct(selectedProduct)){
                    productErrorLabel.setText("Product with associated parts cannot be deleted");
                }
            }
        }
        else {
            productErrorLabel.setText("Please select a product");
        }
    }

    /**
     *
     * @param actionEvent onModProduct will load Modify Product Screen
     *                    for the selected Product or prompt for a product
     *                    to be selected if none is given
     * @throws java.io.IOException if fxml file not found
     *
     */

    public void onModProduct(ActionEvent actionEvent) throws IOException{
        partErrorLabel.setText("");
        productErrorLabel.setText("");
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (null != selectedProduct) {
            Parent root = FXMLLoader.load((getClass().getResource("/view/ModifyProductScreen.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            productErrorLabel.setText("Please select a product");
        }
    }

    /**
     *
     * @param actionEvent onAddProduct will load Add Product Screen
     *
     * @throws java.io.IOException if fxml file not found
     *
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/AddProductScreen.fxml")));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     *
     * @param actionEvent onProductSearch will filter table to find specified
     *                   product by given attribute
     * <p>
     * RUNTIME ERROR Null Pointer Exception is handled for a null product
     *
     */

    public void onProductSearch(ActionEvent actionEvent) {
        partErrorLabel.setText("");
        productErrorLabel.setText("");
        if (!(productSearchBox.getText().isBlank())) {
            int lookupID;
            try {
                lookupID = Integer.parseInt(productSearchBox.getText());
                ObservableList<Product> results = FXCollections.observableArrayList();
                results.add(Inventory.lookupProduct(lookupID));
                productTable.setItems(results);
            } catch (NumberFormatException e) {
                productTable.setItems(Inventory.lookupProduct(productSearchBox.getText()));
            }
        }
        else
            productTable.setItems(Inventory.getAllProducts());
    }
    /**
     *
     * @param actionEvent onPartSearch will filter table to find specified
     *                   part by given attribute
     *  <p>
     *
     *  RUNTIME ERROR Null Pointer Exception is handled for a null part
     */

    public void onPartSearch(ActionEvent actionEvent) {
        partErrorLabel.setText("");
        productErrorLabel.setText("");
        if (!(partSearchBox.getText().isBlank())) {
            int lookupID;
            try {
                lookupID = Integer.parseInt(partSearchBox.getText());
                ObservableList<Part> results = FXCollections.observableArrayList();
                results.add(Inventory.lookupPart(lookupID));
                partsTable.setItems(results);
            } catch (NumberFormatException e) {
                partsTable.setItems(Inventory.lookupPart(partSearchBox.getText()));
            }
        }
        else
            partsTable.setItems(Inventory.getAllParts());
    }
    /**
     *
     * @param actionEvent onExit Exits the program.
     */
    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized.");
        /**
         *
         * Part Table Set Items and Set Cell Values
         */
        partsTable.setItems(Inventory.getAllParts());
        /* Parts Table Set Cell Values */
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         *
         * Product TableSet Items and Set Cell Values
         */
        productTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
