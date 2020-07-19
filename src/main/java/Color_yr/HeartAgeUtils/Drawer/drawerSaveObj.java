package Color_yr.HeartAgeUtils.Drawer;

import Color_yr.HeartAgeUtils.Obj.posObj;
import org.bukkit.Location;
import org.bukkit.Material;

public class drawerSaveObj {
    private Material item;
    private String uuid;
    private posObj pos;
    private int amount;

    public drawerSaveObj(String uuid) {
        this.uuid = uuid;
        item = Material.AIR;
    }

    public boolean checkPos(Location location) {
        return pos.getX() == location.getBlockX() &&
                pos.getY() == location.getBlockY() &&
                pos.getZ() == location.getBlockZ();
    }

    public void setPos(posObj pos) {
        this.pos = pos;
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

    public void setItem(Material item) {
        this.item = item;
    }

    public String getUuid() {
        return uuid;
    }
}
