package Color_yr.HeartAge_year;

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
        new Config_Read().setConfig();
        new tpStone_Read().init();
        log.info("§d[HeartAge_year]§e注册指令中");
        Bukkit.getPluginCommand("tpStone").setExecutor(new toStone_Commder());
        log.info("§d[HeartAge_year]§e事件注册中");
        Bukkit.getPluginManager().registerEvents(new Block_event(), this);
        Bukkit.getPluginManager().registerEvents(new Item_event(), this);

        log.info("§d[HeartAge_year]§e已启动-" + Version);

    }

    @Override
    public void onDisable() {
        log.info("§d[HeartAge_year]§e已停止，感谢使用");
    }
}
