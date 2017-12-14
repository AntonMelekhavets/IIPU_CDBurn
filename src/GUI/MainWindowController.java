package GUI;

import FileExecutors.FileInfo;
import FileExecutors.FileSearcher;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private void initialize() {
        refreshListOfFiles();
        fileListTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> goNextPath(newValue));
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().fileName);
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().fileType);
    }

    @FXML
    public void delFromWriteList() {
        fileToWriteTable.getItems().remove(fileToWriteTable.getSelectionModel().getSelectedItem());
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
    public void addFileToWrite(){
        fileToWriteTable.getItems().add(fileListTableView.getSelectionModel().getSelectedItem());
        typeToWrite.setCellValueFactory(cellData -> cellData.getValue().fileType);
        nameToWrite.setCellValueFactory(cellData -> cellData.getValue().fileName);
    }

    @FXML
    public void formatCD(){

    }

    @FXML
    public void goToPrevDir() {
        fileSearcher.setPrevPath();
        refreshListOfFiles();
    }
}
