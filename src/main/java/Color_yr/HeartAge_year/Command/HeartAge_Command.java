package Color_yr.HeartAge_year.Command;

import Color_yr.HeartAge_year.Config.Config_Read;
import Color_yr.HeartAge_year.Config.tpStone_Read;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

import static Color_yr.HeartAge_year.Config.Config_Read.lan;

public class HeartAge_Command implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        if (command.getName().equalsIgnoreCase("heartage")) {
            if (arg.length == 0) {
                sender.sendMessage(lan.getTitle() + lan.getUnknown_command_heartage());
                return true;
            } else if (arg[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("heartage.admin")) {
                    for (String a : lan.getHelp_command_heartage()) {
                        sender.sendMessage(lan.getTitle() + a);
                    }
                } else
                    sender.sendMessage(lan.getTitle() + lan.getNoPermission_command());
                return true;
            } else if (arg[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("heartage.admin")) {
                    new Config_Read().setConfig();
                    new tpStone_Read().init();
                    sender.sendMessage(lan.getTitle() + lan.getReload_command());
                    return true;
                } else
                    sender.sendMessage(lan.getTitle() + lan.getNoPermission_command());
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] arg) {
        if (sender.hasPermission("heartage.admin")) {
            List<String> temp = new ArrayList<>();
            temp.add("help");
            temp.add("reload");
            return temp;
        }
        return null;
    }
}
