package Color_yr.HeartAge_year.Commder;

import Color_yr.HeartAge_year.Config.tpStone.tpStone_do;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class toStone_Commder implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpStone")) {
            if (args.length == 0) {
                sender.sendMessage("§d[HeartAge_year]§c错误，请使用/tpStone help 获取帮助");
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {//获取帮助
                sender.sendMessage("§d[HeartAge_year]§b输入/tpStone rename [名字] 修改传送石名字");
                sender.sendMessage("§d[HeartAge_year]§b输入/tpStone uplevel 升级传送石");
                return true;
            } else if (args[0].equalsIgnoreCase("rename")) {//重命名传送石
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("§d[HeartAge_year]§c只能玩家使用这个指令");
                    return true;
                }
                Player player = Bukkit.getPlayer(sender.getName());
                if (player == null) {
                    sender.sendMessage("§d[HeartAge_year]§c执行指令的玩家找不到");
                    return true;
                }
                ItemStack stack = player.getInventory().getItemInMainHand();
                Material item = stack.getType();
                if (!item.equals(tpStone_do.item)) {
                    sender.sendMessage("§d[HeartAge_year]§c请手持传送石后执行");
                    return true;
                }
                if (args.length != 2) {
                    sender.sendMessage("§d[HeartAge_year]§c请输入新的传送石名字");
                    return true;
                }
                sender.sendMessage("§d[HeartAge_year]§" + new tpStone_do().rename_tpStone(stack, args[1]));
                return true;
            } else if (args[0].equalsIgnoreCase("uplevel")) {//升级传送石
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§d[HeartAge_year]§c只能玩家使用这个指令");
                    return true;
                }
                Player player = (Player) sender;

                ItemStack stack = player.getInventory().getItemInMainHand();
                Material item = stack.getType();
                if (!item.equals(tpStone_do.item)) {
                    sender.sendMessage("§d[HeartAge_year]§c请手持传送石后执行");
                    return true;
                }
                stack = player.getInventory().getItemInOffHand();
                item = stack.getType();
                if (!item.equals(tpStone_do.updata_item)) {
                    sender.sendMessage("§d[HeartAge_year]§c请副手持升级物品");
                    return true;
                }
                sender.sendMessage("§d[HeartAge_year]§" + new tpStone_do().up_tpStone(stack));
                return true;
            } else if (args[0].equalsIgnoreCase("get")) {//给传送石
                if (!sender.hasPermission("tpStone.give")) {
                    sender.sendMessage("§d[HeartAge_year]§c没有使用该指令的权限");
                    return true;
                }
                UUID uuid;
                do {
                    uuid = UUID.randomUUID();
                } while (tpStone_do.toStone_save.containsKey(uuid.toString()));
                Player player = (Player) sender;
                player.getInventory().addItem(new tpStone_do().new_tpStone(uuid));
                sender.sendMessage("§d[HeartAge_year]§b你获得了一个新的传送石");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 0) {
            list.add("help");
            list.add("rename");
            list.add("uplevel");
        }
        return list;
    }
}
