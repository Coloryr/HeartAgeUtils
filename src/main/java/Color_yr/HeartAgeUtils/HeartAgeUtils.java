package Color_yr.HeartAgeUtils;

import Color_yr.HeartAgeUtils.Command.commandList;
import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.Event.deathChest;
import Color_yr.HeartAgeUtils.Event.drawerBlock;
import Color_yr.HeartAgeUtils.Event.tpStone;
import Color_yr.HeartAgeUtils.Hook.Hook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class HeartAgeUtils extends JavaPlugin {

    private static final String Version = "1.0.0";
    public static Plugin plugin;
    public static Logger log;
    public static configMain ConfigMain;

    public static void LoadConfig()
    {
        if(ConfigMain == null)
            ConfigMain = new configMain();
        else
            ConfigMain.setConfig();
    }

    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();
        log.info("[HeartAgeUtils]正在启动，感谢使用，本插件交流群：571239090");
        log.info("[HeartAgeUtils]读取配置文件中");
        LoadConfig();
        log.info("[HeartAgeUtils]挂钩插件中");
        Hook.init();
        log.info("[HeartAgeUtils]注册指令中");
        Bukkit.getPluginCommand("heartageutils").setExecutor(commandList.command);//注册插件主指令
        Bukkit.getPluginCommand("hau").setExecutor(commandList.command);//注册插件主指令
        log.info("[HeartAgeUtils]事件注册中");
        Bukkit.getPluginManager().registerEvents(new drawerBlock(), this);//注册方块事件
        Bukkit.getPluginManager().registerEvents(new tpStone(), this);//注册物品事件
        Bukkit.getPluginManager().registerEvents(new deathChest(), this);//注册物品事件
        log.info("[HeartAgeUtils]已启动-" + Version);
    }

    @Override
    public void onDisable() {
        ConfigMain.SaveTask.closeTimer();
        log.info("§d[HeartAgeUtils]§e已停止，感谢使用");
    }
}
