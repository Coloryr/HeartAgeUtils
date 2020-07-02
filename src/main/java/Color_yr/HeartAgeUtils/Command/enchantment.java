package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.API.ICommand;
import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.OreGen.oreGenDo;
import Color_yr.HeartAgeUtils.enchantment.enchantmentDo;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class enchantment implements ICommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        languageObj lan = HeartAgeUtils.configMain.lan;
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(lan.getTitle() + lan.getPlayerOnlyCommand());
            return true;
        } else if (args.length == 0 || args[0].equalsIgnoreCase("help")) {//获取帮助
            for (String a : lan.getHelpCommandEnchantment()) {
                sender.sendMessage(lan.getTitle() + a);
            }
            sender.sendMessage(enchantmentDo.help);
            return true;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        if (player == null) {
            sender.sendMessage(lan.getTitle() + lan.getNoPlayerCommand());
            return true;
        } else if (args[0].equalsIgnoreCase("break")) {//启用
            enchantmentDo.EMDo(player, false);
            return true;
        } else if (args[0].equalsIgnoreCase("add")) {//关闭
            enchantmentDo.EMDo(player, true);
            return true;
        } else {
            sender.sendMessage(lan.getTitle() + lan.getUnknownCommandEnchantment());
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("break");
            list.add("add");
            return list;
        }
        return null;
    }
}
