package GUI;

import FileExecutors.CDTask;
import FileExecutors.FileInfo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.File;

public class DialogWindowController {
    @FXML
    public Label operationLabel;
    @FXML
    public Button cancelButton;
    private Stage stage;
    private ObservableList<FileInfo> fileList = null;
    private int sizeToWrite;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
    }

    public void startWriten(ObservableList<FileInfo> list) {
        fileList = list;
        operationLabel.setText("In progress...");
        CDTask task = new CDTask(this);
        task.start();
    }

    public void formatDisk() {
        fileList = null;
        operationLabel.setText("In progress...");
        CDTask task = new CDTask(this);
        task.start();
    }

    public Label getOperationLabel() {
        return operationLabel;
    }

    public ObservableList<FileInfo> getFileList() {
        return fileList;
    }

    public int getSizeToWrite() {
        return sizeToWrite;
    }

    @FXML
    public void finishOperation() {
        stage.close();
    }

}
