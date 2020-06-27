package Color_yr.HeartAgeUtils.Drawer;

import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Hook.Hook;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.Obj.posObj;
import Color_yr.HeartAgeUtils.Utils.ItemNBTSet;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class drawerDo {
    public static Material block;
    public static final Map<String, drawerSaveObj> drawerList = new HashMap<>();

    public static ItemStack getDrawer() {
        if (block == null)
            return null;
        ItemStack item = new ItemStack(block);
        UUID uuid;
        String uuids;
        do {
            uuid = UUID.randomUUID();
            uuids = uuid.toString().replace("-", "");
        } while (drawerList.containsKey(uuids));
        drawerSaveObj obj = new drawerSaveObj(uuids);
        languageObj lan = HeartAgeUtils.configMain.lan;
        drawerList.put(uuids, obj);
        ItemNBTSet nbt = new ItemNBTSet(item);
        nbt.setNbt("type", "drawer");
        nbt.setNbt("uuid", uuids);
        item = nbt.saveNBT();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(lan.getDrawerName());
        List<String> lore = new ArrayList<>();
        String data = lan.getDrawerCheck();
        data = data.replace("%Item%", "无")
                .replace("%Amount%", "0");
        lore.add(data);
        meta.setLore(lore);
        item.setItemMeta(meta);
        configMain.drawer.save(obj, uuids);

        return item;
    }

    public static drawerSaveObj getDrawerSave(Player player, Block block) {
        languageObj lan = HeartAgeUtils.configMain.lan;
        if (block.getType().equals(drawerDo.block)) {
            for (drawerSaveObj obj : drawerList.values()) {
                if (obj.checkPos(block.getLocation())) {
                    if (!Hook.isAllowed(player, block)) {
                        player.sendMessage(lan.getDrawerLock());
                    }
                    return obj;
                }
            }
        }
        return null;
    }

    public static boolean setLocal(Block block, String uuid) {
        if (!drawerList.containsKey(uuid))
            return false;
        drawerSaveObj obj = drawerList.get(uuid);
        obj.setPos(new posObj(block.getLocation()));
        drawerList.put(uuid, obj);
        return true;
    }
}
