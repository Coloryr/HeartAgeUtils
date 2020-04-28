package Color_yr.HeartAgeUtils.DeathChest;

public class costObj {
    private final int SaveInChest;
    private final int SaveInLocal;
    private final int NoDrop;
    private final boolean Enable;

    public boolean isEnable() {
        return Enable;
    }

    public costObj() {
        Enable = true;
        SaveInChest = 100;
        SaveInLocal = 50;
        NoDrop = 500;
    }

    public int getNoDrop() {
        return NoDrop;
    }

    public int getSaveInChest() {
        return SaveInChest;
    }

    public int getSaveInLocal() {
        return SaveInLocal;
    }
}
