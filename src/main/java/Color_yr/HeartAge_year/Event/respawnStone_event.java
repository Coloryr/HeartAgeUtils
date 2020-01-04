package Color_yr.HeartAge_year.Event;

import Color_yr.HeartAge_year.Config.Config_Read;
import Color_yr.HeartAge_year.Config.respawnStone_Read;
import Color_yr.HeartAge_year.HeartAge_year;
import Color_yr.HeartAge_year.Obj.Death_Obj;
import Color_yr.HeartAge_year.Obj.respawnStone_save_Obj;
import Color_yr.HeartAge_year.respawnStone.respawnStone_do;
import Color_yr.HeartAge_year.util.NBT_set;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static Color_yr.HeartAge_year.Config.Config_Read.lan;

public class respawnStone_event implements Listener {
    @EventHandler
    public void item_click(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null)
            return;
        Material test = item.getType();
        if (test.equals(respawnStone_do.item)) {
            NBT_set NBT_set = new NBT_set();
            NBTTagCompound ItemNbt = NBT_set.NBT_get(item);
            if (!ItemNbt.hasKey("uuid"))
                return;
            e.setCancelled(true);
            String uuid = ItemNbt.getString("uuid");
            Player player = e.getPlayer();
            if (!respawnStone_do.respawnStone_save.containsKey(uuid)) {
                player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.0f);
                player.sendMessage(lan.getTitle() + lan.getRespawnStone_no_date());
                return;
            }
            respawnStone_save_Obj obj = respawnStone_do.respawnStone_save.get(uuid);
            switch (e.getAction()) {
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    if (obj.isUse()) {
                        obj.setUse(false);
                        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                        ItemNbt.setBoolean("use", obj.isUse());
                        item = NBT_set.NBT_save(item, ItemNbt);
                        ItemMeta Mete = item.getItemMeta();
                        List<String> a = Mete.getLore();
                        a.set(0, lan.getRespawnStone_now() + lan.getRespawnStone_off());
                        Mete.setLore(a);
                        item.setItemMeta(Mete);
                        item.setType(test);
                    } else {
                        obj.setUse(true);
                        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1.0f, 1.0f);
                        ItemNbt.setBoolean("use", obj.isUse());
                        item = NBT_set.NBT_save(item, ItemNbt);
                        ItemMeta Mete = item.getItemMeta();
                        List<String> a = Mete.getLore();
                        a.set(0, lan.getRespawnStone_now() + lan.getRespawnStone_on());
                        Mete.setLore(a);
                        item.setItemMeta(Mete);
                        item.setType(test);
                    }
                    break;
                case RIGHT_CLICK_AIR:
                case RIGHT_CLICK_BLOCK:
                    player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1.0f, 1.0f);
                    Location location = player.getLocation();
                    obj.setX((int) location.getX());
                    obj.setY((int) location.getY());
                    obj.setZ((int) location.getZ());
                    item = NBT_set.NBT_save(item, ItemNbt);
                    ItemMeta Mete = item.getItemMeta();
                    List<String> a = Mete.getLore();
                    a.set(2, "§aX：§b" + obj.getX() + " §aY：§b" + obj.getY() + " §aZ：§b" + obj.getZ());
                    Mete.setLore(a);
                    item.setItemMeta(Mete);
                    item.setType(test);
                    player.sendMessage(lan.getRespawnStone_set());
                    break;
            }
            new respawnStone_Read().save(obj, uuid);
            respawnStone_do.respawnStone_save.put(uuid, obj);
            player.getInventory().setItemInMainHand(item);
        }
    }

    @EventHandler
    public void PlayerDeath(final PlayerDeathEvent e) {
        Player player = e.getEntity();
        e.setDeathMessage(null);
        boolean isspawn = true;
        Location b = player.getLocation();
        Bukkit.getScheduler().scheduleSyncDelayedTask(HeartAge_year.plugin, () -> {
            player.spigot().respawn();
            player.teleport(b);
        }, 1L);
        Death_Obj obj = Config_Read.main_config.getDeath();

        Location location = new Location(player.getWorld(), obj.getLocation().getX(), obj.getLocation().getY(), obj.getLocation().getZ());
        if (e.getEntity().isOp()) {
            return;
        }
        if (player.getInventory().contains(respawnStone_do.item)) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType().equals(respawnStone_do.item)) {
                    NBTTagCompound nbt = new NBT_set().NBT_get(item);
                    if (nbt.hasKey("uuid") && respawnStone_do.respawnStone_save.containsKey(nbt.getString("uuid"))) {
                        respawnStone_save_Obj obj1 = respawnStone_do.respawnStone_save.get(nbt.getString("uuid"));
                        if (obj1.isUse()) {
                            location = new Location(Bukkit.getWorld(obj1.getWorld()), obj1.getX(), obj1.getY(), obj1.getZ());
                            isspawn = false;
                            break;
                        }
                    }
                }
            }
        }

        Location finalLocation1 = location;
        boolean finalIsspawn = isspawn;
        new Thread(() -> {
            try {
                Bukkit.getScheduler().runTask(HeartAge_year.plugin, () -> player.setGameMode(GameMode.SPECTATOR));

                for (int a = 0; a < obj.getTime(); a++) {
                    player.sendTitle(finalIsspawn ? obj.getTitle() : obj.getTitle1(), obj.getLocation().getName() + (obj.getTime() - a), 10, 20, 10);
                    Thread.sleep(1000);
                }
                Bukkit.getScheduler().runTask(HeartAge_year.plugin, () -> player.setGameMode(GameMode.ADVENTURE));
                player.setHealth(20);
                Bukkit.getScheduler().runTask(HeartAge_year.plugin, () ->
                        player.teleport(finalLocation1));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
