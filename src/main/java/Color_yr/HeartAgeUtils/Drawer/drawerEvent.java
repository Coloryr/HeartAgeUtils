package Color_yr.HeartAgeUtils.Drawer;

import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.Drawer.drawerDo;
import Color_yr.HeartAgeUtils.Drawer.drawerSaveObj;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.NMS.ItemNBTSet;
import Color_yr.HeartAgeUtils.Obj.languageObj;
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


public class drawerEvent implements Listener {
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
                    boolean all = e.getPlayer().isSneaking();
                    if (obj.getItem().equals(test)) {
                        if(all) {
                            for (ItemStack item1 : e.getPlayer().getInventory()) {
                                if (item1!=null && item1.getType().equals(test)) {
                                    obj.setAmount(obj.getAmount() + item1.getAmount());
                                    e.getPlayer().getInventory().removeItem(item1);
                                }
                            }
                        }
                        else {
                            obj.setAmount(obj.getAmount() + item.getAmount());
                            e.getPlayer().getInventory().removeItem(item);
                        }
                        e.setCancelled(true);
                    } else if (obj.getItem().equals(Material.AIR)) {
                        ItemNBTSet set = new ItemNBTSet(item);
                        if(set.haveNBT()) {
                            e.getPlayer().sendMessage(lan.getDrawerCant());
                            return;
                        }
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
                    int maxAmount = obj.getItem().getMaxStackSize();
                    if (obj.getAmount() >= maxAmount) {
                        amount = maxAmount;
                        obj.setAmount(obj.getAmount() - maxAmount);
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
        ItemNBTSet nbt = new ItemNBTSet(item);
        if (nbt.hasKey("type") && nbt.getString("type").equals("drawer")) {
            boolean a = drawerDo.setLocal(e.getBlockPlaced(), nbt.getString("uuid"));
            if (!a) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(HeartAgeUtils.configMain.lan.getDrawerError());
            } else {
                e.getPlayer().sendMessage(HeartAgeUtils.configMain.lan.getDrawerPlace());
            }
        }
    }

    @EventHandler
    public void breal(BlockBreakEvent e) {
        drawerSaveObj obj = drawerDo.getDrawerSave(e.getPlayer(), e.getBlock());
        if (obj != null)
            e.setCancelled(true);
    }
}