package Color_yr.HeartAgeUtils.enchantment;

public class enchantmentObj {

    private final int breakExp;
    private final int breakCost;
    private final int addExp;
    private final int addCost;

    public enchantmentObj() {
        breakExp = 5000;
        breakCost = 2000;
        addExp = 15000;
        addCost = 20000;
    }

    public int getAddCost() {
        return addCost;
    }

    public int getAddExp() {
        return addExp;
    }

    public int getBreakCost() {
        return breakCost;
    }

    public int getBreakExp() {
        return breakExp;
    }
}



