package fileManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {


    public ListView<String> listVewFiles;

    public void menuItemFileExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listVewFiles.getItems().addAll("123",  "1234");
    }

    public List<FileInfo> scanFiles(Path root){
        try{
        return Files.list(root).map(FileInfo::new).collect(Collectors.toList());
        } catch (IOException e){
        throw new RuntimeException("Files scan exception: " + root);
        }
    }
}
