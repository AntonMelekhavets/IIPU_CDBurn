package FileExecutors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.File;

public class FileSearcher {
    private String currentPath = "E:\\";
    private String prevPath = "E:\\";
    private ObservableList<FileInfo> fileInfoList = FXCollections.observableArrayList();

    private void getListOfFiles() {
        File fileList [] = new File(currentPath).listFiles();
        for (int i = 0; i<fileList.length; i++) {
            String fileType;
            if (fileList[i].isFile())
                fileType = "file";
            else
                fileType = "dir";
            fileInfoList.add(new FileInfo(fileList[i].getName(), fileType));
        }
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public ObservableList<FileInfo> getFileInfoList() {
        fileInfoList.clear();
        getListOfFiles();
        return fileInfoList;
    }

    public void setCurrentPath(String fileName) {
        this.prevPath = this.currentPath;
        this.currentPath = this.currentPath + fileName + "\\";
    }

    public String getPrevPath() {
        return prevPath;
    }
}
