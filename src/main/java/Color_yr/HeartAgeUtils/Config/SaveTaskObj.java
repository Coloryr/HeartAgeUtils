package Color_yr.HeartAgeUtils.Config;

import java.io.File;

public class SaveTaskObj {
    private Object saveobj;
    private File file;

    public SaveTaskObj(Object saveobj, File file) {
        this.saveobj = saveobj;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public Object getSaveobj() {
        return saveobj;
    }
}
