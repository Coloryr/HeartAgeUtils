package Color_yr.HeartAgeUtils.Event;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.OreGen.oreGenDo;
import Color_yr.HeartAgeUtils.Utils.Exp;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class oreGen implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void OnPlayerJoin(PlayerJoinEvent e) {
        oreGenDo.init(e.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void OnBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (oreGenDo.itemStacks == null)
            return;
        Player player = e.getPlayer();
        if (!oreGenDo.isEnable(player.getName()))
            return;
        if (!oreGenDo.Blocks.contains(e.getBlock().getType()))
            return;
        languageObj lan = HeartAgeUtils.configMain.lan;
        if (Exp.getExpPoints(player) < HeartAgeUtils.configMain.Config.getOreGen().getExtCost()) {
            oreGenDo.disable(player.getName());
            player.sendMessage(lan.getOreGenAuto());
            return;
        }
        Exp.subtractExpPoints(player, HeartAgeUtils.configMain.Config.getOreGen().getExtCost());
        Material ore = oreGenDo.getOre();
        if (ore != Material.AIR) {
            ItemStack item = new ItemStack(ore);
            player.getLocation().getWorld().dropItem(e.getBlock().getLocation(), item);
            player.sendMessage(lan.getOreGenGet().replace("%ore%", oreGenDo.lan.get(ore)));
        }
    }
}
