package Color_yr.HeartAge_year.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsNumber {
    public boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
