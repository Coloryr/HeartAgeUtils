package Color_yr.HeartAgeUtils.DeathChest;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.posObj;

public class playSetObj extends posObj {
    private int mode;

    public int getMode() {
        int b = 0;
        if (HeartAgeUtils.configMain.Config.getDeathChest().getDisable().contains(mode)) {
            for (int a : HeartAgeUtils.configMain.Config.getDeathChest().getDisable()) {
                if (a == b)
                    b++;
                else {
                    mode = b;
                    break;
                }
            }
        }
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
