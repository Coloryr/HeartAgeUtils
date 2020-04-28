package Color_yr.HeartAgeUtils.DeathChest;

import Color_yr.HeartAgeUtils.HeartAgeUtils;

public class playSetObj {
    private int x;
    private int y;
    private int z;
    private int mode;

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getMode() {
        int b = 0;
        if (HeartAgeUtils.ConfigMain.Config.getDeathChest().getDisable().contains(mode)) {
            for (int a : HeartAgeUtils.ConfigMain.Config.getDeathChest().getDisable()) {
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
