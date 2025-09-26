package rtx.smart_boutique.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import rtx.smart_boutique.Model.*;
import rtx.smart_boutique.Views.Accountype;

import java.net.URL;
import java.util.*;

public class LoginController implements Initializable {

    public TextField usrname_txtfld;
    public PasswordField passwrd_txtfld;
    public Button login_btn;
    public ImageView profile_img;
    public ChoiceBox<Accountype> logintype_chbox;
    public Label error_lbl;
    public boolean adminSucessLogin;
    public boolean employeeSuccessLogin;
    public Button forgotPasswrd_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/boutique_icon.jpg")));
        profile_img.setImage(img1);
        applyCircularClip(profile_img);
        forgotPasswrd_btn.setOnAction(event -> forgotPasswordresetter());

        logintype_chbox.setItems(FXCollections.observableArrayList(Accountype.EMPLOYEE, Accountype.ADMIN));
        logintype_chbox.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        logintype_chbox.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(logintype_chbox.getValue()));
        login_btn.setOnAction(event -> onLogin());
    }

    void applyCircularClip(ImageView imageView) {
        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imageView.setClip(clip);
    }

    public void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();

        if (Model.getInstance().getViewFactory().getLoginAccountType() == Accountype.EMPLOYEE) {
            employeeSuccessLogin = Model.getInstance().evaluateEmployeeCred(usrname_txtfld.getText(), passwrd_txtfld.getText());
            if (employeeSuccessLogin) {
                Model.getInstance().getViewFactory().showEmployeeWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                passwrd_txtfld.setText("");
                usrname_txtfld.setText("");
                error_lbl.setText("Incorrect Credentials! Please try again");

            }
        } else if (Model.getInstance().getViewFactory().getLoginAccountType() == Accountype.ADMIN) {
            adminSucessLogin = Model.getInstance().verifyAdmin(usrname_txtfld.getText(), passwrd_txtfld.getText());
            if (adminSucessLogin) {
                Model.getInstance().getViewFactory().showAdminWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                passwrd_txtfld.setText("");
                usrname_txtfld.setText("");
                error_lbl.setText("Incorrect Credentials! Please try again");

            }
        }

    }

    public void forgotPasswordresetter() {
        final Map<String, String> otpStorage = new HashMap<>();

        DatabaseDriver driver = new DatabaseDriver();
        TextInputDialog usernameDialog = new TextInputDialog();
        usernameDialog.setTitle("Forgot Password");
        usernameDialog.setHeaderText("Recover Your Account");
        usernameDialog.setContentText("Enter your username:");

        usernameDialog.showAndWait().ifPresent(username -> {
            String email = driver.getEmailByUsername(username);
            if (email != null) {
                String otp = OTPGenerator.generateOTP(6);
                otpStorage.put(username, otp);

                EmailSender.sendOTPEmail(email, username, otp);

                TextInputDialog otpDialog = new TextInputDialog();
                otpDialog.setTitle("Enter OTP");
                otpDialog.setHeaderText("An OTP has been sent to your email");
                otpDialog.setContentText("Enter the OTP:");

                otpDialog.showAndWait().ifPresent(enteredOtp -> {
                    if (otp.equals(enteredOtp)) {

                        // Custom Password Dialog
                        Dialog<String> dialog = new Dialog<>();
                        dialog.setTitle("Reset Password");
                        dialog.setHeaderText("Enter your new password");

                        PasswordField passwordField = new PasswordField();
                        passwordField.setPromptText("New Password");

                        GridPane grid = new GridPane();
                        grid.setHgap(10);
                        grid.setVgap(10);
                        grid.setPadding(new Insets(20, 150, 10, 10));

                        grid.add(new Label("Password:"), 0, 0);
                        grid.add(passwordField, 1, 0);

                        dialog.getDialogPane().setContent(grid);

                        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

                        dialog.setResultConverter(dialogButton -> {
                            if (dialogButton == okButtonType) {
                                return passwordField.getText();
                            }
                            return null;
                        });

                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(newPassword -> {
                            driver.resetPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()),email);
                            otpStorage.remove(username);

                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setTitle("Success");
                            success.setHeaderText("Password Reset Successful");
                            success.setContentText("Your password has been updated.");
                            success.showAndWait();
                        });

                    } else {
                        Alert fail = new Alert(Alert.AlertType.ERROR);
                        fail.setTitle("Invalid OTP");
                        fail.setHeaderText("OTP verification failed");
                        fail.setContentText("Please try again.");
                        fail.showAndWait();
                    }
                });

            }

            // Regardless of result, give generic response to avoid user enumeration
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Recovery Email Sent");
                alert.setHeaderText("If this username exists, an OTP has been sent.");
                alert.setContentText("Please check your email.");
                alert.showAndWait();
            }
        });
    }

}
