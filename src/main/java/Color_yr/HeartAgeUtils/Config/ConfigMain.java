package Color_yr.HeartAgeUtils.Config;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.ConfigObj;
import Color_yr.HeartAgeUtils.Obj.LanguageObj;
import Color_yr.HeartAgeUtils.tpStone.tpStoneDo;
import com.google.gson.Gson;
import org.bukkit.Material;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConfigMain {

    public ConfigObj Config;   //主配置文件对象
    public LanguageObj lan;
    public SaveTaskDo SaveTask;
    private File FileName;  //文件缓存

    public ConfigMain() {
        SaveTask = new SaveTaskDo();
        setConfig();
    }

    private void ConfigReload() {
        try {
            Gson json = new Gson();
            InputStreamReader reader = new InputStreamReader(new FileInputStream(FileName), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(reader);
            Config = json.fromJson(bf, ConfigObj.class);
            lan = Config.getLanguage();
            reader.close();
            bf.close();
            //读传送石物品
            if (!Config.gettpStone().getmain().isEmpty()) {
                Material a = Material.matchMaterial(Config.gettpStone().getmain());
                if (a == null) {
                    HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石手中物品找不到");
                } else {
                    tpStoneDo.item = a;
                }
            } else {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石手中物品ID为空");
            }
            //读传送石升级物品
            if (!Config.gettpStone().getUpdata().isEmpty()) {
                Material a = Material.matchMaterial(Config.gettpStone().getUpdata());
                if (a == null) {
                    HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石升级物品找不到");
                } else {
                    tpStoneDo.updateItem = a;
                }
            } else {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石升级物品ID为空");
            }

        } catch (Exception arg0) {
            HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c配置文件 config.json 读取失败:" + arg0);
        }
    }

    public void setConfig() {
        //读取文件初始化
        FileName = new File(HeartAgeUtils.plugin.getDataFolder(), "config.json");
        if (!HeartAgeUtils.plugin.getDataFolder().exists())
            HeartAgeUtils.plugin.getDataFolder().mkdirs();
        if (!FileName.exists()) {
            try (InputStream in = HeartAgeUtils.plugin.getResource("config.json")) {
                Files.copy(in, FileName.toPath());
            } catch (IOException e) {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c配置文件 config.json 创建失败：" + e);
            }
        }
        ConfigReload();
    }
}