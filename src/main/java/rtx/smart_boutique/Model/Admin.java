package rtx.smart_boutique.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Admin {

        private final IntegerProperty id;
        private final StringProperty username;
        private final StringProperty password;

        public Admin(int id, String username, String password) {
            this.id= new SimpleIntegerProperty(this,"ID Number",id);
            this.username= new SimpleStringProperty(this,"Last Name",username);
            this.password=new SimpleStringProperty(this,"Password",password);
        }




    public IntegerProperty idProperty() {
        return id;
    }


    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
