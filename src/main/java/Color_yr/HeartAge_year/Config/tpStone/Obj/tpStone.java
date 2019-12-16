package Color_yr.HeartAge_year.Config.tpStone.Obj;

import java.util.HashMap;
import java.util.Map;

public class tpStone {
    private String name;
    private int slot;
    private Map<String, Localtion> sel = new HashMap<>();

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

    public Map<String, Localtion> getSel() {
        return sel;
    }
}


