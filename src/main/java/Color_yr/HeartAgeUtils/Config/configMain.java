package Color_yr.HeartAgeUtils.Config;

import Color_yr.HeartAgeUtils.API.IConfig;
import Color_yr.HeartAgeUtils.Drawer.drawerDo;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.configObj;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.OreGen.oreGenDo;
import Color_yr.HeartAgeUtils.OreGen.oreGenObj;
import Color_yr.HeartAgeUtils.tpStone.tpStoneDo;
import com.google.gson.Gson;
import org.bukkit.Material;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

public class configMain {

    public static IConfig deathChest = new deathChestRead();
    public static IConfig drawer = new drawerRead();
    public static IConfig tpStone = new tpStoneRead();
    public static IConfig oreGenRead = new oreGenRead();

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
                    HeartAgeUtils.log.info("§d[HeartAgeUtils]§c传送石物品:" + tpStoneDo.item.toString());
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
                    HeartAgeUtils.log.info("§d[HeartAgeUtils]§c传送石升级物品:" + tpStoneDo.updateItem.toString());
                }
            } else {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石升级物品ID为空");
            }
            if (!Config.getDrawer().getBlock().isEmpty()) {
                Material a = Material.matchMaterial(Config.getDrawer().getBlock());
                if (a == null) {
                    HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c抽屉方块找不到");
                } else {
                    drawerDo.block = a;
                    HeartAgeUtils.log.info("§d[HeartAgeUtils]§c抽屉方块:" + drawerDo.block.toString());
                }
            } else {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c抽屉方块ID为空");
            }

            //更新死亡箱子
            deathChest.init();

            //抽屉初始化
            drawer.init();

            //矿物生成初始化
            oreGenRead.init();
            oreGenObj obj = Config.getOreGen();
            if (obj.getCount() < obj.getCoal() + obj.getIron() + obj.getGold() + obj.getDiamond()
                    + obj.getRedStone() + obj.getLapis() + obj.getEmerald() + obj.getNetherglod()
                    + obj.getGlowstone() + obj.getQuartz()) {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c矿石配置错误");
                return;
            }
            oreGenDo.itemStacks = new ArrayList<>();
            int index = 0;
            oreGenDo.Blocks.clear();
            for (String item : obj.getBlock()) {
                Material a = Material.matchMaterial(item);
                if (a != null) {
                    oreGenDo.Blocks.add(a);
                }
            }
            for (int i = 0; i < obj.getCoal(); i++, index++) {
                oreGenDo.itemStacks.add(Material.COAL_ORE);
            }
            for (int i = 0; i < obj.getIron(); i++, index++) {
                oreGenDo.itemStacks.add(Material.IRON_ORE);
            }
            for (int i = 0; i < obj.getGold(); i++, index++) {
                oreGenDo.itemStacks.add(Material.GOLD_ORE);
            }
            for (int i = 0; i < obj.getDiamond(); i++, index++) {
                oreGenDo.itemStacks.add(Material.DIAMOND_ORE);
            }
            for (int i = 0; i < obj.getRedStone(); i++, index++) {
                oreGenDo.itemStacks.add(Material.REDSTONE_ORE);
            }
            for (int i = 0; i < obj.getLapis(); i++, index++) {
                oreGenDo.itemStacks.add(Material.LAPIS_ORE);
            }
            for (int i = 0; i < obj.getEmerald(); i++, index++) {
                oreGenDo.itemStacks.add(Material.EMERALD_ORE);
            }
            for (int i = 0; i < obj.getNetherglod(); i++, index++) {
                oreGenDo.itemStacks.add(Material.NETHER_GOLD_ORE);
            }
            for (int i = 0; i < obj.getGlowstone(); i++, index++) {
                oreGenDo.itemStacks.add(Material.GLOWSTONE);
            }
            for (int i = 0; i < obj.getQuartz(); i++, index++) {
                oreGenDo.itemStacks.add(Material.NETHER_QUARTZ_ORE);
            }
            for (; index < obj.getCount(); index++) {
                oreGenDo.itemStacks.add(Material.AIR);
            }
            Collections.shuffle(oreGenDo.itemStacks);

            oreGenDo.lan.clear();
            oreGenDo.lan.put(Material.COAL_ORE, lan.getHelpOreGenType().get(0));
            oreGenDo.lan.put(Material.IRON_ORE, lan.getHelpOreGenType().get(1));
            oreGenDo.lan.put(Material.GOLD_ORE, lan.getHelpOreGenType().get(2));
            oreGenDo.lan.put(Material.DIAMOND_ORE, lan.getHelpOreGenType().get(3));
            oreGenDo.lan.put(Material.REDSTONE_ORE, lan.getHelpOreGenType().get(4));
            oreGenDo.lan.put(Material.LAPIS_ORE, lan.getHelpOreGenType().get(5));
            oreGenDo.lan.put(Material.EMERALD_ORE, lan.getHelpOreGenType().get(6));
            oreGenDo.lan.put(Material.NETHER_GOLD_ORE, lan.getHelpOreGenType().get(7));
            oreGenDo.lan.put(Material.GLOWSTONE, lan.getHelpOreGenType().get(8));
            oreGenDo.lan.put(Material.NETHER_QUARTZ_ORE, lan.getHelpOreGenType().get(9));

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