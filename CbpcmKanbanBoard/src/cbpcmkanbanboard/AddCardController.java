package cbpcmkanbanboard;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Chris Bigge
 */
public class AddCardController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextArea description;
    
    @FXML
    private ChoiceBox status;
    
    private ArrayList<String> statusOptions = new ArrayList<>();
    private Card data;
    
    @FXML
    public void addCardClicked(ActionEvent event) {
        System.out.println("addCardClicked");
        String cname = name.getText();
        String cdesc = description.getText();
        String cstatus = status.getValue().toString();
        data = new Card(cname, cdesc, cstatus);
        closeStage(event);
    }

    public Card getCard() {
        return data;
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)event.getSource(); 
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }  

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusOptions.add("Backlog");
        statusOptions.add("Sprint Backlog");
        statusOptions.add("Development");
        statusOptions.add("Testing");
        statusOptions.add("Completed");
        
        ObservableList<String> items = FXCollections.observableList(statusOptions);
        status.setItems(items);
    }
    
}
