package FileExecutors;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CDTask extends Thread {
    private Label operationLabel;
    private ObservableList<FileInfo> list;
    private int numberOfCommand;
    private Stage stage;

    public CDTask(Label state, ObservableList<FileInfo> list, int command, Stage stage) {
        this.operationLabel = state;
        this.list = list;
        this.numberOfCommand = command;
        this.stage = stage;
    }

    private void setProgress(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                operationLabel.setText(text);
            }
        });
    }

    private void formatDisk () {
        startOperation("cdrecord -dev=1,0,0 -v -eject -blank=fast");
    }

    private void createTemp () {
        startOperation("mkdir /home/tony/temp");
    }

    private void startWriten () {
        createTemp();
        copyFiles(list);
        createISO();
        writeToDisk();
        deleteTemps(list);
    }

    private void deleteTemps (ObservableList<FileInfo> list) {
        startOperation("rm ./Disk.iso");
        startOperation("rm -rf /home/tony/temp");
    }

    private void startOperation(String operation) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", "echo "
                    + System.getenv("PASSWORD") + " |  "  + operation});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.contains(" written ")) {
                    setProgress("Waiting: " + line.substring(0, line.indexOf(" written ")));
                }
            }
        } catch (Exception r) {
            r.printStackTrace();
        }
    }

    private void copyFiles (ObservableList<FileInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            startOperation("cp -R " + list.get(i).getFile().getAbsolutePath() +  " /home/tony/temp");
        }
    }

    private void writeToDisk () {
        startOperation("cdrecord -dev=1,0,0 -eject -v Disk.iso");
    }

    private void createISO () {
        startOperation("mkisofs -v -J -o Disk.iso /home/tony/temp");
    }

    public void run () {
        switch (numberOfCommand) {
            case 1:
                startWriten();
                break;
            case 2:
                formatDisk();
                break;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.close();
            }
        });

    }
}