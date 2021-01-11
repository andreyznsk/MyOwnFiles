package fileManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {


    public ListView<FileInfo> listVewFiles;
    public TextField pathField;

    Path root;

    public void menuItemFileExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        listVewFiles.setCellFactory(new Callback<ListView<FileInfo>, ListCell<FileInfo>>() {
            @Override
            public ListCell<FileInfo> call(ListView<FileInfo> param) {
                return new ListCell<FileInfo>(){
                    @Override
                    protected void updateItem(FileInfo item, boolean empty){
                        super.updateItem(item,empty);
                        if(item==null||empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            String formattedFilename = String.format("%-30s", item.getFilename());
                            String formattedFileLength = String.format("%,d bytes", item.getFileLength());
                            if(item.getFileLength()==-1L) formattedFileLength = String.format("%s","[ DIR ]");
                            if(item.getFileLength()==-2L) formattedFileLength = "";
                            String text = String.format("%s %-20s", formattedFilename, formattedFileLength);
                            setText(text);
                        }
                    }
                };
            }
        });
        goToPath(Paths.get(".idea"));
    }

    public List<FileInfo> scanFiles(Path root){
        try{
        return Files.list(root).map(FileInfo::new).collect(Collectors.toList());
        } catch (IOException e){
        throw new RuntimeException("Files scan exception: " + root);
        }
    }

    public void goToPath(Path path){
        root = path;
        pathField.setText(root.toAbsolutePath().toString());
        listVewFiles.getItems().clear();
        listVewFiles.getItems().add(new FileInfo(FileInfo.UP_TOKEN,-2l));
        listVewFiles.getItems().addAll(scanFiles(path));
        listVewFiles.getItems().sort(new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo o1, FileInfo o2) {
                if(o1.getFilename().equals(FileInfo.UP_TOKEN)) return -1;
                return new Long(o1.getFileLength() - o2.getFileLength()).intValue();
            }
        });
    }



    public void filesListClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount()==2)   {
            FileInfo fileInfo = listVewFiles.getSelectionModel().getSelectedItem();

            if (fileInfo != null) {
                if(fileInfo.isDirectory()) {
                    Path pathTo = root.resolve(fileInfo.getFilename());
                    goToPath(pathTo);
                }
                if (fileInfo.isUpElement()) {
                    Path pathTo = root.toAbsolutePath().getParent();
                    goToPath(pathTo);
                }
            }
        }
    }
}


