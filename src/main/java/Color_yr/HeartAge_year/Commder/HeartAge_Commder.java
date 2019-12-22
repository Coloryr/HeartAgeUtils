package Color_yr.HeartAge_year.Commder;

import Color_yr.HeartAge_year.Config.Config_Read;
import Color_yr.HeartAge_year.Config.tpStone_Read;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class HeartAge_Commder implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        if (command.getName().equalsIgnoreCase("heartage")) {
            if (arg.length == 0) {
                sender.sendMessage("§d[HeartAge_year]§c错误，请使用/heartage help 获取帮助");
                return true;
            } else if (arg[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("heartage.admin")) {
                    sender.sendMessage("§d[HeartAge_year]§c错误，请使用/heartage help 获取帮助");
                } else
                    sender.sendMessage("§d[HeartAge_year]§c你没有权限使用这条指令");
                return true;
            } else if (arg[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("heartage.admin")) {
                    new Config_Read().setConfig();
                    new tpStone_Read().init();
                } else
                    sender.sendMessage("§d[HeartAge_year]§c你没有权限使用这条指令");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] arg) {
        if(sender.hasPermission("heartage.admin"))
        {
            List<String> temp = new ArrayList<>();
            temp.add("help");
            temp.add("reload");
        }
        return null;
    }
}
