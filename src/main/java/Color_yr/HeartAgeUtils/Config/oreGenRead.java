package Color_yr.HeartAgeUtils.Config;

import Color_yr.HeartAgeUtils.API.IConfig;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.OreGen.oreGenDo;
import Color_yr.HeartAgeUtils.OreGen.oreGenObj;
import Color_yr.HeartAgeUtils.OreGen.oreGenSaveobj;
import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class oreGenRead implements IConfig {
    private static File fileLocal;

    @Override
    public void init() {
        //文件初始化
        fileLocal = new File(HeartAgeUtils.plugin.getDataFolder().getParent() + "/HeartAgeUtils/oreGen");
        try {
            if (!fileLocal.exists())
                fileLocal.mkdirs();
            fileLocal = new File(fileLocal, "oreSave.json");
            if (!fileLocal.exists()) {
                oreGenDo.saveobj = new oreGenSaveobj();
                if (!fileLocal.exists())
                    fileLocal.createNewFile();
                OutputStreamWriter write = new OutputStreamWriter(
                        new FileOutputStream(fileLocal), StandardCharsets.UTF_8);
                write.write(new Gson().toJson(oreGenDo.saveobj));
                write.close();
            } else {
                InputStreamReader reader;
                BufferedReader bfreader;
                Gson json = new Gson();
                reader = new InputStreamReader(new FileInputStream(fileLocal), StandardCharsets.UTF_8);
                bfreader = new BufferedReader(reader);
                oreGenDo.saveobj = json.fromJson(bfreader, oreGenSaveobj.class);
                reader.close();
                bfreader.close();
            }
        } catch (Exception e) {
            HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c矿物生成配置文件初始化失败");
            e.printStackTrace();
        }
    }

    @Override
    public void save(Object stone, String uuid) {
        HeartAgeUtils.configMain.SaveTask.addTask(new saveTaskObj(oreGenDo.saveobj, fileLocal));
    }
}
