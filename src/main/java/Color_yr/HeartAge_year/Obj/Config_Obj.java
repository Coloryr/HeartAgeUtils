package Color_yr.HeartAge_year.Obj;

public class Config_Obj {//主配置对象

    private Config_tpStone_Obj tpStone;
    private respawnStone_Obj respawnStone;
    private Language_Obj language;
    private Death_Obj Death;

    public respawnStone_Obj getRespawnStone() {
        return respawnStone;
    }

    public Death_Obj getDeath() {
        return Death;
    }

    public Config_tpStone_Obj gettpStone() {
        return tpStone;
    }

    public Language_Obj getLanguage() {
        return language;
    }
}
