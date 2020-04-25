package Color_yr.HeartAgeUtils.tpStone;

import Color_yr.HeartAgeUtils.Obj.LocationObj;
import Color_yr.HeartAgeUtils.Obj.tpStoneSaveObj;

import java.util.HashMap;
import java.util.Map;

public class tpStoneObjSet {

    public static final Map<Integer, String> selList = new HashMap<Integer, String>() {
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

    private final tpStoneSaveObj tpStone;

    public tpStoneObjSet(tpStoneSaveObj tpStone_save_Obj) {
        tpStone = tpStone_save_Obj;
    }

    public void setSel(int sel, LocationObj locationObj) {//设置槽位的坐标
        if (!selList.containsKey(sel))
            return;
        tpStone.getSel().put(selList.get(sel), locationObj);
    }

    public LocationObj getSel(int sel) {//读取槽位的坐标
        if (selList.containsKey(sel))
            return tpStone.getSel().get(selList.get(sel));
        return null;
    }
}
