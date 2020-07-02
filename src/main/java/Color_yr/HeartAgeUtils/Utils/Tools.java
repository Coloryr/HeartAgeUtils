package Color_yr.HeartAgeUtils.Utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
    public static boolean CheckIsFull(Player player) {
        PlayerInventory inv = player.getInventory();
        return inv.firstEmpty() == -1;
    }
}
