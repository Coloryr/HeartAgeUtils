package Color_yr.HeartAgeUtils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
