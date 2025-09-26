package rtx.smart_boutique;

import javafx.application.Application;
import javafx.stage.Stage;
import rtx.smart_boutique.Model.Model;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showLoginWindow();

    }
}
