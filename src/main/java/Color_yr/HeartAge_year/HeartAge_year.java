package Color_yr.HeartAge_year;

import Color_yr.HeartAge_year.Commder.HeartAge_Commder;
import Color_yr.HeartAge_year.Commder.toStone_Commder;
import Color_yr.HeartAge_year.Config.Main.Config_Read;
import Color_yr.HeartAge_year.Config.tpStone.tpStone_Read;
import Color_yr.HeartAge_year.Event.Block_event;
import Color_yr.HeartAge_year.Event.Item_event;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class HeartAge_year extends JavaPlugin {

    private static final String Version = "1.0.0";
    public static Plugin plugin;
    public static Logger log;

    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();
        log.info("§d[HeartAge_year]§e正在启动，感谢使用，本插件交流群：571239090");
        log.info("§d[HeartAge_year]§e读取配置文件中");
        new Config_Read().setConfig();//读取主配置文件
        new tpStone_Read().init();//读取传送石储存
        log.info("§d[HeartAge_year]§e注册指令中");
        Bukkit.getPluginCommand("heartage").setExecutor(new HeartAge_Commder());//注册插件主指令
        Bukkit.getPluginCommand("tpStone").setExecutor(new toStone_Commder());//注册传送石指令
        log.info("§d[HeartAge_year]§e事件注册中");
        Bukkit.getPluginManager().registerEvents(new Block_event(), this);//注册方块事件
        Bukkit.getPluginManager().registerEvents(new Item_event(), this);//注册物品事件

        log.info("§d[HeartAge_year]§e已启动-" + Version);

    }

    @Override
    public void onDisable() {
        log.info("§d[HeartAge_year]§e已停止，感谢使用");
    }
}
