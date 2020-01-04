package Color_yr.HeartAge_year.Command;

import Color_yr.HeartAge_year.respawnStone.respawnStone_do;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Color_yr.HeartAge_year.Config.Config_Read.lan;

public class respawnStone_Command implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("respawnStone")) {
            if (args.length == 0) {
                sender.sendMessage(lan.getTitle() + lan.getUnknown_command_tpStone());
                return true;
            } else if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(lan.getTitle() + lan.getPlayer_only_command());
                return true;
            }
            Player player = Bukkit.getPlayer(sender.getName());
            if (player == null) {
                sender.sendMessage(lan.getTitle() + lan.getNo_player_command());
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {//获取帮助
                for (String a : lan.getHelp_command_respawnStone()) {
                    sender.sendMessage(lan.getTitle() + a);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("rename")) {//重命名传送石
                ItemStack stack = player.getInventory().getItemInMainHand();
                Material item = stack.getType();
                if (!item.equals(respawnStone_do.item)) {
                    sender.sendMessage(lan.getTitle() + lan.getRespawnStone_hand_command());
                    return true;
                }
                if (args.length != 2) {
                    sender.sendMessage(lan.getTitle() + lan.getRespawnStone_new_name_command());
                    return true;
                }
                sender.sendMessage(lan.getTitle() + new respawnStone_do().rename_respawnStone(stack, args[1]));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                return true;
            } else if (args[0].equalsIgnoreCase("get")) {//给传送石
                if (!sender.hasPermission("respawnStone.give")) {
                    sender.sendMessage(lan.getTitle() + lan.getNoPermission_command());
                    return true;
                }
                UUID uuid;
                do {
                    uuid = UUID.randomUUID();
                } while (respawnStone_do.respawnStone_save.containsKey(uuid.toString()));
                player.getInventory().addItem(new respawnStone_do().new_respawnStone(uuid.toString(), player.getWorld().getName()));
                sender.sendMessage(lan.getTitle() + lan.getRespawnStone_get_stone());
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("help");
            list.add("rename");
            return list;
        }
        return null;
    }
}
