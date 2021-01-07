package nehe.sharenews.models;

import java.io.File;

public class RawPost {

    String description;

    File file;

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public RawPost() {
    }

    public RawPost(String description, File file) {
        this.description = description;
        this.file = file;
    }

    @Override
    public String toString() {
        return "RawPost [description=" + description + ", file=" + file + "]";
    }

    
    
}
