package Color_yr.HeartAgeUtils.Obj;

import Color_yr.HeartAgeUtils.DeathChest.deathChestConfigObj;
import Color_yr.HeartAgeUtils.Drawer.drawerObj;
import Color_yr.HeartAgeUtils.tpStone.tpStoneConfigObj;

public class configObj {//主配置对象

    private tpStoneConfigObj tpStone;
    private drawerObj drawer;
    private languageObj language;
    private deathChestConfigObj deathChest;

    public tpStoneConfigObj getTpStone() {
        return tpStone;
    }

    public languageObj getLanguage() {
        return language;
    }

    public drawerObj getDrawer() {
        return drawer;
    }

    public deathChestConfigObj getDeathChest() {
        return deathChest;
    }
}
