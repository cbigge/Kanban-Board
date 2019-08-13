package cbpcmkanbanboard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Chris Bigge
 */
public class MainController extends Serializer implements Initializable, PropertyChangeListener, AboutInfo {
    
    @FXML
    AnchorPane mainView;
    
    @FXML
    TableView backlogList;
    @FXML
    TableColumn backlogNameColumn;
    @FXML
    TableColumn backlogDescriptionColumn;
    
    @FXML
    TableView sprintList;
    @FXML
    TableColumn sprintNameColumn;
    @FXML
    TableColumn sprintDescriptionColumn;
    
    @FXML
    TableView devList;
    @FXML
    TableColumn devNameColumn;
    @FXML
    TableColumn devDescriptionColumn;
    
    @FXML
    TableView testingList;
    @FXML
    TableColumn testNameColumn;
    @FXML
    TableColumn testDescriptionColumn;
    
    @FXML
    TableView doneList;
    @FXML
    TableColumn doneNameColumn;
    @FXML
    TableColumn doneDescriptionColumn;
    
    @FXML
    ListView activityList;
    
    @FXML
    Label cardNameView;
    @FXML
    TextArea cardDescriptionView;
    
    private Board board;
    private ArrayList<ArrayList<Card>> arrays;
    private ArrayList<Card> currentlySelected = new ArrayList<>();
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
    
    @FXML
    public void backlogClickItem(MouseEvent event) {
        if(!currentlySelected.isEmpty())
            currentlySelected.clear();
        
        Card temp = (Card)backlogList.getSelectionModel().getSelectedItem();
        if(temp != null) {
            cardNameView.setText(temp.getName());
            cardDescriptionView.setText(temp.getDesc());
            currentlySelected.add(temp);
        }
    }
    
    @FXML
    public void sprintClickItem(MouseEvent event) {
        if(!currentlySelected.isEmpty())
            currentlySelected.clear();
        
        Card temp = (Card)sprintList.getSelectionModel().getSelectedItem();
        if(temp != null) {
            cardNameView.setText(temp.getName());
            cardDescriptionView.setText(temp.getDesc());
            currentlySelected.add(temp);
        }
    }
    
    @FXML
    public void devClickItem(MouseEvent event) {
        if(!currentlySelected.isEmpty())
            currentlySelected.clear();
        
        Card temp = (Card)devList.getSelectionModel().getSelectedItem();
        if(temp != null) {
            cardNameView.setText(temp.getName());
            cardDescriptionView.setText(temp.getDesc());
            currentlySelected.add(temp);
        }
    }
    
    @FXML
    public void testClickItem(MouseEvent event) {
        if(!currentlySelected.isEmpty())
            currentlySelected.clear();
        
        Card temp = (Card)testingList.getSelectionModel().getSelectedItem();
        if(temp != null) {
            cardNameView.setText(temp.getName());
            cardDescriptionView.setText(temp.getDesc());
            currentlySelected.add(temp);
        }
    }
    
    @FXML
    public void doneClickItem(MouseEvent event) {
        if(!currentlySelected.isEmpty())
            currentlySelected.clear();
        
        Card temp = (Card)doneList.getSelectionModel().getSelectedItem();
        if(temp != null)
        {
            cardNameView.setText(temp.getName());
            cardDescriptionView.setText(temp.getDesc());
            currentlySelected.add(temp);
        }
    }
    
    @FXML
    private void addItemClicked(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCard.fxml"));
        Parent parent = fxmlLoader.load();
        AddCardController addCardController = fxmlLoader.<AddCardController>getController();

        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
         
        Card card = addCardController.getCard();
        try {String status = card.getStatus();} catch(NullPointerException ex) { System.out.println("Input Data Error"); }
        
        if(card != null){ board.addCardToList(card);
        
        board.addActivity(dateFormat.format(date).toString() + " - added " + card.getName() + " to " + card.getStatus());
        updateLists();}
    }
    
    @FXML
    private void removeItemClicked() {
        if(currentlySelected.isEmpty())
            return;
        
        board.removeCardFromList(currentlySelected.get(0).getName());
        board.addActivity(dateFormat.format(date).toString() + " - removed " + currentlySelected.get(0).getName());
        currentlySelected.clear();
        updateLists();
        
        cardNameView.setText("");
        cardDescriptionView.setText("");
    }
    
