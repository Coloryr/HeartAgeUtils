package Color_yr.HeartAge_year.Config;

import Color_yr.HeartAge_year.HeartAge_year;
import Color_yr.HeartAge_year.Obj.Config_Obj;
import Color_yr.HeartAge_year.Obj.Language_Obj;
import Color_yr.HeartAge_year.respawnStone.respawnStone_do;
import Color_yr.HeartAge_year.tpStone.tpStone_do;
import com.google.gson.Gson;
import org.bukkit.Material;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Config_Read {

    public static Config_Obj main_config;   //主配置文件对象
    public static Language_Obj lan;
    private File FileName;  //文件缓存

    private void Config_reload() {
        try {
            Gson json = new Gson();
            InputStreamReader reader = new InputStreamReader(new FileInputStream(FileName), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(reader);
            main_config = json.fromJson(bf, Config_Obj.class);
            lan = main_config.getLanguage();
            reader.close();
            bf.close();
            //读传送石物品
            if (!main_config.gettpStone().getmain().isEmpty()) {
                Material a = Material.matchMaterial(main_config.gettpStone().getmain());
                if (a == null) {
                    HeartAge_year.log.warning("§d[HeartAge_year]§c传送石手中物品找不到");
                } else {
                    tpStone_do.item = a;
                }
            } else {
                HeartAge_year.log.warning("§d[HeartAge_year]§c传送石手中物品ID为空");
            }
            //读传送石升级物品
            if (!main_config.gettpStone().getUpdata().isEmpty()) {
                Material a = Material.matchMaterial(main_config.gettpStone().getUpdata());
                if (a == null) {
                    HeartAge_year.log.warning("§d[HeartAge_year]§c传送石升级物品找不到");
                } else {
                    tpStone_do.update_item = a;
                }
            } else {
                HeartAge_year.log.warning("§d[HeartAge_year]§c传送石升级物品ID为空");
            }
            //读复活石物品
            if (!main_config.getRespawnStone().getMain().isEmpty()) {
                Material a = Material.matchMaterial(main_config.getRespawnStone().getMain());
                if (a == null) {
                    HeartAge_year.log.warning("§d[HeartAge_year]§c复活石物品找不到");
                } else {
                    respawnStone_do.item = a;
                }
            } else {
                HeartAge_year.log.warning("§d[HeartAge_year]§c复活石物品ID为空");
            }

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