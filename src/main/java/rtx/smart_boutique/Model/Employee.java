package rtx.smart_boutique.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty username;
    private final StringProperty DOB;
    private final IntegerProperty ID;
    private  final StringProperty Password;

    public Employee(String fName, String lName, String username, int id, String DOB, String password){
        this.firstName= new SimpleStringProperty(this,"First Name", fName);
        this.lastName= new SimpleStringProperty(this,"Last Name", lName);
        this.username=new SimpleStringProperty(this,"Username",username);
        this.ID= new SimpleIntegerProperty(this,"ID Number",id);
        this.DOB= new SimpleStringProperty(this,"Date of Birth", DOB);
        this.Password = new SimpleStringProperty(this,"Password", password);
    }
    public Employee(String fName, String lName, String username, int id, String DOB){
        this.firstName= new SimpleStringProperty(this,"First Name", fName);
        this.lastName= new SimpleStringProperty(this,"Last Name", lName);
        this.username=new SimpleStringProperty(this,"Username",username);
        this.ID= new SimpleIntegerProperty(this,"ID Number",id);
        this.DOB= new SimpleStringProperty(this,"Date of Birth", DOB);
        this.Password = new SimpleStringProperty();
    }
    public StringProperty firstnameProperty(){return firstName;}
    public StringProperty lastnameproperty(){return  lastName;}
    public StringProperty usernameproperty(){return  username;}
    public IntegerProperty IDproperty(){return ID;}
    public  StringProperty  DOBproperty(){return DOB;}
    public StringProperty passwordProperty(){return  Password;}

    }


