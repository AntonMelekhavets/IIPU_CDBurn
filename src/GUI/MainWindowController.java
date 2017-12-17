package GUI;

import FileExecutors.FileInfo;
import FileExecutors.FileSearcher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainWindowController {
    @FXML
    Label pathLabel;
    @FXML
    Button formatButton;
    @FXML
    Button addButton;
    @FXML
    Button delButton;
    @FXML
    TableView<FileInfo> fileListTableView;
    @FXML
    TableView<FileInfo> fileToWriteTable;
    @FXML
    TableColumn<FileInfo, String> nameToWrite;
    @FXML
    TableColumn<FileInfo, String> typeToWrite;
    @FXML
    TableColumn<FileInfo, String> nameColumn;
    @FXML
    TableColumn<FileInfo, String> typeColumn;
    @FXML
    ComboBox<String> CDsList;
    private FileSearcher fileSearcher = new FileSearcher();

    @FXML
    private void initialize() throws Exception {
        refreshListOfFiles();
        fileListTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> goNextPath(newValue));
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().fileName);
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().fileType);
    }

    private void refreshListOfFiles(){
        pathLabel.setText(fileSearcher.getCurrentPath());
        fileListTableView.setItems(fileSearcher.getFileInfoList());
    }

    private void goNextPath (FileInfo fileInfo) {
        fileListTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (fileInfo.getFileType().contains("dir")) {
                    fileSearcher.setCurrentPath(fileInfo.fileName.get());
                    refreshListOfFiles();
                } else {
                    return;
                }
        }});
    }

    @FXML
    private void writeToCD() throws Exception {
        createDialogWindow().startWriten(fileToWriteTable.getItems());
    }

    private DialogWindowController createDialogWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DialogWindow.fxml"));
        AnchorPane root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Progress");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        DialogWindowController controller = loader.getController();
        controller.setStage(stage);
        return controller;
    }

    @FXML
    public void addFileToWrite() throws Exception{
        fileToWriteTable.getItems().add(fileListTableView.getSelectionModel().getSelectedItem());
        typeToWrite.setCellValueFactory(cellData -> cellData.getValue().fileType);
        nameToWrite.setCellValueFactory(cellData -> cellData.getValue().fileName);
    }

    @FXML
    public void delFromWriteList() throws Exception {
        fileToWriteTable.getItems().remove(fileToWriteTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void formatCD() throws Exception {
        createDialogWindow().formatDisk();
    }

    @FXML
    public void goToPrevDir() {
        fileSearcher.setPrevPath();
        refreshListOfFiles();
    }
}
