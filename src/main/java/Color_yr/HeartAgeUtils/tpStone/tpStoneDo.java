package Color_yr.HeartAgeUtils.tpStone;

import Color_yr.HeartAgeUtils.Config.tpStoneRead;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.LanguageObj;
import Color_yr.HeartAgeUtils.Obj.LocationObj;
import Color_yr.HeartAgeUtils.Obj.tpStoneSaveObj;
import Color_yr.HeartAgeUtils.Utils.NBTSet;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tpStoneDo {
    public static Material item;
    public static Material updateItem;
    public static Map<String, tpStoneSaveObj> toStoneSave = new HashMap<>();

    public ItemStack new_tpStone(String uuid) {//获得新的传送石
        if (item == null)
            return null;
        LanguageObj lan = HeartAgeUtils.ConfigMain.lan;
        ItemStack item = new ItemStack(tpStoneDo.item);
        tpStoneSaveObj obj = new tpStoneSaveObj();
        obj.setSlot(1);
        tpStoneObjSet set = new tpStoneObjSet(obj);
        set.setSel(0, new LocationObj(lan.getNew_point()));
        tpStoneDo.toStoneSave.put(uuid, obj);
        ItemMeta ItemMeta = item.getItemMeta();
        ItemMeta.setDisplayName(lan.getNew_stone());
        obj.setName(lan.getNew_stone());
        ItemMeta.setLore(new ArrayList<String>() {
            {
                this.add(lan.getTpStone_slot() + "1");
            }
        });
        item.setItemMeta(ItemMeta);
        NBTTagCompound nbt = NBTSet.NBT_get(item);
        nbt.setString("uuid", uuid);
        item = NBTSet.NBT_save(item, nbt);
        new tpStoneRead().save(obj, uuid);
        return item;
    }

    public String up_tpStone(Player player) {//升级传送石
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        NBTTagCompound nbt = NBTSet.NBT_get(item);
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStoneSave.containsKey(uuid)) {
                tpStoneSaveObj stone = toStoneSave.get(uuid);
                int a = stone.getSlot();
                if (a == 9)
                    return lan.getTpStone_slot_full();
                stone.setSlot(a + 1);
                List<String> lore = meta.getLore();
                lore.set(0, lore.get(0).replace(String.valueOf(a), String.valueOf(a + 1)));
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.getInventory().setItemInMainHand(item);
                new tpStoneObjSet(stone).setSel(a, new LocationObj(lan.getNew_point()));
                new tpStoneRead().save(stone, uuid);
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
        NBTTagCompound nbt = NBTSet.NBT_get(item);
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStoneSave.containsKey(uuid)) {
                tpStoneSaveObj stone = toStoneSave.get(uuid);
                stone.setName(new_name);
                ItemMeta temp = item.getItemMeta();
                temp.setDisplayName(new_name);
                item.setItemMeta(temp);
                new tpStoneRead().save(stone, uuid);
                return lan.getTpStone_rename();
            }
        }
        return lan.getTpStone_error();
    }

    public String set_slot_name(ItemStack item, int slot, String new_name) {  //设置传送点名字
        NBTTagCompound nbt = NBTSet.NBT_get(item);
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStoneSave.containsKey(uuid)) {
                tpStoneSaveObj stone = toStoneSave.get(uuid);
                if (slot < 1)
                    return lan.getTpStone_no_slot();
                if (slot > stone.getSlot())
                    return lan.getTpStone_unlock_slot();
                tpStoneObjSet set = new tpStoneObjSet(stone);
                LocationObj locationObj = set.getSel(slot - 1);
                locationObj.setName(new_name);
                set.setSel(slot - 1, locationObj);
                new tpStoneRead().save(stone, uuid);
                return lan.getTpStone_slot_rename();
            }
        }
        return lan.getTpStone_error();
    }
}