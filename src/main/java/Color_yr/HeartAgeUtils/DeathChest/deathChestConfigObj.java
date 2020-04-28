package Color_yr.HeartAgeUtils.DeathChest;

import java.util.List;

public class deathChestConfigObj {
    private final costObj Cost;
    private List<Integer> Disable;

    public List<Integer> getDisable() {
        return Disable;
    }

    public deathChestConfigObj() {
        Cost = new costObj();
    }

    public costObj getCost() {
        return Cost;
    }
}
