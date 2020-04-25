package Color_yr.HeartAgeUtils;

import Color_yr.HeartAgeUtils.Command.CommandList;
import Color_yr.HeartAgeUtils.Config.ConfigMain;
import Color_yr.HeartAgeUtils.Config.tpStoneRead;
import Color_yr.HeartAgeUtils.Event.DrawerBlock;
import Color_yr.HeartAgeUtils.Event.tpStone;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class HeartAgeUtils extends JavaPlugin {

    private static final String Version = "1.0.0";
    public static Plugin plugin;
    public static Logger log;
    public static ConfigMain ConfigMain;

    public static void LoadConfig()
    {
        if(ConfigMain == null)
            ConfigMain = new ConfigMain();
        else
            ConfigMain.setConfig();
    }

    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();
        log.info("§d[HeartAgeUtils]§e正在启动，感谢使用，本插件交流群：571239090");
        log.info("§d[HeartAgeUtils]§e读取配置文件中");
        LoadConfig();
        log.info("§d[HeartAgeUtils]§e注册指令中");
        Bukkit.getPluginCommand("heartageutils").setExecutor(CommandList.command);//注册插件主指令
        Bukkit.getPluginCommand("hau").setExecutor(CommandList.command);//注册插件主指令
        log.info("§d[HeartAgeUtils]§e事件注册中");
        Bukkit.getPluginManager().registerEvents(new DrawerBlock(), this);//注册方块事件
        Bukkit.getPluginManager().registerEvents(new tpStone(), this);//注册物品事件
        log.info("§d[HeartAgeUtils]§e已启动-" + Version);
    }

    @Override
    public void onDisable() {
        ConfigMain.SaveTask.closeTimer();
        log.info("§d[HeartAgeUtils]§e已停止，感谢使用");
    }
}
