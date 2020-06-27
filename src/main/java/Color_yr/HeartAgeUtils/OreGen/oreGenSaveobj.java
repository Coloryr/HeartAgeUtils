package Color_yr.HeartAgeUtils.OreGen;

import java.util.HashMap;
import java.util.Map;

public class oreGenSaveobj {
    private final Map<String, Boolean> PlayerSave = new HashMap<>();

    public boolean getPlayerSave(String obj) {
        if (PlayerSave.containsKey(obj))
            return PlayerSave.get(obj);
        return false;
    }

    public void setPlayer(String player, boolean open) {
        PlayerSave.put(player, open);
    }

    public void init(String player) {
        if (!PlayerSave.containsKey(player))
            PlayerSave.put(player, false);
    }
}
