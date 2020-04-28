package Color_yr.HeartAgeUtils.tpStone;

import Color_yr.HeartAgeUtils.Obj.posObj;

public class locationObj extends posObj {//传送石坐标储存对象

    private String name;

    public locationObj(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}