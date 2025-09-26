package rtx.smart_boutique.Controllers.Employee;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import rtx.smart_boutique.Model.Model;
import rtx.smart_boutique.Views.EmployeeMenuOptions;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmployeeMenuController implements Initializable {

    public Button dashboard_btn;
    public Button make_sale_btn;
    public Button view_sale_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;
    public ImageView Eprofile_img;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/boutique_icon.jpg")));
        Eprofile_img.setImage(img1);
        applyCircularClip(Eprofile_img);
        addListener();
    }
    public void applyCircularClip(ImageView imageView) {
        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imageView.setClip(clip);
    }
    public void addListener(){
        dashboard_btn.setOnAction(event -> onDashboard());
        make_sale_btn.setOnAction(event -> onMakeSale());
        view_sale_btn.setOnAction(event -> onViewSale());
        logout_btn.setOnAction(event -> onLogout());
        profile_btn.setOnAction(event -> onProfile());
    }

    private void onDashboard(){
        Model.getInstance().getViewFactory().getEmployeeSelectedItem().set(EmployeeMenuOptions.DASHBOARD);
    }
    private  void onMakeSale(){
        Model.getInstance().getViewFactory().getEmployeeSelectedItem().set(EmployeeMenuOptions.MAKE_SALE);
    }
    private void onViewSale(){
        Model.getInstance().getViewFactory().getEmployeeSelectedItem().set(EmployeeMenuOptions.VIEW_SALES);
    }
   private void onLogout(){
        Model.getInstance().rmEmployeeCred();
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
    private void onProfile(){
        Model.getInstance().getViewFactory().getEmployeeSelectedItem().set(EmployeeMenuOptions.PROFILE);
    }

}
