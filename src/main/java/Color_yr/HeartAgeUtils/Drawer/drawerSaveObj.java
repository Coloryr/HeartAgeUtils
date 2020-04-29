package Color_yr.HeartAgeUtils.Drawer;

import org.bukkit.Material;

public class drawerSaveObj {
    private Material item;
    private String uuid;
    private int amount;

    public drawerSaveObj(String uuid) {
        this.uuid = uuid;
        item = Material.AIR;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Material getItem() {
        return item;
    }

    public String getUuid() {
        return uuid;
    }

    public void setItem(Material item) {
        this.item = item;
    }
}
