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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partIDColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Integer> partInventoryLvlColumn;
    public TableColumn<Part, Double> partPriceColumn;
    public TextField nameTextBox;
    public TextField inventoryTextBox;
    public TextField priceTextBox;
    public TextField maxTextBox;
    public TextField minTextBox;
    public TableView<Part> associatedPartsTable;
    public TableColumn<Part, Integer> associatedPartIDColumn;
    public TableColumn<Part, String> associatedPartNameColumn;
    public TableColumn<Part, Integer> associatedPartInventoryLvlColumn;
    public TableColumn<Part, Double> associatedPartPriceColumn;
    public TextField idTextBox;
    public Label errorLabel;
    public Label associatedMessageLabel;
    public TextField partSearchBox;
    public Label removeMessageLabel;
    private Product selectedProduct = MainScreenController.getSelectedProduct();
    private int selectedProductIndex = Inventory.getAllProducts().indexOf(selectedProduct);


    /**
     *  @param actionEvent  onSave returns the Main Screen with any given changes if valid
     *                      else invalid will remain on current screen and prompt for requirements
     * @throws IOException if the fxml is not found
     * **/
    public void onSave(ActionEvent actionEvent) throws IOException {
        if ( validateSave()){
            Parent root = FXMLLoader.load((getClass().getResource("/view/MainScreen.fxml")));
            Scene scene = new Scene(root);
            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            window.setTitle("Main Screen");
            window.setScene(scene);
            window.show();
        }
    }

    /**
     *  @param actionEvent onCancel returns the Main Screen without making any changes
     *
     *
     * @throws IOException if fxml file not found
     * **/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/MainScreen.fxml")));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Main Screen");
        window.setScene(scene);
        window.show();
    }
    /**
     *  @param actionEvent  onAdd will associate a selected part with the
     *                      Selected Product if the part is not already associated else
     *                      prompt for a part to be selected if none is given
     *
     * @throws IOException if fxml file not found
     *
     * **/
    public void onAdd(ActionEvent actionEvent) throws IOException {
        boolean alreadyAssociated = false;
        Part selectedAssociatedPart = partsTable.getSelectionModel().getSelectedItem();
        if (null != selectedAssociatedPart) {
            for (Part part : selectedProduct.getAllAssociatedParts()){
                 if(selectedAssociatedPart.getId() == part.getId()){
                     alreadyAssociated = true;
                     break;
                 }
            }
            if(!alreadyAssociated) {
                selectedProduct.addAssociatedPart(selectedAssociatedPart);
                associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
                associatedMessageLabel.setText("");
                removeMessageLabel.setText("");
                associatedPartsTable.getSortOrder().add(associatedPartIDColumn);
            }
            else {
                associatedMessageLabel.setText("Part is already associated to product");
                removeMessageLabel.setText("");

            }
        }
        else {
            associatedMessageLabel.setText("Please select a part");
            removeMessageLabel.setText("");
        }
    }

    /**
     *
     * @param actionEvent onRemove will raise a dialog box to confirm removal of association
     *                    for the selected part or prompt for a part to be selected
     *
     * @throws java.io.IOException if the fxml file is not found
     *
     */
    public void onRemove(ActionEvent actionEvent) throws Exception {
        Part selectedAssociatedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (null != selectedAssociatedPart) {
            selectedProduct.deleteAssociatedPart(selectedAssociatedPart);
            associatedMessageLabel.setText("");
            removeMessageLabel.setText("");
        }
        else{
            removeMessageLabel.setText("Please select a part");
            associatedMessageLabel.setText("");
            }
    }
    /**
     *
     * @return  boolean valid if each text field is correctly filled out for the given type
     *                 else will set the invalid text field border style to red and prompt
     *                 the user to fill out the text field as stated in the requirements message.
     *                 If all fields are valid the selected product will be updated.
     * <p>
     *   RUNTIME ERROR NumberFormatException is handled for an int or double input that is not the specified type
     *
     *
     *
     */

    public boolean validateSave() {
        boolean valid = true;
        boolean inventoryCheck = true;
        String exception = "";
        String name = "";
        int minNumber = 0;
        int maxNumber = 0;
        int invNumber = 0;
        double price = 0;
        String style = "-fx-border-color: red;";


        if (!(nameTextBox.getText().isBlank())) {
            name = nameTextBox.getText();
        }
        else {
            nameTextBox.setStyle(style);
            exception = "Name is required\n";
            valid = false;
            errorLabel.setText(exception);
        }

        if (!(minTextBox.getText().isBlank())){
            try {
                minNumber = Integer.parseInt(minTextBox.getText());
            } catch (NumberFormatException e) {
                minTextBox.setStyle(style);
                exception  = exception + "Min must be a number\n";
                valid = false;
                inventoryCheck = false;
                errorLabel.setText(exception);
            }
        }
        else {
            minTextBox.setStyle(style);
            exception  = exception + "Min is required\n";
            valid = false;
            inventoryCheck = false;
            errorLabel.setText(exception);
        }

        if (!(maxTextBox.getText().isBlank())) {
            try {
                maxNumber = Integer.parseInt(maxTextBox.getText());
            } catch (NumberFormatException e) {
                maxTextBox.setStyle(style);
                exception = exception + "Max must be a number\n";
                valid = false;
                inventoryCheck = false;
                errorLabel.setText(exception);
            }
        }
        else {
            maxTextBox.setStyle(style);
            exception = exception + "Max is required\n";
            valid = false;
            inventoryCheck = false;
            errorLabel.setText(exception);
        }

        if (!(inventoryTextBox.getText().isBlank())) {
            try {
                invNumber = Integer.parseInt(inventoryTextBox.getText());
                if (!(invNumber >= minNumber && invNumber <= maxNumber) && inventoryCheck){
                    inventoryTextBox.setStyle(style);
                    exception = exception + "Inventory must between " + minNumber + " and " + maxNumber + "\n";
                    valid = false;
                    errorLabel.setText(exception);
                }

            } catch (NumberFormatException e) {
                inventoryTextBox.setStyle(style);
                exception = exception + "Inventory must be a number\n";
                valid = false;
                errorLabel.setText(exception);
            }
        }
        else {
            inventoryTextBox.setStyle(style);
            exception = exception + "Inventory is required\n";
            valid = false;
            errorLabel.setText(exception);
        }
        if (!(priceTextBox.getText().isBlank())){
            try {
                price = Double.parseDouble(priceTextBox.getText());
            } catch (NumberFormatException e) {
                priceTextBox.setStyle(style);
                exception  = exception + "Price/Cost must be double\n";
                valid = false;
                errorLabel.setText(exception);
            }
        }
        else{
            priceTextBox.setStyle(style);
            exception  = exception + "Price/Cost is required\n";
            valid = false;
            errorLabel.setText(exception);
        }

        if (valid){
            selectedProduct.setName(name);
            selectedProduct.setStock(invNumber);
            selectedProduct.setPrice(price);
            selectedProduct.setMin(minNumber);
            selectedProduct.setMax(maxNumber);
            Inventory.updateProduct(selectedProductIndex,selectedProduct);
        }

        return valid;
    }

    /**
     *
     * @param actionEvent onPartSearch will filter table to find specified
     *                   part by given attribute
     */
    public void onPartSearch(ActionEvent actionEvent) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Display the attributes for the Selected Product in each attribute's text field
         *
         * * */
        idTextBox.setText(Integer.toString(selectedProduct.getId()));
        nameTextBox.setText(selectedProduct.getName());
        inventoryTextBox.setText(Integer.toString(selectedProduct.getStock()));
        priceTextBox.setText(Double.toString(selectedProduct.getPrice()));
        minTextBox.setText(Integer.toString((selectedProduct.getMin())));
        maxTextBox.setText(Integer.toString((selectedProduct.getMax())));


        /**
         * All Parts Table Set Table & Cell Values
         *
         * * */
        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        /**
         *  Associated Parts Table Set Table & Cell Values
         *
         *  */
        associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTable.getSortOrder().add(associatedPartIDColumn);

    }
}