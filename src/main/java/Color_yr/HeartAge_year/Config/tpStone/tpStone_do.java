package Color_yr.HeartAge_year.Config.tpStone;

import Color_yr.HeartAge_year.Config.tpStone.Obj.Location;
import Color_yr.HeartAge_year.Config.tpStone.Obj.tpStone;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class tpStone_do {
    public static Material item;
    public static Material updata_item;
    public static Map<String, Color_yr.HeartAge_year.Config.tpStone.Obj.tpStone> toStone_save = new HashMap<>();

    public ItemStack new_tpStone(UUID uuid) {//获得新的传送石
        ItemStack item = new ItemStack(tpStone_do.item);
        tpStone obj = new tpStone();
        obj.setSlot(1);
        tpStone_set set = new tpStone_set(obj);
        set.setsel(tpStone_set.sel_list.get(0), new Location());
        tpStone_do.toStone_save.put(uuid.toString(), obj);
        ItemMeta ItemMeta = item.getItemMeta();
        assert ItemMeta != null;
        ItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c新的传送石"));
        ItemMeta.setLore(new ArrayList<String>() {
            {
                this.add(ChatColor.translateAlternateColorCodes('&', "&b传送石槽位&d：&a1"));
            }
        });
        NBTTagCompound nbt = new NBTset().NBT_get(item);
        nbt.setString("uuid", uuid.toString());
        return item;
    }
}
