package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.API.ICommand;
import Color_yr.HeartAgeUtils.Drawer.drawerDo;
import Color_yr.HeartAgeUtils.Drawer.drawerSaveObj;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Hook.Hook;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.Utils.itemNBTSet;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.List;

class drawer implements ICommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        languageObj lan = HeartAgeUtils.configMain.lan;
        if (args.length == 0) {
            sender.sendMessage(lan.getUnknownCommandDrawer());
            return true;
        } else if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(lan.getTitle() + lan.getPlayerOnlyCommand());
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            for (String help : lan.getHelpDrawer()) {
                sender.sendMessage(help);
            }
            return true;
        } else if (args[0].equalsIgnoreCase("break")) {
            Player player = (Player) sender;
            Block block = player.getTargetBlockExact(5);
            if (block == null) {
                player.sendMessage(lan.getDeathLockAt());
                return true;
            }
            drawerSaveObj obj = drawerDo.getDrawerSave(player, block);
            if (obj == null)
                return true;
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
            NBTTagCompound nbt = itemNBTSet.getNBT(item);
            nbt.setString("type", "drawer");
            nbt.setString("uuid", obj.getUuid());
            item = itemNBTSet.saveNBT(item, nbt);
            inventory.addItem(item);

            return true;
        } else if (args[0].equalsIgnoreCase("get")
                && sender.hasPermission("drawer.give")) {
            ItemStack item = drawerDo.getDrawer();
            Player player = (Player) sender;
            player.getInventory().addItem(item);
            player.sendMessage(lan.getDrawerGet());
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
