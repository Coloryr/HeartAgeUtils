package Color_yr.HeartAge_year.respawnStone;

import Color_yr.HeartAge_year.Config.respawnStone_Read;
import Color_yr.HeartAge_year.Obj.respawnStone_save_Obj;
import Color_yr.HeartAge_year.util.NBT_set;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Color_yr.HeartAge_year.Config.Config_Read.lan;

public class respawnStone_do {
    public static Material item;
    public static Map<String, respawnStone_save_Obj> respawnStone_save = new HashMap<>();

    public ItemStack new_respawnStone(String uuid, String world) {//获得新的复活石
        if (respawnStone_do.item == null)
            return null;
        ItemStack item = new ItemStack(respawnStone_do.item);
        respawnStone_save_Obj obj = new respawnStone_save_Obj();
        respawnStone_do.respawnStone_save.put(uuid, obj);
        ItemMeta ItemMeta = item.getItemMeta();
        ItemMeta.setDisplayName(lan.getNew_respawnStone());
        ItemMeta.setLore(new ArrayList<String>() {
            {
                this.add(lan.getRespawnStone_now() + lan.getRespawnStone_off());
                this.add(lan.getTpStone_show());
                this.add("§aX：§b" + obj.getX() + " §aY：§b" + obj.getY() + " §aZ：§b" + obj.getZ());
            }
        });
        obj.setName(lan.getNew_respawnStone());
        obj.setUse(false);
        obj.setWorld(world);
        item.setItemMeta(ItemMeta);
        NBT_set NBT_set = new NBT_set();
        NBTTagCompound nbt = NBT_set.NBT_get(item);
        nbt.setString("uuid", uuid);
        nbt.setBoolean("use", false);
        item = NBT_set.NBT_save(item, nbt);
        new respawnStone_Read().save(obj, uuid);
        return item;
    }

    public String rename_respawnStone(ItemStack item, String new_name) {
        NBTTagCompound nbt = new NBT_set().NBT_get(item);
        if (nbt.hasKey("uuid")) {
            String uuid = nbt.getString("uuid");
            if (respawnStone_save.containsKey(uuid)) {
                respawnStone_save_Obj stone = respawnStone_save.get(uuid);
                stone.setName(new_name);
                ItemMeta temp = item.getItemMeta();
                temp.setDisplayName(new_name);
                item.setItemMeta(temp);
                new respawnStone_Read().save(stone, uuid);
                return lan.getTpStone_rename();
            }
        }
        return lan.getTpStone_error();
    }
}
