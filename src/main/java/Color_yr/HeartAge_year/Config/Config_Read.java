package Color_yr.HeartAge_year.Config;

import Color_yr.HeartAge_year.Obj.Config_Obj;
import Color_yr.HeartAge_year.tpStone.tpStone_do;
import Color_yr.HeartAge_year.HeartAge_year;
import com.google.gson.Gson;
import org.bukkit.Material;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Config_Read {

    public static Config_Obj main_config;   //主配置文件对象
    private File FileName;  //文件缓存

    private void Config_reload() {
        try {
            Gson read_gson = new Gson();
            InputStreamReader reader = new InputStreamReader(new FileInputStream(FileName), StandardCharsets.UTF_8);
            BufferedReader bfreader = new BufferedReader(reader);
            main_config = read_gson.fromJson(bfreader, Config_Obj.class);
            //读传送石物品
            /*
            if (!main_config.gettpStone().getmain().isEmpty()) {
                Material temp = Material.matchMaterial(main_config.gettpStone().getmain(), true);
                if (temp == null) {
                    HeartAge_year.log.warning("§d[HeartAge_year]§c手中物品找不到");
                } else {
                    tpStone_do.item = temp;
                }
            } else {
                HeartAge_year.log.warning("§d[HeartAge_year]§c手中物品ID为空");
            }
            //读传送石升级物品
            if (!main_config.gettpStone().getUpdata().isEmpty()) {
                Material temp = Material.matchMaterial(main_config.gettpStone().getUpdata(), true);
                if (temp == null) {
                    HeartAge_year.log.warning("§d[HeartAge_year]§c升级物品找不到");
                } else {
                    tpStone_do.updata_item = temp;
                }
            } else {
                HeartAge_year.log.warning("§d[HeartAge_year]§c升级物品ID为空");
            }

             */
            tpStone_do.item = Material.STONE;
            tpStone_do.updata_item = Material.STONE;
        } catch (Exception arg0) {
            HeartAge_year.log.warning("§d[HeartAge_year]§c配置文件 config.json 读取失败:" + arg0);
        }
    }

    public void setConfig() {
        //读取文件初始化
        FileName = new File(HeartAge_year.plugin.getDataFolder(), "config.json");
        if (!HeartAge_year.plugin.getDataFolder().exists())
            HeartAge_year.plugin.getDataFolder().mkdirs();
        if (!FileName.exists()) {
            try (InputStream in = HeartAge_year.plugin.getResource("config.json")) {
                Files.copy(in, FileName.toPath());
            } catch (IOException e) {
                HeartAge_year.log.warning("§d[HeartAge_year]§c配置文件 config.json 创建失败：" + e);
            }
        }
        Config_reload();
    }
}