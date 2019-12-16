package Color_yr.HeartAge_year.Event;

import Color_yr.HeartAge_year.Config.tpStone.NBTset;
import Color_yr.HeartAge_year.Config.tpStone.Obj.tpStone;
import Color_yr.HeartAge_year.Config.tpStone.tpStone_do;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Item_event implements Listener {

    @EventHandler
    public void itemclick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack item = e.getItem();
            if (item == null)
                return;
            if (item.equals(tpStone_do.item)) {
                NBTTagCompound ItemNbt = new NBTset().NBT_get(item);
                if (!ItemNbt.hasKeyOfType("Pack", 2))
                    return;
                String pack_name = ItemNbt.getString("uuid");
                if (!tpStone_do.toStone_save.containsKey(pack_name)) {
                    e.getPlayer().sendMessage("§d[HeartAge_year]§c没有这个背包的数据");
                    return;
                }
                tpStone obj = tpStone_do.toStone_save.get(pack_name);
                Inventory inv = Bukkit.createInventory(e.getPlayer(), InventoryType.CREATIVE, "传送石" + obj.getName() + "设置");
            }
        }
    }
}
