package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

class HAU implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        if (command.getName().equalsIgnoreCase("heartageutils")
                || command.getName().equalsIgnoreCase("hau")) {
            languageObj lan = HeartAgeUtils.configMain.lan;
            if (arg.length == 0) {
                sender.sendMessage(lan.getTitle() + lan.getUnknownCommandHeartAge());
                return true;
            } else if (arg[0].equalsIgnoreCase("help")) {
                for (String a : lan.getHelpCommandHeartAge()) {
                    sender.sendMessage(lan.getTitle() + a);
                }
                return true;
            } else if (arg[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("heartage.admin")) {
                    HeartAgeUtils.LoadConfig();
                    sender.sendMessage(lan.getTitle() + lan.getReloadCommand());
                    return true;
                }
            } else if (arg[0].equalsIgnoreCase("tpstone")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.tpStone.onCommand(sender, ss);
            } else if (arg[0].equalsIgnoreCase("deathchest")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.deathChest.onCommand(sender, ss);
            } else if (arg[0].equalsIgnoreCase("drawer")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.drawer.onCommand(sender, ss);
            } else if (arg[0].equalsIgnoreCase("test")) {
                Player player = (Player) sender;
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] arg) {
        if (arg.length == 1) {
            List<String> temp = new ArrayList<>();
            if (sender.hasPermission("heartage.admin")) {
                temp.add("reload");
            }
            temp.add("tpstone");
            temp.add("deathchest");
            temp.add("drawer");
            temp.add("help");
            return temp;
        } else if (arg.length >= 2) {
            if (arg[0].equalsIgnoreCase("tpstone")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.tpStone.onTabComplete(sender, ss);
            } else if (arg[0].equalsIgnoreCase("deathchest")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.deathChest.onTabComplete(sender, ss);
            } else if (arg[0].equalsIgnoreCase("drawer")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.drawer.onTabComplete(sender, ss);
            }
        }
        return null;
    }
}
