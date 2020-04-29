package Color_yr.HeartAgeUtils.Drawer;

import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Hook.Hook;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.Utils.itemNBTSet;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.MetadataValueAdapter;

import java.util.*;

public class drawerDo {
    public static Material block;
    public static final Map<String, drawerSaveObj> drawerList = new HashMap<>();

    public static MetadataValue getTag(String uuid) {
        return new MetadataValueAdapter(HeartAgeUtils.plugin) {
            @Override
            public Object value() {
                return uuid;
            }

            @Override
            public void invalidate() {

            }
        };
    }

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
        NBTTagCompound nbt = itemNBTSet.getNBT(item);
        nbt.setString("type", "drawer");
        nbt.setString("uuid", uuids);
        item = itemNBTSet.saveNBT(item, nbt);
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
        if (block.getType().equals(drawerDo.block)) {
            List<MetadataValue> list = block.getMetadata("uuid");
            if (list.size() > 0) {
                languageObj lan = HeartAgeUtils.configMain.lan;
                String uuid = list.get(0).asString();
                if (!Hook.isAllowed(player, block)) {
                    player.sendMessage(lan.getDrawerLock());
                }
                drawerSaveObj obj = drawerDo.drawerList.get(uuid);
                if (obj == null) {
                    player.sendMessage(lan.getDrawerError());
                    block.setType(Material.AIR);
                    return null;
                }
                return obj;
            }
        }
        return null;
    }
}
