package FileExecutors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;

public class FileInfo {
    public StringProperty fileName;
    public StringProperty fileType;
    private File file;

    FileInfo (String name, String type, File file) {
        this.fileName = new SimpleStringProperty(name);
        this.fileType = new SimpleStringProperty(type);
        this.file = file;
    }

    public String getFileName() {
        return fileName.get();
    }

    public String getFileType() {
        return fileType.get();
    }

    public File getFile() {
        return file;
    }
}
