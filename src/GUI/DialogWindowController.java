package GUI;

import FileExecutors.CDTask;
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

    public void setStage (Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize () {
        CDTask cdTask = new CDTask("lsusb", progressBar);
        cdTask.start();
    }

    @FXML
    public void finishOperation() {
        stage.close();
    }

}
