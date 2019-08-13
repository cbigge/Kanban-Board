package cbpcmkanbanboard;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Chris Bigge
 */
public class CbpcmKanabanBoard extends Application {
    private static Stage stage;
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
            
            Scene scene = new Scene(root);
            stage.setTitle("Untitled - Kanban Board");
            stage.setScene(scene);
            this.stage = stage;
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public static Stage getStage() { return stage; }
    
    public static void main(String[] args) {
        launch(args);
    }
}
