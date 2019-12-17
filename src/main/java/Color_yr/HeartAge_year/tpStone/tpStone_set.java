package Color_yr.HeartAge_year.tpStone;

import Color_yr.HeartAge_year.Obj.Location_Obj;
import Color_yr.HeartAge_year.Obj.tpStone_save_Obj;

import java.util.ArrayList;
import java.util.List;

public class tpStone_set {

    public static final List<String> sel_list = new ArrayList<String>() {
        {
            this.add("sel1");
            this.add("sel2");
            this.add("sel3");
            this.add("sel4");
            this.add("sel5");
            this.add("sel6");
            this.add("sel7");
            this.add("sel8");
            this.add("sel9");
        }
    };

    private tpStone_save_Obj tpStone;

    public tpStone_set(tpStone_save_Obj tpStone_save_Obj) {
        tpStone = tpStone_save_Obj;
    }

    public void setsel(String sel, Location_Obj locationObj) {//设置槽位的坐标
        if (!sel_list.contains(sel))
            return;
        tpStone.getSel().put(sel, locationObj);
    }

    public Location_Obj getsel(String sel) {//读取槽位的坐标
        if (sel_list.contains(sel))
            return tpStone.getSel().get(sel);
        return null;
    }
}
