package data.collection.extractor.comparator;

import utils.StringUtils;

import java.util.Comparator;

public class GroupingKeyComparator implements Comparator<String> {

    @Override
    public int compare(String string1, String string2) {
        return StringUtils.compare(string1, string2);
    }
}
