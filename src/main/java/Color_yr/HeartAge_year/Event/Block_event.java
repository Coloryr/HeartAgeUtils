package Color_yr.HeartAge_year.Event;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class Block_event implements Listener {

    private boolean hasPerm(final Location loc, final Player player) {
        final ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);
        if (res == null)
            return false;
        return (res.getPermissions().playerHas(player, Flags.build, false));
    }

    @EventHandler
    public void blockBreakEvent(final BlockBreakEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        final boolean hasPerm = this.hasPerm(e.getBlock().getLocation(), e.getPlayer());
        e.setCancelled(!hasPerm);
        if (!hasPerm) {
            e.getPlayer().sendMessage("禁止破坏领地外的方块");
        }
    }

    @EventHandler
    public void BlockPlaceEvent(final BlockPlaceEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        final boolean hasPerm = this.hasPerm(e.getBlock().getLocation(), e.getPlayer());
        e.setCancelled(!hasPerm);
        if (!hasPerm) {
            e.getPlayer().sendMessage("禁止在领地外放置方块");
        }
    }

    @EventHandler
    public void PlayerInteractEvent(final PlayerInteractEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        if ((e.getAction() == Action.PHYSICAL && Objects.requireNonNull(e.getClickedBlock()).getType() == Material.SOUL_SAND) || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final boolean hasPerm = this.hasPerm(Objects.requireNonNull(e.getClickedBlock()).getLocation(), e.getPlayer());
            e.setCancelled(!hasPerm);
            if (!hasPerm) {
                e.getPlayer().sendMessage("禁止交互");
            }
        }
    }

    @EventHandler
    public void EntityInteractEvent(final EntityInteractEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void PlayerBucketFillEvent(final PlayerBucketFillEvent e) {
        if (e.getPlayer().isOp()) {

            return;
        }
        final boolean hasPerm = this.hasPerm(e.getBlockClicked().getLocation(), e.getPlayer());
        e.setCancelled(!hasPerm);
        if (!hasPerm) {
            e.getPlayer().sendMessage("禁止在领地外使用桶");
        }
    }

    @EventHandler
    public void PlayerBucketEmptyEvent(final PlayerBucketEmptyEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        final boolean hasPerm = this.hasPerm(e.getBlockClicked().getLocation(), e.getPlayer());
        e.setCancelled(!hasPerm);
        if (!hasPerm) {
            e.getPlayer().sendMessage("禁止在领地外使用桶");
        }
    }
}