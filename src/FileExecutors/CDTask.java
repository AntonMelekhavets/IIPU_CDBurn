package FileExecutors;

import GUI.DialogWindowController;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CDTask extends Thread {
    private DialogWindowController controller;
    private int CDSize = 700000000;
    private boolean flag = false;

    public CDTask(DialogWindowController controller) {
        this.controller = controller;
    }

    private void formatDisk() {
        setProgress("Formatting");
        startOperation("cdrecord -dev=1,0,0 -v -blank=fast");

    }

    private void createTemp() {
        setProgress("Creating temp");
        startOperation("mkdir /home/tony/temp");

    }

    private void deleteTemps(ObservableList<FileInfo> list) {
        setProgress("Deleting temp files");
        startOperation("rm ./Disk.iso");
        startOperation("rm -rf /home/tony/temp");
    }

    private void umountDisk() {
        startOperation("umount /dev/sr0");
    }

    private Process writeToDisk() {
        flag = true;
        return startOperation("cdrecord -dev=1,0,0 -eject -v Disk.iso");
    }

    private void createISO() {
        setProgress("Creating ISO");
        startOperation("mkisofs -v -J -o Disk.iso /home/tony/temp");
    }

    private void copyFiles(ObservableList<FileInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            startOperation("cp -R " + list.get(i).getFile().getAbsolutePath() + " /home/tony/temp");
        }
    }

    private Process startOperation(String operation) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", "echo "
                    + System.getenv("PASSWORD") + " |  " + operation});
            return process;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void checkProgress(Process process) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(" written ")) {
                    setProgress("Waiting: " + line.substring(0, line.indexOf(" written ")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startWriten() {
        createTemp();
        copyFiles(controller.getFileList());
        if (checkDisk()) {
            createISO();
            formatDisk();
            checkProgress(writeToDisk());
        } else {
            setProgress("Error! CDRom not founded or not enough memory!");
        }
        deleteTemps(controller.getFileList());
        umountDisk();
    }

    private boolean checkDisk() {
        int occupiedSpace = execCommand("df -k | grep 'sr0' | awk '{print $3}'");
        if (occupiedSpace < 0)
            return false;
        if (execCommand("du -k -s /home/tony/temp | awk '{print $1}'") > CDSize - occupiedSpace)
            return false;
        return true;
    }

    private int execCommand(String command) {
        try {
            ProcessBuilder bash = new ProcessBuilder("bash", "-c", command);
            Process process = bash.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            if ((line = reader.readLine()) != null)
                return Integer.parseInt(line.toString());
            else
                return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setProgress(String text) {
        Platform.runLater(() -> {
            controller.getOperationLabel().setText(text);
        });
    }

    public void run() {
        startWriten();
        Platform.runLater(() -> {
            controller.finishOperation();
        });
    }
}