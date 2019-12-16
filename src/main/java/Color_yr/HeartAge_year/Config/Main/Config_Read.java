package Color_yr.HeartAge_year.Config.Main;

import Color_yr.HeartAge_year.Config.tpStone.tpStone_do;
import Color_yr.HeartAge_year.HeartAge_year;
import com.google.gson.Gson;
import org.bukkit.Material;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Config_Read {

    public static Config_Obj main_config;
    private File FileName;

    private void Config_reload() {
        try {
            Gson read_gson = new Gson();
            InputStreamReader reader = new InputStreamReader(new FileInputStream(FileName), StandardCharsets.UTF_8);
            BufferedReader bfreader = new BufferedReader(reader);
            main_config = read_gson.fromJson(bfreader, Config_Obj.class);
            if (!main_config.getItem().getHand().isEmpty()) {
                Material temp = Material.matchMaterial(main_config.getItem().getHand());
                if (temp == null) {
                    HeartAge_year.log.warning("§d[HeartAge_year]§c手中物品找不到");
                } else {
                    tpStone_do.item = temp;
                }
            } else {
                HeartAge_year.log.warning("§d[HeartAge_year]§c手中物品ID为空");
            }
            if (!main_config.getItem().getUpdata().isEmpty()) {
                Material temp = Material.matchMaterial(main_config.getItem().getUpdata());
                if (temp == null) {
                    HeartAge_year.log.warning("§d[HeartAge_year]§c升级物品找不到");
                } else {
                    tpStone_do.updata_item = temp;
                }
            } else {
                HeartAge_year.log.warning("§d[HeartAge_year]§c升级物品ID为空");
            }
        } catch (Exception arg0) {
            HeartAge_year.log.warning("§d[HeartAge_year]§c配置文件 config.yml 读取失败:" + arg0);
        }
    }

    public void setConfig() {
        FileName = new File(HeartAge_year.plugin.getDataFolder(), "config.yml");
        if (!HeartAge_year.plugin.getDataFolder().exists())
            HeartAge_year.plugin.getDataFolder().mkdirs();
        if (!FileName.exists()) {
            try (InputStream in = HeartAge_year.plugin.getResource("config.json")) {
                Files.copy(in, FileName.toPath());
            } catch (IOException e) {
                HeartAge_year.log.warning("§d[HeartAge_year]§c配置文件 config.yml 创建失败：" + e);
            }
        }
        Config_reload();
    }
}
