module rtx.smart_boutique {

    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires de.jensd.fx.glyphs.fontawesome;
    requires annotations;
    requires jbcrypt;
    requires jakarta.mail;


    opens rtx.smart_boutique.Controllers.Employee;
    opens rtx.smart_boutique.Controllers.Admin;
    exports rtx.smart_boutique;
    exports rtx.smart_boutique.Controllers;
    exports rtx.smart_boutique.Model;
    exports rtx.smart_boutique.Views;
    exports rtx.smart_boutique.Controllers.Employee;
    exports rtx.smart_boutique.Controllers.Admin;

}