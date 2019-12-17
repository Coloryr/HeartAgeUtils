package Color_yr.HeartAge_year.Config.tpStone;

import Color_yr.HeartAge_year.Config.tpStone.Obj.Location;
import Color_yr.HeartAge_year.Config.tpStone.Obj.tpStone;

import java.util.ArrayList;
import java.util.List;

public class tpStone_set {

    public static final List<String> sel_list = new ArrayList<String>() {
        {
            sel_list.add("sel1");
            sel_list.add("sel2");
            sel_list.add("sel3");
            sel_list.add("sel4");
            sel_list.add("sel5");
            sel_list.add("sel6");
            sel_list.add("sel7");
            sel_list.add("sel8");
            sel_list.add("sel9");
        }
    };

    private final tpStone tpStone;

    public tpStone_set(tpStone tpStone) {
        this.tpStone = tpStone;
    }

    public boolean setsel(String sel, Location location) {//设置槽位的坐标
        if (!sel_list.contains(sel))
            return false;
        tpStone.getSel().put(sel, location);
        return true;
    }

    public Location getsel(String sel) {//读取槽位的坐标
        if (sel_list.contains(sel))
            return tpStone.getSel().get(sel);
        return null;
    }
}
