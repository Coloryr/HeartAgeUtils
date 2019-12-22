package Color_yr.HeartAge_year.tpStone;

import Color_yr.HeartAge_year.Obj.Location_Obj;
import Color_yr.HeartAge_year.Obj.tpStone_save_Obj;
import Color_yr.HeartAge_year.Config.tpStone_Read;
import Color_yr.HeartAge_year.util.NBT_set;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tpStone_do {
    public static Material item;
    public static Material update_item;
    public static Map<String, tpStone_save_Obj> toStone_save = new HashMap<>();

    public ItemStack new_tpStone(String uuid) {//获得新的传送石
        if(tpStone_do.item == null)
            return null;
        ItemStack item = new ItemStack(tpStone_do.item);
        tpStone_save_Obj obj = new tpStone_save_Obj();
        obj.setSlot(1);
        tpStone_set set = new tpStone_set(obj);
        set.setsel(tpStone_set.sel_list.get(0), new Location_Obj());
        tpStone_do.toStone_save.put(uuid, obj);
        ItemMeta ItemMeta = item.getItemMeta();
        ItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c新的传送石"));
        ItemMeta.setLore(new ArrayList<String>() {
            {
                this.add(ChatColor.translateAlternateColorCodes('&', "&b传送石槽位&d：&a1"));
            }
        });
        item.setItemMeta(ItemMeta);
        NBT_set NBT_set= new NBT_set();
        NBTTagCompound nbt = NBT_set.NBT_get(item);
        nbt.setString("uuid", uuid);
        item = NBT_set.NBT_save(item, nbt);
        new tpStone_Read().save(obj, uuid);
        return item;
    }

    public String up_tpStone(ItemStack item) {//升级传送石
        NBTTagCompound nbt = new NBT_set().NBT_get(item);
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStone_save.containsKey(uuid)) {
                tpStone_save_Obj stone = toStone_save.get(uuid);
                if (stone.getSlot() == 9)
                    return "c传送石槽位已满";
                stone.setSlot(stone.getSlot() + 1);
                new tpStone_set(stone).setsel(tpStone_set.sel_list.get(stone.getSlot()), new Location_Obj());
                new tpStone_Read().save(stone, uuid);
                return "b传送石已升级";
            }
        }
        return "c传送石异常";
    }

    public String rename_tpStone(ItemStack item, String new_name) {//重命名传送石
        NBTTagCompound nbt = new NBT_set().NBT_get(item);
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStone_save.containsKey(uuid)) {
                tpStone_save_Obj stone = toStone_save.get(uuid);
                stone.setName(new_name);
                ItemMeta temp = item.getItemMeta();
                temp.setDisplayName(new_name);
                item.setItemMeta(temp);
                new tpStone_set(stone).setsel(tpStone_set.sel_list.get(stone.getSlot()), new Location_Obj());
                new tpStone_Read().save(stone, uuid);
                return "b传送石已重命名";
            }
        }
        return "c传送石异常";
    }
}