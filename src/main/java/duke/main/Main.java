package duke.main;

import java.io.IOException;
import duke.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Duke duke;

    @Override
    public void start(Stage stage) {
        System.out.println(getParameters().getNamed().toString());
        duke = Duke.createApplication();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().makeDuke();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
