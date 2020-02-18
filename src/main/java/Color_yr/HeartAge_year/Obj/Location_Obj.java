package Color_yr.HeartAge_year.Obj;

public class Location_Obj {//传送石坐标储存对象

    private String name;
    private int x = 0;
    private int y = 0;
    private int z = 0;

    public Location_Obj(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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