package Color_yr.HeartAgeUtils.Hook;

import nl.rutgerkok.blocklocker.BlockLockerAPIv2;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

class blocklocker {

    public boolean can(Player player, Block block, boolean allowBypass) {
        if (BlockLockerAPIv2.isProtected(block))
            return BlockLockerAPIv2.isAllowed(player, block, allowBypass);
        return true;
    }
}
