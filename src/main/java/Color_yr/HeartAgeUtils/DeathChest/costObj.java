package Color_yr.HeartAgeUtils.DeathChest;

public class costObj {
    private int SaveInChest;
    private int SaveInLocal;
    private int NoDrop;
    private boolean Enable;

    public costObj() {
        Enable = true;
        SaveInChest = 100;
        SaveInLocal = 50;
        NoDrop = 500;
    }

    public boolean isEnable() {
        return Enable;
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
