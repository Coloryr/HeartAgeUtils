package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.API.ICommand;
import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.OreGen.oreGenDo;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class oreGen implements ICommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        languageObj lan = HeartAgeUtils.configMain.lan;
        if (args.length == 0) {
            sender.sendMessage(lan.getTitle() + lan.getUnknownCommandOreGen());
            return true;
        } else if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(lan.getTitle() + lan.getPlayerOnlyCommand());
            return true;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        if (player == null) {
            sender.sendMessage(lan.getTitle() + lan.getNoPlayerCommand());
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {//获取帮助
            for (String a : lan.getHelpCommandOreGen()) {
                sender.sendMessage(lan.getTitle() + a);
            }
            return true;
        } else if (args[0].equalsIgnoreCase("enable")) {//启用
            configMain.oreGenRead.save(null, null);
            oreGenDo.saveobj.setPlayer(player.getName(), true);
            sender.sendMessage(lan.getOreGenEnable());
            player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 1.0f, 1.0f);
            return true;
        } else if (args[0].equalsIgnoreCase("disable")) {//关闭
            configMain.oreGenRead.save(null, null);
            oreGenDo.saveobj.setPlayer(player.getName(), false);
            sender.sendMessage(lan.getOreGenDisable());
            player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, 1.0f, 1.0f);
            return true;
        } else {
            sender.sendMessage(lan.getTitle() + lan.getUnknownCommandOreGen());
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("enable");
            list.add("disable");
            return list;
        }
        return null;
    }
}
