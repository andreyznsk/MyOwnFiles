package fileManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javax.swing.text.PlainDocument;

public class Controller {
    public void menuItemFileExit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
