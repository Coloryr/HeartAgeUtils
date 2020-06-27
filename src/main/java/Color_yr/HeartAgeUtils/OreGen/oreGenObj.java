package Color_yr.HeartAgeUtils.OreGen;

import java.util.List;

public class oreGenObj {
    private boolean Enable;

    private int Count;

    private int Coal;
    private int Iron;
    private int Gold;
    private int Diamond;
    private int RedStone;
    private int Lapis;
    private int emerald;
    private int netherglod;
    private int glowstone;
    private int quartz;

    private int ExtCost;

    private List<String> Block;

    public boolean isEnable() {
        return Enable;
    }

    public int getExtCost() {
        return ExtCost;
    }

    public int getEmerald() {
        return emerald;
    }

    public int getGlowstone() {
        return glowstone;
    }

    public int getLapis() {
        return Lapis;
    }

    public int getNetherglod() {
        return netherglod;
    }

    public int getQuartz() {
        return quartz;
    }

    public int getRedStone() {
        return RedStone;
    }

    public int getCoal() {
        return Coal;
    }

    public int getCount() {
        return Count;
    }

    public int getDiamond() {
        return Diamond;
    }

    public int getGold() {
        return Gold;
    }

    public int getIron() {
        return Iron;
    }

    public List<String> getBlock() {
        return Block;
    }
}
