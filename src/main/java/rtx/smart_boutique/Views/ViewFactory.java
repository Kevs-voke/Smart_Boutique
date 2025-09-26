package rtx.smart_boutique.Views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rtx.smart_boutique.Controllers.Admin.AdminController;
import rtx.smart_boutique.Controllers.Employee.EmployeeController;

public class ViewFactory {
    private Accountype loginAccountType;
    private AnchorPane profileView;

    //Employee Variables
    private final ObjectProperty<EmployeeMenuOptions> employeeSelectedItem;
    private AnchorPane dashboardView;
    private AnchorPane makeSaleView;
    private AnchorPane viewSaleView;


    //Admin Variables
    private final ObjectProperty<AdminMenuOptions> adminSelectedItem;
    private AnchorPane aDashboardView;
    private AnchorPane newEmployeeView;
    private AnchorPane viewEmployeeView;
    private AnchorPane newItemView;
    private AnchorPane viewStockview;

    public ViewFactory(){
        this.loginAccountType = Accountype.EMPLOYEE;
        this.employeeSelectedItem = new SimpleObjectProperty<>();
        this.adminSelectedItem = new SimpleObjectProperty<>();
    }

    public Accountype getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(Accountype loginAccountType) {
        this.loginAccountType = loginAccountType;
    }


    public void showLoginWindow(){
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showEmployeeWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Employee/Employee.fxml"));
        EmployeeController employeeController = new EmployeeController();
        loader.setController(employeeController);
        createStage(loader);
    }
    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }
    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Smart_Boutique");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/boutique_icon.jpg")));
        stage.setResizable(false);
        stage.show();
    }
    public void closeStage(Stage stage) {
        stage.close();
    }



    public AnchorPane getProfileView(){
        if (profileView == null){
            try {
                profileView = new FXMLLoader(getClass().getResource("/Fxml/Profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }
    // Employee Views
    public ObjectProperty<EmployeeMenuOptions> getEmployeeSelectedItem(){return  employeeSelectedItem;}

    public AnchorPane getDashboardView(){
    if (dashboardView == null){
        try{
        dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Employee/Dashboard.fxml")).load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    return dashboardView;
    }

    public AnchorPane getMakeSaleView() {
        if(makeSaleView == null){
            try{
                makeSaleView = new FXMLLoader(getClass().getResource("/Fxml/Employee/MakeSale.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return makeSaleView;
    }

    public AnchorPane getViewSaleView(){
        if(viewSaleView == null){
            try{
                viewSaleView = new FXMLLoader(getClass().getResource("/Fxml/Employee/View_Sales.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  viewSaleView;
    }


   //Admin Views

    public ObjectProperty<AdminMenuOptions> getAdminSelectedItem() {return adminSelectedItem;}
    public AnchorPane getaDashboardView(){
        if (aDashboardView == null){
            try {
                aDashboardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ADashboard.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  aDashboardView;
    }
    public AnchorPane getNeWItemView(){
        if (newItemView == null){
            try {
                newItemView = new FXMLLoader(getClass().getResource("/Fxml/Admin/NewItem.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return newItemView;
    }
   public AnchorPane getNewEmployeeView(){
        if (newEmployeeView == null){
            try {
                newEmployeeView = new FXMLLoader(getClass().getResource("/Fxml/Admin/NewEmployees.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return newEmployeeView;
   }
   public AnchorPane getviewEmployeeViews(){
        if (viewEmployeeView == null){
            try {
                viewEmployeeView = new  FXMLLoader(getClass().getResource("/Fxml/Admin/ViewEmployee.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return viewEmployeeView;
   }
   public AnchorPane getViewStockview(){
        if (viewStockview == null){
            try{
                viewStockview = new FXMLLoader(getClass().getResource("/Fxml/Admin/ViewSales.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
       return viewStockview;
   }
}

