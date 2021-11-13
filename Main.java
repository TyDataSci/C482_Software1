package main;

/**
 *
 * @author Tyler Sanders
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/view/mockUp.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        //stage.initStyle(StageStyle.TRANSPARENT);
        //stage.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
