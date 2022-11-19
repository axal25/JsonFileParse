package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DebugUtils {

    static void compareStrings(String s1, String s2) {
        assertNotNull(s1);
        assertNotNull(s2);
        assertEquals(s1.length(), s2.length());
        if (!s1.equals(s2)) assertStepByStep(s1, s2);
        assertEquals(s1, s2);
    }

    private static void assertStepByStep(String s1, String s2) {
        char[] c1s = s1.toCharArray();
        char[] c2s = s2.toCharArray();
        assertEquals(c1s.length, c2s.length);
        for (int i = 0; i < Math.min(c1s.length, c2s.length); i++)
            assertEquals(c1s[i], c2s[i],
                    String.format(
                            "Chars at position %d are not equal." +
                                    "\n\tChar from string 1: '%c' (%d) \"%s\"." +
                                    "\n\tChar from string2: '%c' (%d) \"%s\"." +
                                    "\n\tArea from string 1: %s" +
                                    "\n\tArea from string 2: %s",
                            i,
                            c1s[i], (int) c1s[i], c1s[i],
                            c2s[i], (int) c2s[i], c2s[i],
                            getAreaFromAround(i, c1s),
                            getAreaFromAround(i, c2s)
                    )
            );
        assertEquals(c1s, c2s);
    }

    private static String getAreaFromAround(int index, char[] array) {
        StringBuilder sb = new StringBuilder().append("[");

        int indexOffset = 4;
        int minIndex = index - indexOffset;
        while (minIndex < 0) minIndex++;
        int maxIndex = index + indexOffset;
        while (maxIndex > array.length) maxIndex--;

        for (int i = minIndex; i < maxIndex; i++) {
            if (!sb.toString().equals("[")) sb.append(", ");
            sb.append(String.format("'%c' (%d) \"%s\"", array[i], (int) array[i], array[i]));
        }

        return sb.append("]").toString();
    }
}
