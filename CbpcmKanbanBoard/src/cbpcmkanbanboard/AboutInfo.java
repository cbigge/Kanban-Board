/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbpcmkanbanboard;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Chris Bigge
 */
public interface AboutInfo {
    final String title = "About";
    final String content = "Project: Kanban Board\nAuthor: Chris Bigge\nContact: cbigge@mail.missouri.edu";
    final Alert alert = new Alert(AlertType.INFORMATION);
    
    public void showAlert();
}
