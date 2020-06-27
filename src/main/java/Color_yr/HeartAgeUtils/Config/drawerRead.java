package Color_yr.HeartAgeUtils.Config;

import Color_yr.HeartAgeUtils.API.IConfig;
import Color_yr.HeartAgeUtils.Drawer.drawerDo;
import Color_yr.HeartAgeUtils.Drawer.drawerSaveObj;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class drawerRead implements IConfig {
    private File fileLocal;

    @Override
    public void init() {
        //文件初始化
        fileLocal = new File(HeartAgeUtils.plugin.getDataFolder().getParent() + "/HeartAgeUtils/Drawer");
        try {
            if (!fileLocal.exists())
                fileLocal.mkdirs();
            File[] files = fileLocal.listFiles((dir, name) -> name.endsWith(".json"));
            if (files != null) {
                InputStreamReader reader;
                BufferedReader bfreader;
                for (File temp : files) {
                    Gson json = new Gson();
                    reader = new InputStreamReader(new FileInputStream(temp), StandardCharsets.UTF_8);
                    bfreader = new BufferedReader(reader);
                    drawerSaveObj obj = json.fromJson(bfreader, drawerSaveObj.class);
                    drawerDo.drawerList.clear();
                    if (obj != null)
                        drawerDo.drawerList.put(temp.getName().replace(".json", ""), obj);
                    reader.close();
                    bfreader.close();
                }
            }
        } catch (Exception e) {
            HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c抽屉配置文件初始化失败");
            e.printStackTrace();
        }
    }

    @Override
    public void save(Object stone, String uuid) {
        File saveFile = new File(fileLocal, uuid + ".json");
        HeartAgeUtils.configMain.SaveTask.addTask(new saveTaskObj(stone, saveFile));
    }
}
