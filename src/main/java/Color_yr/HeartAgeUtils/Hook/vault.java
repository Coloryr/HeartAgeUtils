package Color_yr.HeartAgeUtils.Hook;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

class vault {
    private Economy econ = null;

    public vault() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    public boolean setupEconomy() {
        return econ != null;
    }

    public boolean check(Player player, int cost) {
        return econ.has(player, cost);
    }

    public void cost(Player player, int cost, String message) {
        EconomyResponse r = econ.withdrawPlayer(player, cost);
        if (r.transactionSuccess()) {
            player.sendMessage(message);
        }
    }
}
