package Color_yr.HeartAge_year.Event;

import Color_yr.HeartAge_year.Obj.Location_Obj;
import Color_yr.HeartAge_year.util.NBT_set;
import Color_yr.HeartAge_year.Obj.tpStone_save_Obj;
import Color_yr.HeartAge_year.Config.tpStone_Read;
import Color_yr.HeartAge_year.tpStone.tpStone_do;
import Color_yr.HeartAge_year.tpStone.tpStone_set;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Item_event implements Listener {

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
            String uuid = ItemNbt.getString("uuid");
            Player player = e.getPlayer();
            if (!tpStone_do.toStone_save.containsKey(uuid)) {
                player.sendMessage("§d[HeartAge_year]§c没有这个传送石的数据");
                return;
            }
            tpStone_save_Obj obj = tpStone_do.toStone_save.get(uuid);
            Inventory inv = Bukkit.createInventory(e.getPlayer(), InventoryType.DISPENSER, "传送石" + obj.getName() + "设置");
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
                temp1.setDisplayName("坐标" + slot);
                temp1.setLore(new ArrayList<String>() {{
                    this.add("§e设置的坐标：");
                    this.add("§aX：§b" + locationObj.getX() + " §aY：§b" + locationObj.getY() + " §aZ：§b" + locationObj.getZ());
                }});
                itemStack.setItemMeta(temp1);
                inv.setItem(Integer.decode(slot) - 1, itemStack);
            }
            itemStack = new ItemStack(Material.BARRIER);
            temp1 = itemStack.getItemMeta();
            temp1.setDisplayName("§4未解锁");
            temp1.setLore(new ArrayList<String>() {
                {
                    this.add("使用升级石来解锁");
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
            GUI_save.put(player.getName(), player.openInventory(inv));
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
                e.setCancelled(true);
                InventoryView inv = GUI_save.get(player.getName());
                if (inv.getPlayer().equals(player)) {
                    ItemStack item = inv.getItem(e.getSlot());
                    NBTTagCompound ItemNbt = new NBT_set().NBT_get(item);
                    if(!ItemNbt.hasKey("disable") || (!ItemNbt.hasKey("x") || !ItemNbt.hasKey("y") || !ItemNbt.hasKey("z")))
                        return;
                    if (ItemNbt.getBoolean("disable"))
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
                            tpStone_save_Obj stone = tpStone_do.toStone_save.get(uuid);
                            tpStone_set set = new tpStone_set(stone);
                            Location_Obj location1 = new Location_Obj();
                            location1.setX((int) location.getX());
                            location1.setY((int) location.getY());
                            location1.setZ((int) location.getZ());
                            set.setsel(tpStone_set.sel_list.get(e.getSlot()), location1);
                            new tpStone_Read().save(stone, uuid);
                            tpStone_do.toStone_save.put(uuid, stone);
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
