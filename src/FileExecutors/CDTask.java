package FileExecutors;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.io.IOException;

public class CDTask extends Thread {
    private ProgressBar progressBar;
    public CDTask (String operation, ProgressBar progressBar) {
        this.progressBar = progressBar;
        try {
            Process process = Runtime.getRuntime().exec(operation);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i<1000000 && process.isAlive(); i++) {
                        progressBar.setProgress(i/1000000.0);
                    }
                    progressBar.setProgress(1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
