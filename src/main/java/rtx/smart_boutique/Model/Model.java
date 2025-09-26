package rtx.smart_boutique.Model;

import org.mindrot.jbcrypt.BCrypt;
import rtx.smart_boutique.Views.ViewFactory;

import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private  boolean employeeSuccessLogin;
    private  boolean adminSuccessLogin;


    //EmployeeMenuOptions
    Employee employee;


    Admin admin;

    private Model(){
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        this.employeeSuccessLogin = false;
        this.adminSuccessLogin = false;
    }
    public static synchronized Model getInstance(){
        if(model == null){
            model= new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public Employee getEmployee(){
        return employee;
    }

    public boolean evaluateEmployeeCred(String username, String password){

        this.employee = this.databaseDriver.getEmployeeData(username);
       if(employee != null && BCrypt.checkpw(password,employee.passwordProperty().get())){
           this.employee = employee;
           return true;
       }
       return false;
    }

   public boolean verifyAdmin(String username, String enteredPassword) {
       this.admin = this.databaseDriver.getAdminData(username);
       if (admin != null && BCrypt.checkpw(enteredPassword, admin.passwordProperty().get())) {
               this.admin = admin;
               return true;
           }

       return false;
   }



    public void rmEmployeeCred(){
        this.employee = new Employee("", "","", 0, "");
        this.employeeSuccessLogin = false;
    }

    public String getName(){
        String firstname = employee.firstnameProperty().get();
        return firstname;
    }
    public String getUsername(){
        String username = employee.usernameproperty().get();
        return username;
    }

    public void sendEmployeeData(String firstName, String lastName, String userName, LocalDate selectedDate, String nationalId, String password, String email){
        DatabaseDriver databaseDriver = new DatabaseDriver();
        databaseDriver.updateEmployeeRecord(firstName,lastName,userName,selectedDate,nationalId,password,email);
    }
    public void sendItmemData(String Name, double unit_price, int quantity, String description){
        DatabaseDriver databaseDriver = new DatabaseDriver();
        databaseDriver.addItemsRecord(Name, unit_price, quantity, description);
    }
    public void changePassword(String password){
        databaseDriver.changePassword(password,getUsername());
    }

    public void changeEmail(String password){
        databaseDriver.changeEmail(password,getUsername());
    }
}
