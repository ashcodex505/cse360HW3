// In UserRecord.java (in the application package)
package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserRecord {
    private final StringProperty userName;
    private final StringProperty role;

    public UserRecord(String userName, String role) {
        this.userName = new SimpleStringProperty(userName);
        this.role = new SimpleStringProperty(role);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }
    public StringProperty roleProperty() {
    	return role;
    }
}