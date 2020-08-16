package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.API.ICommand;
import Color_yr.HeartAgeUtils.Drawer.drawerDo;
import Color_yr.HeartAgeUtils.Drawer.drawerSaveObj;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.NMS.ItemNBTSet;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.Utils.Tools;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

class drawer implements ICommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        languageObj lan = HeartAgeUtils.configMain.lan;
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(lan.getTitle() + lan.getPlayerOnlyCommand());
            return true;
        } else if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            for (String help : lan.getHelpCommandDrawer()) {
                sender.sendMessage(help);
            }
            return true;
        } else if (args[0].equalsIgnoreCase("break")) {
            Player player = (Player) sender;
            Block block = player.getTargetBlockExact(5);
            if (block == null) {
                player.sendMessage(lan.getDrawerNot());
                return true;
            }
            drawerSaveObj obj = drawerDo.getDrawerSaveOwner(player, block);
            if (obj == null) {
                player.sendMessage(lan.getDrawerNot());
                return true;
            }
            Inventory inventory = player.getInventory();
            if (inventory.firstEmpty() == -1) {
                player.sendMessage(lan.getDrawerNoEmpty());
                return true;
            }

            block.setType(Material.AIR);
            ItemStack item = new ItemStack(drawerDo.block);
            item.setAmount(1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(lan.getDrawerName());
            List<String> lore = new ArrayList<>();
            String data = lan.getDrawerCheck();
            data = data.replace("%Item%", obj.getItem().name())
                    .replace("%Amount%", "" + obj.getAmount());
            lore.add(data);
            meta.setLore(lore);
            item.setItemMeta(meta);
            ItemNBTSet nbt = new ItemNBTSet(item);
            nbt.setNbt("type", "drawer");
            nbt.setNbt("uuid", obj.getUuid());
            item = nbt.saveNBT();
            inventory.addItem(item);

            player.sendMessage(lan.getDrawerBreak());

            return true;
        } else if (args[0].equalsIgnoreCase("get")
                && sender.hasPermission("drawer.give")) {
            Player player;
            if (args.length == 2) {
                player = Bukkit.getPlayer(args[1]);
            } else {
                player = (Player) sender;
            }
            if (player != null) {
                ItemStack item = drawerDo.getDrawer();
                if (Tools.CheckIsFull(player))
                    player.getLocation().getWorld().dropItem(player.getLocation(), item);
                else
                    player.getInventory().addItem(item);
                player.sendMessage(lan.getDrawerGet());
            }
            return true;
        } else {
            sender.sendMessage(lan.getUnknownCommandDrawer());
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("break");
            return list;
        }
        return null;
    }
}
