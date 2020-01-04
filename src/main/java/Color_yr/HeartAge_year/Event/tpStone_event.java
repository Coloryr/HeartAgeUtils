package Color_yr.HeartAge_year.Event;

import Color_yr.HeartAge_year.Config.tpStone_Read;
import Color_yr.HeartAge_year.Obj.Location_Obj;
import Color_yr.HeartAge_year.Obj.tpStone_save_Obj;
import Color_yr.HeartAge_year.tpStone.tpStone_do;
import Color_yr.HeartAge_year.tpStone.tpStone_set;
import Color_yr.HeartAge_year.util.NBT_set;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
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
import java.util.HashMap;
import java.util.Map;

import static Color_yr.HeartAge_year.Config.Config_Read.lan;

public class tpStone_event implements Listener {

    private static Map<String, InventoryView> GUI_save = new HashMap<>();

    @EventHandler
    public void item_click(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null)
            return;
        Material test = item.getType();
        if (test.equals(tpStone_do.item)) {
            NBT_set NBT_set = new NBT_set();
            NBTTagCompound ItemNbt = NBT_set.NBT_get(item);
            if (!ItemNbt.hasKey("uuid"))
                return;
            e.setCancelled(true);
            String uuid = ItemNbt.getString("uuid");
            Player player = e.getPlayer();
            if (!tpStone_do.toStone_save.containsKey(uuid)) {
                player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                player.sendMessage(lan.getTitle() + lan.getTpStone_no_date());
                return;
            }
            tpStone_save_Obj obj = tpStone_do.toStone_save.get(uuid);
            Inventory inv = Bukkit.createInventory(e.getPlayer(), InventoryType.DISPENSER,
                    lan.getTpStone_title() + obj.getName());
            ItemStack itemStack = new ItemStack(Material.COMPASS);
            ItemMeta temp1;
            for (Map.Entry<String, Location_Obj> temp : obj.getSel().entrySet()) {
                String slot = temp.getKey().replace("sel", "");
                Location_Obj locationObj = temp.getValue();
                ItemNbt = NBT_set.NBT_get(itemStack);
                ItemNbt.setBoolean("disable", false);
                ItemNbt.setInt("x", locationObj.getX());
                ItemNbt.setInt("y", locationObj.getY());
                ItemNbt.setInt("z", locationObj.getZ());
                itemStack = NBT_set.NBT_save(itemStack, ItemNbt);
                temp1 = itemStack.getItemMeta();
                temp1.setDisplayName(locationObj.getName());
                temp1.setLore(new ArrayList<String>() {{
                    this.add(lan.getTpStone_show());
                    this.add("§aX：§b" + locationObj.getX() + " §aY：§b" + locationObj.getY() + " §aZ：§b" + locationObj.getZ());
                }});
                itemStack.setItemMeta(temp1);
                inv.setItem(Integer.decode(slot) - 1, itemStack);
            }
            itemStack = new ItemStack(Material.BARRIER);
            temp1 = itemStack.getItemMeta();
            temp1.setDisplayName(lan.getTpStone_unlock());
            temp1.setLore(new ArrayList<String>() {
                {
                    this.add(lan.getTpStone_unlock_need());
                }
            });
            itemStack.setItemMeta(temp1);
            ItemNbt = NBT_set.NBT_get(itemStack);
            ItemNbt.setBoolean("disable", true);
            itemStack = NBT_set.NBT_save(itemStack, ItemNbt);
            for (int i = 0; i < 9; i++) {
                ItemStack a = inv.getItem(i);
                if (a == null) {
                    inv.setItem(i, itemStack);
                }
            }
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
            GUI_save.put(player.getName(), player.openInventory(inv));
        } else if (e.getHand() == EquipmentSlot.OFF_HAND) {
            ItemStack offhand = e.getPlayer().getInventory().getItemInOffHand();
            ItemStack mainhand = e.getPlayer().getInventory().getItemInMainHand();
            if (mainhand.getType().equals(tpStone_do.item) && offhand.getType().equals(tpStone_do.update_item)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void sel_gui(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player player = (Player) e.getWhoClicked();
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand.getType().equals(Material.AIR))
                return;
            if (GUI_save.containsKey(player.getName())) {
                InventoryView inv = GUI_save.get(player.getName());
                if (!inv.getTitle().contains(lan.getTpStone_title())) {
                    player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                    GUI_save.remove(player.getName());
                    return;
                }
                e.setCancelled(true);
                if (inv.getPlayer().equals(player)) {
                    ItemStack item = inv.getItem(e.getSlot());
                    NBTTagCompound ItemNbt = new NBT_set().NBT_get(item);
                    if (ItemNbt.getBoolean("disable")) {
                        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 1.0f, 1.0f);
                        player.sendMessage(lan.getTitle() + lan.getTpStone_unlock_slot());
                    } else if (!ItemNbt.hasKey("disable") || (!ItemNbt.hasKey("x") || !ItemNbt.hasKey("y") || !ItemNbt.hasKey("z"))) {
                        player.sendMessage(lan.getTitle() + lan.getTpStone_error());
                        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                        GUI_save.remove(player.getName());
                        return;
                    } else if (e.getClick() == ClickType.LEFT) {
                        int x = ItemNbt.getInt("x");
                        int y = ItemNbt.getInt("y");
                        int z = ItemNbt.getInt("z");
                        if (x == 0 && y == 0 && z == 0) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1.0f, 1.0f);
                            player.sendMessage(lan.getTitle() + lan.getTpStone_cant_tp());
                        } else {
                            player.teleport(new org.bukkit.Location(player.getWorld(), x, y, z));
                            player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1.0f, 1.0f);
                            player.sendMessage(lan.getTitle() + lan.getTpStone_tp());
                        }
                    } else if (e.getClick() == ClickType.RIGHT) {
                        org.bukkit.Location location = player.getLocation();
                        ItemNbt = new NBT_set().NBT_get(hand);
                        String uuid = ItemNbt.getString("uuid");
                        if (!tpStone_do.toStone_save.containsKey(uuid)) {
                            player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                            player.sendMessage(lan.getTitle() + lan.getTpStone_error());
                        } else {
                            tpStone_save_Obj stone = tpStone_do.toStone_save.get(uuid);
                            tpStone_set set = new tpStone_set(stone);
                            Location_Obj location1 = set.get_sel(e.getSlot());
                            location1.setX((int) location.getX());
                            location1.setY((int) location.getY());
                            location1.setZ((int) location.getZ());
                            set.set_sel(e.getSlot(), location1);
                            new tpStone_Read().save(stone, uuid);
                            tpStone_do.toStone_save.put(uuid, stone);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                            player.sendMessage(lan.getTitle() + lan.getTpStone_save());
                        }
                    }
                }
                player.closeInventory();
                GUI_save.remove(player.getName());
            }
        }
    }
}
