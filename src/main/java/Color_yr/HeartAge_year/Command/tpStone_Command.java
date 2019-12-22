package Color_yr.HeartAge_year.Command;

import Color_yr.HeartAge_year.tpStone.tpStone_do;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Color_yr.HeartAge_year.Config.Config_Read.lan;

public class tpStone_Command implements CommandExecutor, TabExecutor {

    private boolean isNumer(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpStone")) {
            if (args.length == 0) {
                sender.sendMessage(lan.getTitle() + lan.getUnknown_command_tpStone());
                return true;
            }else if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(lan.getTitle() + lan.getPlayer_only_command());
                return true;
            }
            Player player = Bukkit.getPlayer(sender.getName());
            if (player == null) {
                sender.sendMessage(lan.getTitle() + lan.getNo_player_command());
                return true;
            }
            else if (args[0].equalsIgnoreCase("help")) {//获取帮助
                for(String a: lan.getHelp_command_tpStone())
                {
                    sender.sendMessage(lan.getTitle() + a);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("rename")) {//重命名传送石
                ItemStack stack = player.getInventory().getItemInMainHand();
                Material item = stack.getType();
                if (!item.equals(tpStone_do.item)) {
                    sender.sendMessage(lan.getTitle() + lan.getTpStone_hand_command());
                    return true;
                }
                if (args.length != 2) {
                    sender.sendMessage(lan.getTitle() +lan.getTpStone_new_name_command());
                    return true;
                }
                sender.sendMessage(lan.getTitle() + new tpStone_do().rename_tpStone(stack, args[1]));
                return true;
            } else if (args[0].equalsIgnoreCase("uplevel")) {//升级传送石
                ItemStack stack = player.getInventory().getItemInMainHand();
                Material item = stack.getType();
                if (!item.equals(tpStone_do.item)) {
                    sender.sendMessage(lan.getTitle() + lan.getTpStone_hand_command());
                    return true;
                }
                stack = player.getInventory().getItemInOffHand();
                item = stack.getType();
                if (!item.equals(tpStone_do.update_item)) {
                    sender.sendMessage(lan.getTitle() + lan.getTpStone_offhand_command());
                    return true;
                }
                sender.sendMessage(lan.getTitle() + new tpStone_do().up_tpStone(player));
                return true;
            } else if (args[0].equalsIgnoreCase("setname")) {
                ItemStack stack = player.getInventory().getItemInMainHand();
                Material item = stack.getType();
                if (!item.equals(tpStone_do.item)) {
                    sender.sendMessage(lan.getTitle() + lan.getTpStone_hand_command());
                    return true;
                }
                if (args.length == 1) {
                    sender.sendMessage(lan.getTitle() + lan.getTpStone_set_name_command());
                    return true;
                }
                if (args.length == 2) {
                    sender.sendMessage(lan.getTitle() + lan.getTpStone_set_name_slot_command());
                    return true;
                }
                if(!isNumer(args[2]))
                {
                    sender.sendMessage(lan.getTitle() + lan.getTpStone_set_name_number_command());
                    return true;
                }
                sender.sendMessage(lan.getTitle() + new tpStone_do()
                        .set_slot_name(player.getInventory().getItemInMainHand(), Integer.decode(args[2]), args[3]));
                return true;
            } else if (args[0].equalsIgnoreCase("get")) {//给传送石
                if (!sender.hasPermission("tpStone.give")) {
                    sender.sendMessage(lan.getTitle() + lan.getNoPermission_command());
                    return true;
                }
                UUID uuid;
                do {
                    uuid = UUID.randomUUID();
                } while (tpStone_do.toStone_save.containsKey(uuid.toString()));
                player.getInventory().addItem(new tpStone_do().new_tpStone(uuid.toString()));
                sender.sendMessage(lan.getTitle() + lan.getTpStone_get_stone());
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
            list.add("uplevel");
            return list;
        }
        return null;
    }
}
