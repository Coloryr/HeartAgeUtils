package Color_yr.HeartAgeUtils.tpStone;

import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.languageObj;
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

    public static ItemStack newTpStone(String uuid) {//获得新的传送石
        if (item == null)
            return null;
        languageObj lan = HeartAgeUtils.ConfigMain.lan;
        ItemStack item = new ItemStack(tpStoneDo.item);
        tpStoneSaveObj obj = new tpStoneSaveObj();
        obj.setSlot(1);
        tpStoneObjSet set = new tpStoneObjSet(obj);
        set.setSel(0, new locationObj(lan.getNewPoint()));
        tpStoneDo.toStoneSave.put(uuid, obj);
        ItemMeta ItemMeta = item.getItemMeta();
        ItemMeta.setDisplayName(lan.getNewStone());
        obj.setName(lan.getNewStone());
        ItemMeta.setLore(new ArrayList<String>() {
            {
                this.add(lan.getTpStoneSlot() + "1");
            }
        });
        item.setItemMeta(ItemMeta);
        NBTTagCompound nbt = NBTSet.NBT_get(item);
        nbt.setString("uuid", uuid);
        item = NBTSet.NBT_save(item, nbt);
        configMain.tpStone.save(obj, uuid);
        return item;
    }

    public static String upTpStone(Player player) {//升级传送石
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        NBTTagCompound nbt = NBTSet.NBT_get(item);
        languageObj lan = HeartAgeUtils.ConfigMain.lan;
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStoneSave.containsKey(uuid)) {
                tpStoneSaveObj stone = toStoneSave.get(uuid);
                int a = stone.getSlot();
                if (a == 9)
                    return lan.getTpStoneSlotFull();
                stone.setSlot(a + 1);
                List<String> lore = meta.getLore();
                lore.set(0, lore.get(0).replace(String.valueOf(a), String.valueOf(a + 1)));
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.getInventory().setItemInMainHand(item);
                new tpStoneObjSet(stone).setSel(a, new locationObj(lan.getNewPoint()));
                configMain.tpStone.save(stone, uuid);
                ItemStack offhand = player.getInventory().getItemInOffHand();
                if (offhand.getAmount() > 1) {
                    offhand.setAmount(offhand.getAmount() - 1);
                    player.getInventory().setItemInOffHand(offhand);
                } else
                    player.getInventory().setItemInOffHand(null);
                return lan.getTpStoneUpdate();
            }
        }
        return lan.getTpStoneError();
    }

    public static String renameTpStone(ItemStack item, String new_name) {//重命名传送石
        NBTTagCompound nbt = NBTSet.NBT_get(item);
        languageObj lan = HeartAgeUtils.ConfigMain.lan;
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStoneSave.containsKey(uuid)) {
                tpStoneSaveObj stone = toStoneSave.get(uuid);
                stone.setName(new_name);
                ItemMeta temp = item.getItemMeta();
                temp.setDisplayName(new_name);
                item.setItemMeta(temp);
                configMain.tpStone.save(stone, uuid);
                return lan.getTpStoneRename();
            }
        }
        return lan.getTpStoneError();
    }

    public static String setSlotName(ItemStack item, int slot, String new_name) {  //设置传送点名字
        NBTTagCompound nbt = NBTSet.NBT_get(item);
        languageObj lan = HeartAgeUtils.ConfigMain.lan;
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (toStoneSave.containsKey(uuid)) {
                tpStoneSaveObj stone = toStoneSave.get(uuid);
                if (slot < 1)
                    return lan.getTpStoneNoSlot();
                if (slot > stone.getSlot())
                    return lan.getTpStoneUnlockSlot();
                tpStoneObjSet set = new tpStoneObjSet(stone);
                locationObj locationObj = set.getSel(slot - 1);
                locationObj.setName(new_name);
                set.setSel(slot - 1, locationObj);
                configMain.tpStone.save(stone, uuid);
                return lan.getTpStoneSlotRename();
            }
        }
        return lan.getTpStoneError();
    }
}