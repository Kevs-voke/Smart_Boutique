package rtx.smart_boutique.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import rtx.smart_boutique.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class NewEmployeeController implements Initializable {

    public Button cancel_btn;
    public Button confirm_btn;
    public TextField FirstName_txt;
    public TextField LastName_txt;
    public TextField username_txt;
    public TextField NationalId_txt;
    public TextField password_txt;
    public DatePicker dob_chooser;
    public TextField email_txt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addListener();
    }
    public void addListener(){
       cancel_btn.setOnAction(event ->clearText() );
        confirm_btn.setOnAction(event ->newEmp());
    }

    public void newEmp(){
        Model.getInstance().sendEmployeeData(FirstName_txt.getText(),LastName_txt.getText(),username_txt.getText(), dob_chooser.getValue(), NationalId_txt.getText(), BCrypt.hashpw(password_txt.getText(), BCrypt.gensalt()),email_txt.getText());
        FirstName_txt.setText("");
        LastName_txt.setText("");
        username_txt.setText("");
        NationalId_txt.setText("");
        password_txt.setText("");
        dob_chooser.setValue(null);

    }

    public void clearText(){
        FirstName_txt.setText("");
        LastName_txt.setText("");
        username_txt.setText("");
        NationalId_txt.setText("");
        password_txt.setText("");
        dob_chooser.setValue(null);
    }
}

