package utils;

import java.util.Objects;

public class StringUtils {

    public static int compare(String string1, String string2) {
        if (Objects.equals(string1, string2)) return 0;
        if (string1 == null) return -1;
        return string1.compareTo(string2);
    }
}
