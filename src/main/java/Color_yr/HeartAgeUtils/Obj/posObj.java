package Color_yr.HeartAgeUtils.Obj;

import org.bukkit.Location;

public class posObj {
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private String world;

    public posObj(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        world = "world";
    }

    public posObj() {
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
