package rtx.smart_boutique.Controllers.Admin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import rtx.smart_boutique.Model.DatabaseDriver;
import rtx.smart_boutique.Model.Employee;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewEmployeeController implements Initializable {
    public ListView<String> employee_lstView;
    public TextField search_txt;
    public Button deleteEmployee_btn;
    public Button clear_btn;
    public Button setAdmin_btn;
    public Button removeAdmin_btn;
    DatabaseDriver driver = new DatabaseDriver();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      addListener();
    }

    public void addListener(){
        search_txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                try {
                    onSearch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        deleteEmployee_btn.setOnAction(event -> onDeleteEmployee());
        clear_btn.setOnAction(event -> clearList());
        setAdmin_btn.setOnAction(event -> onSetAdmin());
       removeAdmin_btn.setOnAction(event -> onrmAdmin());
    }

    public void clearList() {
        employee_lstView.getItems().clear();
    }
    public void onSearch(){
        DatabaseDriver driver = new DatabaseDriver();
        if(search_txt.getText() == null){
            employee_lstView.getItems().clear();
        }
        List<Employee> employees = driver.QueryEmployeeData(search_txt.getText());
        ObservableList<String> employeeNames = FXCollections.observableArrayList();
        int index = 1;
        for (Employee emp : employees) {
            employeeNames.add( "\t" +index + ". " + "The first Name is:" +"\t\t" + emp.firstnameProperty().get() + "," + "\t" + " Second Name:" + "\t\t" + emp.lastnameproperty().get() + "," + "\t\t" + "and Username:" + "\t\t" + emp.usernameproperty().get()  + "\t" + "ID Number:" + "\t" + emp.IDproperty().get());
            index++;
        }
        employee_lstView.setItems(employeeNames);
    }

    private int extractEmployeeId( String selectedEmployee) {
        String[] parts = selectedEmployee.split("\t+");
        String idPart = parts[parts.length - 1].trim();
        return Integer.parseInt(idPart);
    }

    private void onDeleteEmployee(){
        String selectedEmployee =employee_lstView.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            int employeeId = extractEmployeeId(selectedEmployee);
            driver.deleteEmployee(employeeId);
            employee_lstView.getItems().remove(selectedEmployee);
        } else {
            System.out.println("No employee selected.");
        }
    }
private  void onSetAdmin() {
    String selectedEmployee = employee_lstView.getSelectionModel().getSelectedItem();
    if (selectedEmployee != null) {
        int employeeId = extractEmployeeId(selectedEmployee);
        driver.setAdmin(employeeId);
    }
}
    private  void onrmAdmin() {
        String selectedEmployee = employee_lstView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            int employeeId = extractEmployeeId(selectedEmployee);
            driver.rmAdmin(employeeId);
        }
    }
}
