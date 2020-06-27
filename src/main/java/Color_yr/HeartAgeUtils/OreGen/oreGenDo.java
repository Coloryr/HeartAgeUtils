package Color_yr.HeartAgeUtils.OreGen;

import Color_yr.HeartAgeUtils.Config.configMain;
import org.bukkit.Material;

import java.util.*;

public class oreGenDo {
    public static List<Material> itemStacks;
    public static oreGenSaveobj saveobj = new oreGenSaveobj();
    public static Set<Material> Blocks = new HashSet<>();
    public static Map<Material, String> lan = new HashMap<>();

    public static boolean isEnable(String player) {
        return saveobj.getPlayerSave(player);
    }

    public static Material getOre() {
        Random random = new Random();
        return itemStacks.get(random.nextInt(itemStacks.size() - 1));
    }

    public static void init(String player) {
        saveobj.init(player);
    }

    public static void disable(String player) {
        saveobj.setPlayer(player, false);
        configMain.oreGenRead.save(null, null);
    }
}