package Color_yr.HeartAgeUtils.DeathChest;

import java.util.List;

public class deathChestConfigObj {
    private final costObj cost;
    private List<Integer> disable;

    public deathChestConfigObj() {
        cost = new costObj();
    }

    public List<Integer> getDisable() {
        return disable;
    }

    public costObj getCost() {
        return cost;
    }
}
