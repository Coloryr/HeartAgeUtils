package Color_yr.HeartAge_year.Obj;

import java.util.HashMap;
import java.util.Map;

public class tpStone_save_Obj {//传送石储存对象
    private String name;
    private int slot;
    private Map<String, Location_Obj> sel = new HashMap<>();

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

    public Map<String, Location_Obj> getSel() {
        return sel;
    }
}


