package cbpcmkanbanboard;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Chris Bigge
 */
public class Card extends AbstractModel implements Serializable {
    
    private String name;
    private String desc;
    private String status;
    
    public Card(String name, String desc, String status) {
        this.name = name;
        this.desc = desc;
        this.status = status;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setStatus(String status) {
        String old = this.status;
        this.status = status;
        firePropertyChange("Status", old, this.status);
    }
    
    public String getStatus() {
        return this.status;
    }
}
