package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OtpRequest {
    private final StringProperty userName;

    public OtpRequest(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }
}
