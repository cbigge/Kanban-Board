/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbpcmkanbanboard;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

/**
 * @author Chris Bigge
 */
public class Board extends AbstractModel implements PropertyChangeListener, Serializable {
    
    private ArrayList<Card> backlog;
    private ArrayList<Card> sprint;
    private ArrayList<Card> dev;
    private ArrayList<Card> test;
    private ArrayList<Card> complete;
    private ArrayList<String> activity;
    private ArrayList<ArrayList<Card>> arrays;
    
    public Board() {
        backlog = new ArrayList<>();
        sprint = new ArrayList<>();
        dev = new ArrayList<>();
        test = new ArrayList<>();
        complete = new ArrayList<>();
        activity = new ArrayList<>();
        arrays = new ArrayList<>();
        arrays.add(backlog);
        arrays.add(sprint);
        arrays.add(dev);
        arrays.add(test);
        arrays.add(complete);
    }
    
    public Card createCard(String name, String desc, String status) {
        Card card = new Card(name, desc, status);
        card.addPropertyChangeListener(this);
        return card;
    }
    
    public ArrayList<Card> getBacklog() {
        return backlog;
    }
    
    public void addBacklog(Card card) {
        backlog.add(card);
    }
    
    public ArrayList<Card> getSprint() {
        return sprint;
    }
    
    public void addSprint(Card card) {
        sprint.add(card);
    }
    
    public ArrayList<Card> getDev() {
        return dev;
    }
    
    public void addDev(Card card) {
        dev.add(card);
    }
    
    public ArrayList<Card> getTest() {
        return test;
    }
    
    public void addTest(Card card) {
        test.add(card);
    }
    
    public ArrayList<Card> getComplete() {
        return test;
    }
    
    public void addComplete(Card card) {
        complete.add(card);
    }
    
    public ArrayList<String> getActivity() {
        return activity;
    }
    
    public void addActivity(String e) {
        activity.add(e);
    }
    
    public ArrayList<ArrayList<Card>> getLists() {
        return arrays;
    }
    
    public void addCardToList(Card card) {
        ArrayList<ArrayList<Card>> old = arrays;
        switch(card.getStatus()) {
            case "Backlog":
                addBacklog(card);
                break;
            case "Sprint Backlog":
                addSprint(card);
                break;
            case "Development":
                addDev(card);
                break;
            case "Testing":
                addTest(card);
                break;
            case "Completed":
                addComplete(card);
                break;
            default:
                System.out.println("Error");
                break;
        }
        firePropertyChange("List Change", old, arrays);
    }
    
    public Card removeCardFromList(String name) {
        ArrayList<ArrayList<Card>> old = arrays;
        Card result = null;
        for(int i=0; i<5; i++) {
            for(int j=0; j<arrays.get(i).size(); j++) {
                if(arrays.get(i).get(j).getName().equals(name))
                {
                    result = arrays.get(i).get(j);
                    arrays.get(i).remove(j);
                    firePropertyChange("List Change", old, arrays);
                    return result;
                }
            }
        }
        return null;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getNewValue());
        Card temp = (Card)evt.getSource();
        String cardName = temp.getName();
        removeCardFromList(cardName);
        addCardToList(temp);
    }
}
