package Color_yr.HeartAge_year.Event;

import Color_yr.HeartAge_year.Config.tpStone.NBT_set;
import Color_yr.HeartAge_year.Config.tpStone.Obj.Location;
import Color_yr.HeartAge_year.Config.tpStone.Obj.tpStone;
import Color_yr.HeartAge_year.Config.tpStone.tpStone_Read;
import Color_yr.HeartAge_year.Config.tpStone.tpStone_do;
import Color_yr.HeartAge_year.Config.tpStone.tpStone_set;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Item_event implements Listener {

    private static Map<String, Inventory> GUI_save = new HashMap<>();

    @EventHandler
    public void itemclick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null)
            return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (item.equals(tpStone_do.item)) {
                NBT_set NBT_set = new NBT_set();
                NBTTagCompound ItemNbt = NBT_set.NBT_get(item);
                if (!ItemNbt.hasKeyOfType("Pack", 2))
                    return;
                String pack_name = ItemNbt.getString("uuid");
                Player player = e.getPlayer();
                if (!tpStone_do.toStone_save.containsKey(pack_name)) {
                    player.sendMessage("§d[HeartAge_year]§c没有这个传送石的数据");
                    return;
                }
                tpStone obj = tpStone_do.toStone_save.get(pack_name);
                Inventory inv = Bukkit.createInventory(e.getPlayer(), InventoryType.CREATIVE, "传送石" + obj.getName() + "设置");
                ItemStack itemStack = new ItemStack(Material.COMPASS);
                for (Map.Entry<String, Location> temp : obj.getSel().entrySet()) {
                    String solt = temp.getKey().replace("sel", "");
                    Location location = temp.getValue();
                    ItemNbt = NBT_set.NBT_get(itemStack);
                    ItemNbt.setBoolean("disable", false);
                    ItemNbt.setInt("x", location.getX());
                    ItemNbt.setInt("y", location.getY());
                    ItemNbt.setInt("z", location.getZ());
                    itemStack.getItemMeta().setDisplayName("坐标" + solt);
                    itemStack.getItemMeta().setLore(new ArrayList<String>() {{
                        this.add("§e设置的坐标：");
                        this.add("§aX：§b" + location.getX() + " §aY：§b" + location.getY() + " §aZ：§b" + location.getZ());
                    }});
                    inv.setItem(Integer.getInteger(solt), itemStack);
                }
                itemStack = new ItemStack(Material.BARRIER);
                ItemNbt = NBT_set.NBT_get(itemStack);
                ItemNbt.setBoolean("disable", true);
                itemStack.getItemMeta().setDisplayName("§4n未解锁");
                itemStack.getItemMeta().setLore(new ArrayList<String>() {
                    {
                        this.add("&7使用升级石来解锁");
                    }
                });
                for (int i = 0; i < 9; i++) {
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, itemStack);
                    }
                }
                player.closeInventory();
                InventoryView a = player.openInventory(inv);
                GUI_save.put(player.getName(), inv);
            }
        }
    }

    @EventHandler
    public void sel_gui(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player player = (Player) e.getWhoClicked();
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand == null)
                return;
            if (GUI_save.containsKey(player.getName())) {
                e.setCancelled(true);
                Inventory inv = GUI_save.get(player.getName());
                if (inv.getViewers().equals(player)) {
                    ItemStack item = inv.getItem(e.getSlot());
                    NBTTagCompound ItemNbt = new NBT_set().NBT_get(item);
                    if (!ItemNbt.getBoolean("disable"))
                        player.sendMessage("§d[HeartAge_year]§c传送石的这个槽位未解锁");
                    else if (e.getClick() == ClickType.LEFT) {
                        int x = ItemNbt.getInt("x");
                        int y = ItemNbt.getInt("y");
                        int z = ItemNbt.getInt("z");
                        player.teleport(new org.bukkit.Location(player.getWorld(), x, y, z));
                        player.sendMessage("§d[HeartAge_year]§b已将你传送至目的坐标");
                    } else if (e.getClick() == ClickType.RIGHT) {
                        org.bukkit.Location location = player.getLocation();
                        ItemNbt = new NBT_set().NBT_get(hand);
                        String uuid = ItemNbt.getString("uuid");
                        if (!tpStone_do.toStone_save.containsKey(uuid)) {
                            player.sendMessage("§d[HeartAge_year]§c你的传送石异常");
                        } else {
                            tpStone stone = tpStone_do.toStone_save.get(uuid);
                            tpStone_set set = new tpStone_set(stone);
                            Location location1 = new Location();
                            location1.setX((int) location.getX());
                            location1.setY((int) location.getY());
                            location1.setZ((int) location.getZ());
                            set.setsel(tpStone_set.sel_list.get(e.getSlot()), location1);
                            new tpStone_Read().save(stone, uuid);
                            player.sendMessage("§d[HeartAge_year]§b已保存你脚下的坐标");
                        }
                    }
                }
                player.closeInventory();
                GUI_save.remove(player.getName());
            }
        }
    }
}
