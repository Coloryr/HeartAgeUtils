package Color_yr.HeartAgeUtils.Obj;

import java.util.HashMap;
import java.util.Map;

public class tpStoneSaveObj {//传送石储存对象
    private String name;
    private int slot;
    private Map<String, LocationObj> sel = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Map<String, LocationObj> getSel() {
        return sel;
    }
}


