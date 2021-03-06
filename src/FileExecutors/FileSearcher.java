package FileExecutors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class FileSearcher {
    private String currentPath = "/";
    private String prevPath = "/";
    private ObservableList<FileInfo> fileInfoList = FXCollections.observableArrayList();

    private void getListOfFiles() {
        File fileList[] = new File(currentPath).listFiles();
        for (int i = 0; i < fileList.length; i++) {
            String fileType;
            if (fileList[i].isFile())
                fileType = "file";
            else
                fileType = "dir";
            fileInfoList.add(new FileInfo(fileList[i].getName(), fileType, fileList[i]));
        }
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String filePath) {
        this.prevPath = this.currentPath;
        this.currentPath = this.currentPath + filePath + "/";
    }

    public ObservableList<FileInfo> getFileInfoList() {
        fileInfoList.clear();
        getListOfFiles();
        return fileInfoList;
    }

    public void setCurrentPath() {
        this.currentPath = this.prevPath;
    }
}
