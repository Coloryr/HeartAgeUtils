package Color_yr.HeartAgeUtils.DeathChest;

import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.configObj;
import Color_yr.HeartAgeUtils.Obj.languageObj;

import java.util.*;

public class deathChestDo {
    public static boolean enableLocal = false;
    public static final List<String> enable = new ArrayList<>();
    public static final Map<String, playSetObj> deathChestSet = new HashMap<>();
    public static String help = "";

    public static void GenHelp() {
        help = "";
        enable.clear();
        configObj config = HeartAgeUtils.ConfigMain.Config;
        languageObj lan = HeartAgeUtils.ConfigMain.lan;

        for (int a = 0; a < 8; a++) {
            if (!config.getDeathChest().getDisable().contains(a)) {
                enable.add("" + a);
                help += lan.getHelpModeDeathChest().get(a) + "\n";
            }
        }

        if (config.getDeathChest().getCost().isEnable()) {
            if (!config.getDeathChest().getDisable().contains(1)
                    || !config.getDeathChest().getDisable().contains(4)
                    || !config.getDeathChest().getDisable().contains(5)
                    || !config.getDeathChest().getDisable().contains(7)) {
                help += lan.getHelpCostDeathChest().get(0).replace("%Money%",
                        "" + config.getDeathChest().getCost().getSaveInLocal()) + "\n";
            }
            if (!config.getDeathChest().getDisable().contains(2)
                    || !config.getDeathChest().getDisable().contains(4)
                    || !config.getDeathChest().getDisable().contains(5)
                    || !config.getDeathChest().getDisable().contains(7)) {
                help += lan.getHelpCostDeathChest().get(1).replace("%Money%",
                        "" + config.getDeathChest().getCost().getSaveInLocal()) + "\n";
                enableLocal = true;
            } else {
                enableLocal = false;
            }
            if (!config.getDeathChest().getDisable().contains(3)
                    || !config.getDeathChest().getDisable().contains(4)
                    || !config.getDeathChest().getDisable().contains(5)
                    || !config.getDeathChest().getDisable().contains(6)) {
                help += lan.getHelpCostDeathChest().get(2).replace("%Money%",
                        "" + config.getDeathChest().getCost().getSaveInLocal()) + "\n";
            }
        }
    }

    public static playSetObj getPlayerSet(UUID player, boolean saveData) {
        String uuid = player.toString().replace("-", "0");
        playSetObj save = deathChestSet.get(uuid);
        if (save == null) {
            save = new playSetObj();
            deathChestSet.put(uuid, save);
        }
        if (saveData) {
            configMain.deathChest.save(save, uuid);
        }
        return save;
    }

    public static void setPlayerSet(UUID player, playSetObj set) {
        String uuid = player.toString().replace("-", "0");
        deathChestSet.put(uuid, set);
        configMain.deathChest.save(set, uuid);
    }
}
