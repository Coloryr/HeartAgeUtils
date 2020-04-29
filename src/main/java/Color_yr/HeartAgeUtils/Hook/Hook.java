package Color_yr.HeartAgeUtils.Hook;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import org.bukkit.entity.Player;

public class Hook {
    public static vault vault;

    private static boolean vaultHook;

    public static void init() {
        vault = new vault();
        if (!HeartAgeUtils.configMain.Config.getDeathChest().getCost().isEnable()
                || vault.setupEconomy()) {
            vaultHook = true;
            HeartAgeUtils.log.info("[HeartAgeUtils]vault已挂钩");
        }
    }

    public static boolean vaultCheck(Player player, int cost) {
        if (vaultHook) {
            return vault.check(player, cost);
        }
        return true;
    }

    public static void vaultCost(Player player, int cost, String message) {
        if (vaultHook) {
            vault.cost(player, cost, message);
        }
    }
}
