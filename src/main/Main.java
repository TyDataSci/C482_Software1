package main;

/**
 *
 * @author Tyler Sanders
 *
 *
 * FUTURE ENHANCEMENT the method validateSave does not embrace the DRY (don't repeat yourself) principle
 * for a future enhancement would design a more dynamic design that would make validition changes accross controllers
 * more easy maintain by implementing a Hash Map, custom Exception Class, or Inheritance methology
 *
 * FUTURE ENHANCEMENT the table storage is limited by its internal memory, for a future enhancement CRUD design should be used and data
 * would be normalized and stored in multiples database tables.
 *
 * FUTURE ENHANCEMENT as the business need scale the current part/product lookup methods would be inefficient and a more logical algorithm
 * such as a  binary search method should be implemented
 *
 *
 *
 * JavaDoc folder can be found in the project directory ./C482_Software1/JavaDoc
 *
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {

        Inventory.addPart(new InHouse(Inventory.getUniquePartId(),"Screw", 500, 0.03,1,10,555));
        Inventory.addPart(new InHouse(Inventory.getUniquePartId(),"Sheet", 9, 4.00,1,10,12345));
        Inventory.addPart(new OutSourced(Inventory.getUniquePartId(),"JChannel", 34, .74,1,10,"Athene"));
        Inventory.addPart(new OutSourced(Inventory.getUniquePartId(),"Nail", 744, 0.01,1,10,"Workiva"));



        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
