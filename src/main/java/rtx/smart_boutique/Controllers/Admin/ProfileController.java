package rtx.smart_boutique.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rtx.smart_boutique.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    public TextField email_txt;
    public Button email_btn;
    public Label chPassword_lbl;
    public TextField newPass_txt;
    public TextField confPass_txt;
    public Button pass_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    addListener();
    }
    public void addListener(){
        email_btn.setOnAction(event -> changeEmail());
        pass_btn.setOnAction(event -> updatePassword());
    }

    public void updatePassword(){
        String newPassword = newPass_txt.getText();
        String confPassword = confPass_txt.getText();

        if (newPassword.equals(confPassword)){
            Model.getInstance().changePassword(confPassword);
        }else {
            chPassword_lbl.setText("The password does not match");
        }

    }
    public void changeEmail(){
        Model.getInstance().changeEmail(email_txt.getText());
    }

}
