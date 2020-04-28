package Color_yr.HeartAgeUtils.Config;

import Color_yr.HeartAgeUtils.API.IConfig;
import Color_yr.HeartAgeUtils.Drawer.drawerBlock;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.configObj;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.tpStone.tpStoneDo;
import com.google.gson.Gson;
import org.bukkit.Material;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class configMain {

    public static IConfig deathChest = new deathChestRead();
    public static IConfig drawer = new drawerRead();
    public static IConfig tpStone = new tpStoneRead();

    public configObj Config;   //主配置文件对象
    public languageObj lan;
    public saveTaskDo SaveTask;
    private File FileName;  //文件缓存

    public configMain() {
        SaveTask = new saveTaskDo();
        setConfig();
    }

    private void ConfigReload() {
        try {
            Gson json = new Gson();
            InputStreamReader reader = new InputStreamReader(new FileInputStream(FileName), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(reader);
            Config = json.fromJson(bf, configObj.class);
            lan = Config.getLanguage();
            reader.close();
            bf.close();

            tpStone.init();
            //读传送石物品
            if (!Config.getTpStone().getmain().isEmpty()) {
                Material a = Material.matchMaterial(Config.getTpStone().getmain());
                if (a == null) {
                    HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石手中物品找不到");
                } else {
                    tpStoneDo.item = a;
                }
            } else {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石手中物品ID为空");
            }
            //读传送石升级物品
            if (!Config.getTpStone().getUpdata().isEmpty()) {
                Material a = Material.matchMaterial(Config.getTpStone().getUpdata());
                if (a == null) {
                    HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石升级物品找不到");
                } else {
                    tpStoneDo.updateItem = a;
                }
            } else {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石升级物品ID为空");
            }
            //更新死亡箱子
            deathChest.init();
            if (!Config.getDrawer().getBlock().isEmpty()) {
                Material a = Material.matchMaterial(Config.getDrawer().getBlock());
                if (a == null) {
                    HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c抽屉方块找不到");
                } else {
                    drawerBlock.block = a;
                }
            } else {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c抽屉方块ID为空");
            }

            drawer.init();
        } catch (Exception arg0) {
            HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c配置文件读取失败");
            arg0.printStackTrace();
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