package FileExecutors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileInfo {
    public StringProperty fileName;
    public StringProperty fileType;

    FileInfo (String name, String path) {
        this.fileName = new SimpleStringProperty(name);
        this.fileType = new SimpleStringProperty(path);
    }

    public String getFileName() {
        return fileName.get();
    }

    public String getFileType() {
        return fileType.get();
    }
}
