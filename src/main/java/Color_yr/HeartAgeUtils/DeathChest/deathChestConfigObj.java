package Color_yr.HeartAgeUtils.DeathChest;

import java.util.ArrayList;
import java.util.List;

public class deathChestConfigObj {
    private costObj cost;
    private List<Integer> disable;
    private boolean enable;
    private boolean lock;

    public deathChestConfigObj() {
        cost = new costObj();
        disable = new ArrayList<>();
    }

    public boolean isLock() {
        return lock;
    }

    public boolean isEnable() {
        return enable;
    }

    public List<Integer> getDisable() {
        return disable;
    }

    public costObj getCost() {
        return cost;
    }
}
