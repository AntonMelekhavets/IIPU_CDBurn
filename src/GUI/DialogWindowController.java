package GUI;

import FileExecutors.CDTask;
import FileExecutors.FileInfo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class DialogWindowController {
    @FXML
    public Label operationLabel;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button cancelButton;
    private Stage stage;
    private String [] operation;

    public void setStage (Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize () {
    }

    public void startWriten (ObservableList<FileInfo> list) {
        operationLabel.setText("In progress...");
        CDTask task = new CDTask(operationLabel, list, 1, stage);
        task.start();
    }

    public void formatDisk () {
        operationLabel.setText("In progress...");
        CDTask task = new CDTask(operationLabel, null, 2, stage);
        task.start();
    }

    @FXML
    public void finishOperation() {
        stage.close();
    }

}
