package Color_yr.HeartAgeUtils.Event;

import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.DeathChest.deathChestDo;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.Utils.itemNBTSet;
import Color_yr.HeartAgeUtils.tpStone.locationObj;
import Color_yr.HeartAgeUtils.tpStone.tpStoneDo;
import Color_yr.HeartAgeUtils.tpStone.tpStoneObjSet;
import Color_yr.HeartAgeUtils.tpStone.tpStoneSaveObj;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;

public class tpStone implements Listener {

    @EventHandler
    public void itemClick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null)
            return;
        Material test = item.getType();
        if (test.equals(tpStoneDo.item)) {
            NBTTagCompound ItemNbt = itemNBTSet.getNBT(item);
            if (!ItemNbt.hasKey("uuid"))
                return;
            e.setCancelled(true);
            String uuid = ItemNbt.getString("uuid");
            Player player = e.getPlayer();
            languageObj lan = HeartAgeUtils.configMain.lan;
            if (!tpStoneDo.toStoneSave.containsKey(uuid)) {
                player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                player.sendMessage(lan.getTitle() + lan.getTpStoneNoDate());
                return;
            }
            tpStoneSaveObj obj = tpStoneDo.toStoneSave.get(uuid);
            Inventory inv = Bukkit.createInventory(e.getPlayer(), InventoryType.DISPENSER,
                    lan.getTpStone_title() + obj.getName());
            ItemStack itemStack = new ItemStack(Material.COMPASS);
            ItemMeta temp1;
            for (Map.Entry<String, locationObj> temp : obj.getSel().entrySet()) {
                String slot = temp.getKey().replace("sel", "");
                locationObj locationObj = temp.getValue();
                ItemNbt = itemNBTSet.getNBT(itemStack);
                ItemNbt.setBoolean("disable", false);
                ItemNbt.setInt("x", locationObj.getX());
                ItemNbt.setInt("y", locationObj.getY());
                ItemNbt.setInt("z", locationObj.getZ());
                itemStack = itemNBTSet.saveNBT(itemStack, ItemNbt);
                temp1 = itemStack.getItemMeta();
                temp1.setDisplayName(locationObj.getName());
                temp1.setLore(new ArrayList<String>() {{
                    this.add(lan.getTpStoneShow());
                    this.add("§aX：§b" + locationObj.getX() + " §aY：§b" + locationObj.getY() + " §aZ：§b" + locationObj.getZ());
                }});
                itemStack.setItemMeta(temp1);
                inv.setItem(Integer.decode(slot) - 1, itemStack);
            }
            itemStack = new ItemStack(Material.BARRIER);
            temp1 = itemStack.getItemMeta();
            temp1.setDisplayName(lan.getTpStoneUnlock());
            temp1.setLore(new ArrayList<String>() {
                {
                    this.add(lan.getTpStoneUnlockNeed());
                }
            });
            itemStack.setItemMeta(temp1);
            ItemNbt = itemNBTSet.getNBT(itemStack);
            ItemNbt.setBoolean("disable", true);
            itemStack = itemNBTSet.saveNBT(itemStack, ItemNbt);
            for (int i = 0; i < 9; i++) {
                ItemStack a = inv.getItem(i);
                if (a == null) {
                    inv.setItem(i, itemStack);
                }
            }
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
            deathChestDo.GUI_save.put(player.getName(), player.openInventory(inv));
        } else if (e.getHand() == EquipmentSlot.OFF_HAND) {
            ItemStack offhand = e.getPlayer().getInventory().getItemInOffHand();
            ItemStack mainhand = e.getPlayer().getInventory().getItemInMainHand();
            if (mainhand.getType().equals(tpStoneDo.item) && offhand.getType().equals(tpStoneDo.updateItem)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void selGui(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player player = (Player) e.getWhoClicked();
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand.getType().equals(Material.AIR))
                return;
            languageObj lan = HeartAgeUtils.configMain.lan;
            if (deathChestDo.GUI_save.containsKey(player.getName())) {
                InventoryView inv = deathChestDo.GUI_save.get(player.getName());
                if (!inv.getTitle().contains(lan.getTpStone_title())) {
                    player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                    deathChestDo.GUI_save.remove(player.getName());
                    return;
                }
                e.setCancelled(true);
                if (inv.getPlayer().equals(player)) {
                    ItemStack item = inv.getItem(e.getSlot());
                    NBTTagCompound ItemNbt = itemNBTSet.getNBT(item);
                    if (ItemNbt.getBoolean("disable")) {
                        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 1.0f, 1.0f);
                        player.sendMessage(lan.getTitle() + lan.getTpStoneUnlockSlot());
                    } else if (!ItemNbt.hasKey("disable") || (!ItemNbt.hasKey("x") || !ItemNbt.hasKey("y") || !ItemNbt.hasKey("z"))) {
                        player.sendMessage(lan.getTitle() + lan.getTpStoneError());
                        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                        deathChestDo.GUI_save.remove(player.getName());
                        return;
                    } else if (e.getClick() == ClickType.LEFT) {
                        int x = ItemNbt.getInt("x");
                        int y = ItemNbt.getInt("y");
                        int z = ItemNbt.getInt("z");
                        if (x == 0 && y == 0 && z == 0) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1.0f, 1.0f);
                            player.sendMessage(lan.getTitle() + lan.getTpStoneCantTp());
                        } else {
                            player.teleport(new org.bukkit.Location(player.getWorld(), x, y, z));
                            player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1.0f, 1.0f);
                            player.sendMessage(lan.getTitle() + lan.getTpStoneTp());
                        }
                    } else if (e.getClick() == ClickType.RIGHT) {
                        org.bukkit.Location location = player.getLocation();
                        ItemNbt = itemNBTSet.getNBT(hand);
                        String uuid = ItemNbt.getString("uuid");
                        if (!tpStoneDo.toStoneSave.containsKey(uuid)) {
                            player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                            player.sendMessage(lan.getTitle() + lan.getTpStoneError());
                        } else {
                            tpStoneSaveObj stone = tpStoneDo.toStoneSave.get(uuid);
                            tpStoneObjSet set = new tpStoneObjSet(stone);
                            locationObj location1 = set.getSel(e.getSlot());
                            location1.setX((int) location.getX());
                            location1.setY((int) location.getY());
                            location1.setZ((int) location.getZ());
                            set.setSel(e.getSlot(), location1);
                            configMain.tpStone.save(stone, uuid);
                            tpStoneDo.toStoneSave.put(uuid, stone);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                            player.sendMessage(lan.getTitle() + lan.getTpStoneSave());
                        }
                    }
                }
                player.closeInventory();
                deathChestDo.GUI_save.remove(player.getName());
            }
        }
    }
}
