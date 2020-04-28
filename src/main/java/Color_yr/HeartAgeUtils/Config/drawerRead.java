package Color_yr.HeartAgeUtils.Config;

import Color_yr.HeartAgeUtils.API.IConfig;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.tpStone.tpStoneSaveObj;
import Color_yr.HeartAgeUtils.tpStone.tpStoneDo;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

class drawerRead implements IConfig {
    private static File fileLocal;

    @Override
    public void init() {
        //文件初始化
        fileLocal = new File(HeartAgeUtils.plugin.getDataFolder().getParent() + "/HeartAgeUtils/Drawer");
        try {
            if (!fileLocal.exists())
                fileLocal.mkdirs();
            File[] file_list = fileLocal.listFiles((dir, name) -> name.endsWith(".json"));
            if (file_list != null) {
                InputStreamReader reader;
                BufferedReader bfreader;
                for (File temp : file_list) {
                    Gson json = new Gson();
                    reader = new InputStreamReader(new FileInputStream(temp), StandardCharsets.UTF_8);
                    bfreader = new BufferedReader(reader);
                    tpStoneSaveObj obj = json.fromJson(bfreader, tpStoneSaveObj.class);
                    tpStoneDo.toStoneSave.clear();
                    if (obj != null)
                        tpStoneDo.toStoneSave.put(temp.getName().replace(".json", ""), obj);
                    reader.close();
                    bfreader.close();
                }
            }
        } catch (Exception e) {
            HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c抽屉配置文件初始化失败");
            e.printStackTrace();
        }
    }

    public void save(Object stone, String uuid) {
        File saveFile = new File(fileLocal, uuid + ".json");
        HeartAgeUtils.ConfigMain.SaveTask.addTask(new saveTaskObj(stone, saveFile));
    }
}