    @FXML
    private void moveLeftClicked() {
        if(currentlySelected.isEmpty())
            return;
        Card temp = board.removeCardFromList(currentlySelected.get(0).getName());
        currentlySelected.clear();
        String oldStatus = temp.getStatus();
        switch(oldStatus) {
            case "Backlog":
                System.out.println("Error: Can't move left.");
                break;
            case "Sprint Backlog":
                temp.setStatus("Backlog");
                break;
            case "Development":
                temp.setStatus("Sprint Backlog");
                break;
            case "Testing":
                temp.setStatus("Development");
                break;
            case "Completed":
                temp.setStatus("Testing");
                break;
            default:
                System.out.println("Error: Invalid status(moveLeftClicked).");
                break;
        }
        board.addActivity(dateFormat.format(date).toString() + " - moved " + temp.getName() + " from " + oldStatus + " to " + temp.getStatus());
        board.addCardToList(temp);
        updateLists();
    }
    
    @FXML
    private void moveRightClicked() {
        if(currentlySelected.isEmpty())
            return;
        Card temp = board.removeCardFromList(currentlySelected.get(0).getName());
        currentlySelected.clear();
        String oldStatus = temp.getStatus();
        switch(oldStatus) {
            case "Backlog":
                temp.setStatus("Sprint Backlog");
                break;
            case "Sprint Backlog":
                temp.setStatus("Development");
                break;
            case "Development":
                temp.setStatus("Testing");
                break;
            case "Testing":
                temp.setStatus("Completed");
                break;
            case "Completed":
                System.out.println("Error: Can't move right.");
                break;
            default:
                System.out.println("Error: Invalid status(moveRightClicked).");
                break;
        }
        board.addActivity(dateFormat.format(date).toString() + " - moved " + temp.getName() + " from " + oldStatus + " to " + temp.getStatus());
        board.addCardToList(temp);
        updateLists();
    }
    
    @FXML
    private void importClicked() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Board");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Kanban Board", "*.ser");
        fc.getExtensionFilters().add(filter);
        
        File f = fc.showOpenDialog(null);
        if(f == null) return;
        board = (Board)deserialize(f);
        arrays = board.getLists();
        Stage primStage = (Stage)mainView.getScene().getWindow();
        primStage.setTitle(f.getName() + " - Kanban Board");
        updateLists();
    }
    
    @FXML
    private void exportClicked() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Board");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Kanban Board",  "*.ser");
        fc.getExtensionFilters().add(filter);
        
        File f = fc.showSaveDialog(null);
        
        if(f != null) {
            serialize(f, board);
        }
    }
    
    @FXML
    private void clearClicked() {
        board = new Board();
        arrays = board.getLists();
        currentlySelected.clear();
        cardNameView.setText("");
        cardDescriptionView.setText("");
        updateLists();
    }
    
    @FXML
    private void aboutClicked() {
        showAlert();
    }
    
    @Override
    public void showAlert() {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @Override
    public Board deserialize(File f) {
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(f); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            Board result =  (Board)in.readObject(); 
            in.close(); 
            file.close(); 
            return result;
        } 
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught");
        } 
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } catch(NullPointerException ex) {System.out.println("NullPointException is caught");}
        return null;
    }
    
    public void serialize(File f, Board o) {
        if(f != null) {
            try
            {    
                //Saving of object in a file 
                FileOutputStream file = new FileOutputStream(f); 
                ObjectOutputStream out = new ObjectOutputStream(file); 

                // Method for serialization of object 
                out.writeObject(o); 

                out.close(); 
                file.close(); 

                System.out.println("Object has been serialized"); 

            } 
            catch(IOException ex) 
            { 
                System.out.println("IOException is caught"); 
            } 
        }
    }
    
    public void updateLists() {
        ObservableList<Card> backlogNames = FXCollections.observableArrayList(arrays.get(0));
        ObservableList<Card> sprintNames = FXCollections.observableArrayList(arrays.get(1));
        ObservableList<Card> devNames = FXCollections.observableArrayList(arrays.get(2));
        ObservableList<Card> testNames = FXCollections.observableArrayList(arrays.get(3));
        ObservableList<Card> doneNames = FXCollections.observableArrayList(arrays.get(4));
        ObservableList<String> activities = FXCollections.observableArrayList(board.getActivity());
        
        backlogList.setItems(backlogNames);
        sprintList.setItems(sprintNames);
        devList.setItems(devNames);
        testingList.setItems(testNames);
        doneList.setItems(doneNames);
        activityList.setItems(activities);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board();
        board.addPropertyChangeListener(this);
        arrays = board.getLists();
        
        backlogNameColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("name"));
        backlogDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("desc"));
        sprintNameColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("name"));
        sprintDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("desc"));
        devNameColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("name"));
        devDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("desc"));
        testNameColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("name"));
        testDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("desc"));
        doneNameColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("name"));
        doneDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Card,String>("desc"));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName());
        updateLists();
    }
}
