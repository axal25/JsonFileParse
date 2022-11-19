package data.collection.printer;

import data.model.dto.RecordDto;
import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CollectionDataPrinter<T extends Comparable<T>> {

    private final LinkedHashMap<String, List<RecordDto>> topGroupings;
    private final Map<String, T> topGroupingComparableValues;
    private final Integer groupingAmountLimit;
    private final Boolean isOrderReversed;
    private final String groupingKeyDescription;
    private final String decisiveFieldDescription;

    public void printTopGroupings() {
        System.out.printf(
                "Top %1d %2s (%3s) by \"%4s\".\n",
                groupingAmountLimit,
                groupingKeyDescription,
                Boolean.TRUE.equals(isOrderReversed) ? "Descending" : "Ascending",
                decisiveFieldDescription);

        String printFormat = getPrintFormat();
        int index = 1;
        for (Map.Entry<String, List<RecordDto>> groupingKeyToRecordDtos : topGroupings.entrySet()) {
            if (index > groupingAmountLimit) break;
            System.out.printf(
                    printFormat,
                    index,
                    groupingKeyToRecordDtos.getKey(),
                    decisiveFieldDescription,
                    topGroupingComparableValues.get(groupingKeyToRecordDtos.getKey())
            );
            index++;
        }
    }

    private String getPrintFormat() {
        int indexCharWidth = groupingAmountLimit < 10
                ? 1
                : groupingAmountLimit < 100
                ? 2
                : groupingAmountLimit < 1000
                ? 3
                : groupingAmountLimit < 10000
                ? 4
                : 10;
        int groupingKeyCharWidth =
                topGroupings.keySet().stream()
                        .map(String::length)
                        .mapToInt(i -> i)
                        .max().orElse(0);
        int comparableValuesCharWidth =
                topGroupingComparableValues.values().stream()
                        .map(a -> String.valueOf(a).length())
                        .mapToInt(i -> i)
                        .max().orElse(0);
        return new StringBuilder()
                .append("%")
                .append(indexCharWidth)
                .append("d. %")
                .append(groupingKeyCharWidth)
                .append("s -> \"%s\": %")
                .append(comparableValuesCharWidth)
                .append("s.%n")
                .toString();
    }
}
