package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.OutSourced;
import model.Part;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {
    public Button saveButton;
    public RadioButton inHouseRadio;
    public ToggleGroup partSourceGroup;
    public RadioButton outsourcedRadio;
    public TextField nameTextBox;
    public TextField inventoryTextBox;
    public TextField priceTextBox;
    public TextField maxTextBox;
    public TextField sourceTextBox;
    public Label partSourceLabel;
    public TextField minTextBox;
    public Button cancelButton;
    public Label errorLabel;
    public boolean inHouse;
    public TextField idTextBox;
    private Part selectedPart = MainScreenController.getSelectedPart();
    private int selectedPartIndex = Inventory.getAllParts().indexOf(selectedPart);


    public void onInHouse(ActionEvent actionEvent) {
        inHouse = true;
        partSourceLabel.setText("Machine ID");
    }

    public void onOutsourced(ActionEvent actionEvent) {
        inHouse = false;
        partSourceLabel.setText("Company Name");
    }

    /**
     *  @param actionEvent  onSave returns the Main Screen with any given changes if valid
     *                      else invalid will remain on current screen and prompt for requirements
     * @throws java.io.IOException if fxml file not found
     *
     * **/
    public void onSave(ActionEvent actionEvent) throws IOException {
        if ( validateSave()) {
            Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml"))));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setTitle("Main Screen");
            window.setScene(scene);
            window.show();
        }
    }

    /**
     *  @param actionEvent onCancel returns the Main Screen without making any changes
     *
     * @throws java.io.IOException if fxml file not found
     *
     * **/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml"))));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Main Screen");
        window.setScene(scene);
        window.show();
    }

    /**
     *
     * @return boolean valid if each text field is correctly filled out for the given type
     *                 else will set the invalid text field border style to red and prompt
     *                 the user to fill out the text field as stated in the requirements message.
     *                 If all fields are valid the selected part will be updated.
     * <p>
     *
     * RUNTIME ERROR NumberFormatException is handled for an int or double input that is not the specified type
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
        int machineID = 0;
        String companyName = "";
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

        if (inHouse) {
            if (!(sourceTextBox.getText().isBlank())) {

                try {
                    machineID = Integer.parseInt(sourceTextBox.getText());
                } catch (NumberFormatException e) {
                    sourceTextBox.setStyle(style);
                    exception = exception + "Machine ID must be a number\n";
                    valid = false;
                    errorLabel.setText(exception);
                }
            } else {
                sourceTextBox.setStyle(style);
                exception = exception + "Machine ID is required\n";
                valid = false;
                errorLabel.setText(exception);
            }
        }
        else {
            if (!(sourceTextBox.getText().isBlank())) {
                companyName = (sourceTextBox.getText());
            } else {
                sourceTextBox.setStyle(style);
                exception = exception + "Company Name is required\n";
                valid = false;
                errorLabel.setText(exception);
            }
        }

        if (valid && inHouse){
            selectedPart = new InHouse(selectedPart.getId(),name,invNumber,price,minNumber,maxNumber,machineID);
            Inventory.updatePart(selectedPartIndex,selectedPart);
        }
        else if (valid){
            selectedPart = new OutSourced(selectedPart.getId(),name,invNumber,price,minNumber,maxNumber,companyName);
            Inventory.updatePart(selectedPartIndex,selectedPart);
        }
        return valid;
    }

    /**
     * Determine the source of the selected part to display corresponding text field label and selected radio button
     *
     * * */
    public void initializeSourceOfPart() {
        if (selectedPart instanceof InHouse) {
            sourceTextBox.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
            inHouseRadio.setSelected(true);
            inHouse= true;
        }
        else if (selectedPart instanceof OutSourced) {
            sourceTextBox.setText(((OutSourced) selectedPart).getCompanyName());
            outsourcedRadio.setSelected(true);
            partSourceLabel.setText("Company Name");
            inHouse = false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Display the attributes for the Selected Part in each attribute's text field
         *
         * * */
        idTextBox.setText(Integer.toString(MainScreenController.getSelectedPart().getId()));
        nameTextBox.setText(MainScreenController.getSelectedPart().getName());
        inventoryTextBox.setText(Integer.toString(MainScreenController.getSelectedPart().getStock()));
        priceTextBox.setText(Double.toString(MainScreenController.getSelectedPart().getPrice()));
        minTextBox.setText(Integer.toString((MainScreenController.getSelectedPart().getMin())));
        maxTextBox.setText(Integer.toString((MainScreenController.getSelectedPart().getMax())));
        /**
         * Display the selected part text field label and radio button
         *
         * * */
        initializeSourceOfPart();
    }
}