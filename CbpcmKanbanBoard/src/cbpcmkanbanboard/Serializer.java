/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbpcmkanbanboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.stage.Stage;

/**
 * @author Chris Bigge
 */
public abstract class Serializer {
    public Object deserialize(File f) {
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(f); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            Object result =  in.readObject(); 
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
        } 
        return null;
    }
    
    public void serialize(File f, Object o) {
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
}
