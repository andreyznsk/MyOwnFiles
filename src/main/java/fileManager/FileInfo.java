package fileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInfo {
    private String filename;
    private long fileLength;

    public String getFilename() {
        return filename;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public FileInfo(Path path) {
        this.filename = path.getFileName().toString();
        try {
            if (Files.isDirectory(path)) this.fileLength = -1;
            else {
                this.fileLength = Files.size(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

