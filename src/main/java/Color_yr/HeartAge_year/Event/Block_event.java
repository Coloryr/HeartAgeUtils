package Color_yr.HeartAge_year.Event;

import Color_yr.HeartAge_year.HeartAge_year;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import net.Indyuce.mmoitems.ability.Snowman_Turret;
import net.Indyuce.mmoitems.api.Ability;
import net.Indyuce.mmoitems.api.event.AbilityUseEvent;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.function.Supplier;

import static Color_yr.HeartAge_year.Config.Config_Read.lan;

public class Block_event implements Listener {

    private boolean hasPerm(final Location loc, final Player player) {
        final ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);
        if (res == null)
            return false;
        return (res.getPermissions().playerHas(player, Flags.build, false));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityDamageByEntityEvent(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            e.getDamager().sendMessage(lan.getNo_PVP());
            e.setCancelled(true);
        } else if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player)
            if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
                ((Player) ((Projectile) e.getDamager()).getShooter()).sendMessage(lan.getNo_PVP());
                e.setCancelled(true);
            } else if (e.getDamager() instanceof Snowball && e.getEntity() instanceof Player) {
                e.setCancelled(true);
            }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void MMODamage(final AbilityUseEvent e) {
        if (e.getTarget() instanceof Player) {
            e.getAbility().getAbility().disable();
            e.getPlayer().sendMessage(lan.getNo_PVP());
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void blockBreakEvent(final BlockBreakEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        final boolean hasPerm = this.hasPerm(e.getBlock().getLocation(), e.getPlayer());
        e.setCancelled(!hasPerm);
        if (!hasPerm) {
            e.getPlayer().sendMessage(lan.getNo_build_destroy());
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
            e.getPlayer().sendMessage(lan.getNo_build_place());
        }
    }

    @EventHandler
    public void PlayerInteractEvent(final PlayerInteractEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        if ((e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOUL_SAND)) {
            final boolean hasPerm = this.hasPerm(e.getClickedBlock().getLocation(), e.getPlayer());
            e.setCancelled(!hasPerm);
            if (!hasPerm) {
                e.getPlayer().sendMessage(lan.getNo_build_ues());
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
            e.getPlayer().sendMessage(lan.getNo_build_bucket());
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
            e.getPlayer().sendMessage(lan.getNo_build_bucket());
        }
    }
}