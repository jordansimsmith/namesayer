package namesayer;

import java.io.File;

public class NameVersion {

    private String name;
    private File file;

    public NameVersion(String name, File file) {

        this.name = name;
        this.file = file;
    }

    @Override
    public String toString() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }
}
