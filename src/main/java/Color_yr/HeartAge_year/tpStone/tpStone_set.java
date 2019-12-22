package Color_yr.HeartAge_year.tpStone;

import Color_yr.HeartAge_year.Obj.Location_Obj;
import Color_yr.HeartAge_year.Obj.tpStone_save_Obj;

import java.util.HashMap;
import java.util.Map;

public class tpStone_set {

    public static final Map<Integer, String> sel_list = new HashMap<Integer, String>() {
        {
            this.put(0, "sel1");
            this.put(1, "sel2");
            this.put(2, "sel3");
            this.put(3, "sel4");
            this.put(4, "sel5");
            this.put(5, "sel6");
            this.put(6, "sel7");
            this.put(7, "sel8");
            this.put(8, "sel9");
        }
    };

    private tpStone_save_Obj tpStone;

    public tpStone_set(tpStone_save_Obj tpStone_save_Obj) {
        tpStone = tpStone_save_Obj;
    }

    public void set_sel(int sel, Location_Obj locationObj) {//设置槽位的坐标
        if (!sel_list.containsKey(sel))
            return;
        tpStone.getSel().put(sel_list.get(sel), locationObj);
    }

    public Location_Obj get_sel(int sel) {//读取槽位的坐标
        if (sel_list.containsKey(sel))
            return tpStone.getSel().get(sel_list.get(sel));
        return null;
    }
}
