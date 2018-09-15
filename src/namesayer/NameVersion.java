package namesayer;

import java.io.File;

public class NameVersion extends Name{

    private File file;

    public NameVersion(String name, File file) {
        super(name);

        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
