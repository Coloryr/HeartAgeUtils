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
    public static Map<String, tpStone> toStone_save = new HashMap<>();

    public ItemStack new_tpStone(UUID uuid) {//获得新的传送石
        if(tpStone_do.item == null)
            return null;
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
        NBTTagCompound nbt = new NBT_set().NBT_get(item);
        nbt.setString("uuid", uuid.toString());
        return item;
    }

    public String up_tpStone(ItemStack item) {//升级传送石
        NBTTagCompound nbt = new NBT_set().NBT_get(item);
        if (nbt.hasKeyOfType("uuid", 3)) {
            String uuid = nbt.getString("uuid");
            if (toStone_save.containsKey(uuid)) {
                tpStone stone = toStone_save.get(uuid);
                if (stone.getSlot() == 9)
                    return "c传送石槽位已满";
                stone.setSlot(stone.getSlot() + 1);
                new tpStone_set(stone).setsel(tpStone_set.sel_list.get(stone.getSlot()), new Location());
                new tpStone_Read().save(stone, uuid);
                return "b传送石已升级";
            }
        }
        return "c传送石异常";
    }

    public String rename_tpStone(ItemStack item, String new_name) {//重命名传送石
        NBTTagCompound nbt = new NBT_set().NBT_get(item);
        if (nbt.hasKeyOfType("uuid", 3)) {
            String uuid = nbt.getString("uuid");
            if (toStone_save.containsKey(uuid)) {
                tpStone stone = toStone_save.get(uuid);
                stone.setName(new_name);
                item.getItemMeta().setDisplayName(new_name);
                new tpStone_set(stone).setsel(tpStone_set.sel_list.get(stone.getSlot()), new Location());
                new tpStone_Read().save(stone, uuid);
                return "b传送石已重命名";
            }
        }
        return "c传送石异常";
    }
}