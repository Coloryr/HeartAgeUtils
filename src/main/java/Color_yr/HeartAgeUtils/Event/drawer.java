package Color_yr.HeartAgeUtils.Event;

import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.Drawer.drawerDo;
import Color_yr.HeartAgeUtils.Drawer.drawerSaveObj;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Hook.Hook;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.Utils.itemNBTSet;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.List;


public class drawer implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void itemClick(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;
        if (e.getHand() == EquipmentSlot.OFF_HAND)
            return;
        Block block = e.getClickedBlock();
        if (block == null)
            return;

        languageObj lan = HeartAgeUtils.configMain.lan;
        drawerSaveObj obj = drawerDo.getDrawerSave(e.getPlayer(), block);
        if (obj == null) {
            return;
        }

        switch (e.getAction()) {
            case RIGHT_CLICK_BLOCK:
                ItemStack item = e.getItem();
                if (item != null) {
                    Material test = item.getType();
                    if (obj.getItem().equals(test)) {
                        obj.setAmount(obj.getAmount() + item.getAmount());
                        e.getPlayer().getInventory().removeItem(item);
                        e.setCancelled(true);
                    } else if (obj.getItem().equals(Material.AIR)) {
                        obj.setItem(test);
                        obj.setAmount(item.getAmount());
                        e.getPlayer().getInventory().removeItem(item);
                        e.setCancelled(true);
                    } else {
                        return;
                    }
                }
                break;
            case LEFT_CLICK_BLOCK:
                if (!obj.getItem().equals(Material.AIR)) {
                    ItemStack item1 = new ItemStack(obj.getItem());
                    if (e.getPlayer().getInventory().firstEmpty() == -1) {
                        e.getPlayer().sendMessage(lan.getDrawerNoEmpty());
                        return;
                    }
                    int amount;
                    if (obj.getAmount() >= 64) {
                        amount = 64;
                        obj.setAmount(obj.getAmount() - 64);
                    } else {
                        amount = obj.getAmount();
                        obj.setAmount(0);
                    }
                    if (obj.getAmount() == 0) {
                        obj.setItem(Material.AIR);
                    }
                    item1.setAmount(amount);
                    e.getPlayer().getInventory().addItem(item1);
                    e.setCancelled(true);
                }
                break;
        }
        String temp = lan.getDrawerCheck();
        temp = temp.replace("%Item%", obj.getItem().name())
                .replace("%Amount%", "" + obj.getAmount());
        e.getPlayer().sendMessage(temp);
        configMain.drawer.save(obj, obj.getUuid());
    }

    @EventHandler
    public void place(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        NBTTagCompound nbt = itemNBTSet.getNBT(item);
        if (nbt.hasKey("type") && nbt.getString("type").equals("drawer")) {
            e.getBlockPlaced().setMetadata("uuid", drawerDo.getTag(nbt.getString("uuid")));
        }
    }

    @EventHandler
    public void breal(BlockBreakEvent e) {
        drawerSaveObj obj = drawerDo.getDrawerSave(e.getPlayer(), e.getBlock());
        if (obj != null)
            e.setCancelled(true);
    }
}