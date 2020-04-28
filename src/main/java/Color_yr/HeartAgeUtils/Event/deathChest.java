package Color_yr.HeartAgeUtils.Event;

import Color_yr.HeartAgeUtils.DeathChest.deathChestConfigObj;
import Color_yr.HeartAgeUtils.DeathChest.deathChestDo;
import Color_yr.HeartAgeUtils.DeathChest.playSetObj;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Hook.Hook;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class deathChest implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onplayerDeath(PlayerDeathEvent e) {
        if (e.getDrops().size() == 0)
            return;
        Player player = e.getEntity();
        playSetObj set = deathChestDo.getPlayerSet(player.getUniqueId(), true);
        deathChestConfigObj config = HeartAgeUtils.ConfigMain.Config.getDeathChest();
        languageObj lan = HeartAgeUtils.ConfigMain.lan;
        switch (set.getMode()) {
            case 1: {
                if (Hook.vaultCheck(player,
                        config.getCost().getSaveInLocal())) {
                    deathChestDo.RE re = deathChestDo.genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(lan.getDeathChestCantGen());
                    } else {
                        deathChestDo.GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney());
                }
                break;
            }
            case 2:
                if (Hook.vaultCheck(player,
                        config.getCost().getSaveInChest())) {
                    if (!deathChestDo.setChest(e)) {
                        player.sendMessage(lan.getDeathChestError());
                    } else {
                        deathChestDo.GenLocalChest(player);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney1());
                }
                break;
            case 3: {
                if (Hook.vaultCheck(player,
                        config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();

                } else {
                    player.sendMessage(lan.getDeathChestNoMoney2());
                }
                break;
            }
            case 4: {
                if (Hook.vaultCheck(player,
                        config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    deathChestDo.NoDrop(player);
                    return;
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInChest())) {
                    if (deathChestDo.setChest(e)) {
                        deathChestDo.GenLocalChest(player);
                        return;
                    }
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInLocal())) {
                    deathChestDo.RE re = deathChestDo.genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(lan.getDeathChestCantGen());
                    } else {
                        deathChestDo.GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney());
                }
                break;
            }
            case 5: {
                if (Hook.vaultCheck(player,
                        config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    deathChestDo.NoDrop(player);
                    return;
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInChest())) {
                    if (!deathChestDo.setChest(e)) {
                        player.sendMessage(lan.getDeathChestError());
                    } else {
                        deathChestDo.GenLocalChest(player);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney1());
                }
                break;
            }
            case 6: {
                if (Hook.vaultCheck(player,
                        config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    deathChestDo.NoDrop(player);
                    return;
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInLocal())) {
                    deathChestDo.RE re = deathChestDo.genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(lan.getDeathChestCantGen());
                    } else {
                        deathChestDo.GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney());
                }
                break;
            }
            case 7: {
                if (Hook.vaultCheck(player,
                        config.getCost().getSaveInChest())) {
                    if (deathChestDo.setChest(e)) {
                        deathChestDo.GenLocalChest(player);
                        return;
                    }
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInLocal())) {
                    deathChestDo.RE re = deathChestDo.genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(lan.getDeathChestCantGen());
                    } else {
                        deathChestDo.GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney());
                }
                break;
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        deathChestConfigObj config = HeartAgeUtils.ConfigMain.Config.getDeathChest();
        languageObj lan = HeartAgeUtils.ConfigMain.lan;
        Player player = e.getPlayer();
        playSetObj save = deathChestDo.getPlayerSet(player.getUniqueId(), true);
        String temp = lan.getDeathChestMode();
        int b = 0;
        int mode = save.getMode();
        if (config.getDisable().contains(mode)) {
            for (int a : config.getDisable()) {
                if (a == b)
                    b++;
                else {
                    save.setMode(b);
                    deathChestDo.setPlayerSet(player.getUniqueId(), save);
                    break;
                }
            }
        }
        temp = temp.replace("%Mode%", "" + mode);
        player.sendMessage(temp);
    }

    @EventHandler
    public void de(BlockBreakEvent e) {
        NBTTagCompound nbt = blockNBTSet.getNBT(e.getBlock()).b();
        if (nbt != null && nbt.hasKey("Nodrop")) {
            e.getPlayer().sendMessage("Has");
        }
    }

}
