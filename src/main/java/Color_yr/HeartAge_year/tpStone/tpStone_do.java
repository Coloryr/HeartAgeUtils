package Color_yr.HeartAge_year.tpStone;

import Color_yr.HeartAge_year.Config.tpStone_Read;
import Color_yr.HeartAge_year.Obj.Location_Obj;
import Color_yr.HeartAge_year.Obj.tpStone_save_Obj;
import Color_yr.HeartAge_year.util.NBT_set;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Color_yr.HeartAge_year.Config.Config_Read.lan;

public class tpStone_do {
    public static Material item;
    public static Material update_item;
    public static Map<String, tpStone_save_Obj> toStone_save = new HashMap<>();

    public ItemStack new_tpStone(String uuid) {//获得新的传送石
        if (tpStone_do.item == null)
            return null;
        ItemStack item = new ItemStack(tpStone_do.item);
        tpStone_save_Obj obj = new tpStone_save_Obj();
        obj.setSlot(1);
        tpStone_set set = new tpStone_set(obj);
        set.set_sel(0, new Location_Obj(lan.getNew_point()));
        tpStone_do.toStone_save.put(uuid, obj);
        ItemMeta ItemMeta = item.getItemMeta();
        ItemMeta.setDisplayName(lan.getNew_stone());
        obj.setName(lan.getNew_stone());
        ItemMeta.setLore(new ArrayList<String>() {
            {
                this.add(lan.getTpStone_slot() + "1");
            }
        });
        item.setItemMeta(ItemMeta);
        NBT_set NBT_set = new NBT_set();
        NBTTagCompound nbt = NBT_set.NBT_get(item);
        nbt.setString("uuid", uuid);
        item = NBT_set.NBT_save(item, nbt);
        new tpStone_Read().save(obj, uuid);
        return item;
    }

    public String up_tpStone(Player player) {//升级传送石
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        NBTTagCompound nbt = new NBT_set().NBT_get(item);
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStone_save.containsKey(uuid)) {
                tpStone_save_Obj stone = toStone_save.get(uuid);
                int a = stone.getSlot();
                if (a == 9)
                    return lan.getTpStone_slot_full();
                stone.setSlot(a + 1);
                List<String> lore = meta.getLore();
                lore.set(0, lore.get(0).replace(String.valueOf(a), String.valueOf(a + 1)));
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.getInventory().setItemInMainHand(item);
                new tpStone_set(stone).set_sel(a, new Location_Obj(lan.getNew_point()));
                new tpStone_Read().save(stone, uuid);
                ItemStack offhand = player.getInventory().getItemInOffHand();
                if (offhand.getAmount() > 1) {
                    offhand.setAmount(offhand.getAmount() - 1);
                    player.getInventory().setItemInOffHand(offhand);
                } else
                    player.getInventory().setItemInOffHand(null);
                return lan.getTpStone_update();
            }
        }
        return lan.getTpStone_error();
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
                new tpStone_Read().save(stone, uuid);
                return lan.getTpStone_rename();
            }
        }
        return lan.getTpStone_error();
    }

    public String set_slot_name(ItemStack item, int slot, String new_name) {  //设置传送点名字
        NBTTagCompound nbt = new NBT_set().NBT_get(item);
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStone_save.containsKey(uuid)) {
                tpStone_save_Obj stone = toStone_save.get(uuid);
                if (slot < 1)
                    return lan.getTpStone_no_slot();
                if (slot > stone.getSlot())
                    return lan.getTpStone_unlock_slot();
                tpStone_set set = new tpStone_set(stone);
                Location_Obj locationObj = set.get_sel(slot - 1);
                locationObj.setName(new_name);
                set.set_sel(slot - 1, locationObj);
                new tpStone_Read().save(stone, uuid);
                return lan.getTpStone_slot_rename();
            }
        }
        return lan.getTpStone_error();
    }
}