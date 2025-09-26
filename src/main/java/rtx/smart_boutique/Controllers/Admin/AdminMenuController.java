package rtx.smart_boutique.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import rtx.smart_boutique.Model.Model;
import rtx.smart_boutique.Views.AdminMenuOptions;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public ImageView Eprofile_img;
    public Button dashboard_btn;
    public Button N_Stock_btn;
    public Button V_Stock_btn;
    public Button N_Employee_btn;
    public Button V_Employee_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/boutique_icon.jpg")));
        Eprofile_img.setImage(img1);
        applyCircularClip(Eprofile_img);
        addListener();
    }
    public void addListener(){
        logout_btn.setOnAction(event -> onLogout());
        dashboard_btn.setOnAction(event -> onADashboard());
        N_Stock_btn.setOnAction(event -> onNewStock());
        V_Stock_btn.setOnAction(event -> onViewStock());
        N_Employee_btn.setOnAction(event -> onNewEmployee());
        V_Employee_btn.setOnAction(event -> onViewEmployee());
        profile_btn.setOnAction(event -> onProfile());
    }

    public void applyCircularClip(ImageView imageView) {
        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imageView.setClip(clip);
    }

    private void onADashboard(){
        Model.getInstance().getViewFactory().getAdminSelectedItem().set(AdminMenuOptions.ADASHBOARD);
    }
    private void onNewStock(){
        Model.getInstance().getViewFactory().getAdminSelectedItem().set(AdminMenuOptions.NEW_STOCK);
    }
    private void onViewStock(){
        Model.getInstance().getViewFactory().getAdminSelectedItem().set(AdminMenuOptions.VIEW_STOCK);
    }
    private void onNewEmployee(){
        Model.getInstance().getViewFactory().getAdminSelectedItem().set(AdminMenuOptions.NEW_EMPLOYEE);
    }
    private void onViewEmployee(){
        Model.getInstance().getViewFactory().getAdminSelectedItem().set(AdminMenuOptions.VIEW_EMPLOYEE);
    }
    private void onLogout(){
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
    private void onProfile(){
        Model.getInstance().getViewFactory().getAdminSelectedItem().set(AdminMenuOptions.PROFILE);
    }
}
