package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.LanguageObj;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class HAU implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        if (command.getName().equalsIgnoreCase("heartageutils")
                || command.getName().equalsIgnoreCase("huc")) {
            LanguageObj lan = HeartAgeUtils.ConfigMain.lan;
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
                    HeartAgeUtils.LoadConfig();
                    sender.sendMessage(lan.getTitle() + lan.getReload_command());
                    return true;
                } else
                    sender.sendMessage(lan.getTitle() + lan.getNoPermission_command());
            } else if (arg[0].equalsIgnoreCase("tpstone")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return CommandList.tpStone.onCommand(sender, ss);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] arg) {
        if (arg.length == 1) {
            List<String> temp = new ArrayList<>();
            if (sender.hasPermission("heartage.admin")) {
                temp.add("help");
                temp.add("reload");
            }
            temp.add("tpstone");
            return temp;
        } else if (arg.length == 2) {
            if (arg[1].equalsIgnoreCase("tpstone")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return CommandList.tpStone.onTabComplete(sender, ss);
            }
        }
        return null;
    }
}
