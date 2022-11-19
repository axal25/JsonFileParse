package data.collection.extractor;

import data.collection.collector.CollectionDataCollector;
import data.collection.extractor.options.SortByOption;
import data.model.dto.RecordDto;
import interfaces.functional.GroupingKeyGenerator;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
class CollectionDataExtractorAsserter<T extends Comparable<T>> {

    private static final Boolean IS_DEBUG_PRINTING_ON = null;

    private final CollectionDataExtractor<T> collectionDataExtractor;
    private final Integer groupingAmountLimit;
    private final Boolean isOrderReversed;
    private final Boolean isComparingListOfRecordDtosSkipped;

    CollectionDataExtractorAsserter(
            String resourcePathToFile,
            SortByOption<T> sortByOption,
            GroupingKeyGenerator groupingKeyGenerator,
            Integer groupingAmountLimit,
            Boolean isOrderReversed,
            Boolean isComparingListOfRecordDtosSkipped) {
        this(
                new CollectionDataCollector(resourcePathToFile),
                sortByOption,
                groupingKeyGenerator,
                groupingAmountLimit,
                isOrderReversed,
                isComparingListOfRecordDtosSkipped);
    }

    CollectionDataExtractorAsserter(
            List<RecordDto> inputRecordDtos,
            SortByOption<T> sortByOption,
            GroupingKeyGenerator groupingKeyGenerator,
            Integer groupingAmountLimit,
            Boolean isOrderReversed,
            Boolean isComparingListOfRecordDtosSkipped) {
        this(
                new CollectionDataCollector(inputRecordDtos),
                sortByOption,
                groupingKeyGenerator,
                groupingAmountLimit,
                isOrderReversed,
                isComparingListOfRecordDtosSkipped);
    }

    private CollectionDataExtractorAsserter(
            CollectionDataCollector collectionDataCollector,
            SortByOption<T> sortByOption,
            GroupingKeyGenerator groupingKeyGenerator,
            Integer groupingAmountLimit,
            Boolean isOrderReversed,
            Boolean isComparingListOfRecordDtosSkipped) {
        collectionDataExtractor = CollectionDataExtractor.Builder.<T>newBuilder()
                .setRecordDtosComparator(sortByOption.getComparableValueExtractor())
                .setCollectionDataCollector(collectionDataCollector)
                .setGroupingKeyGenerator(groupingKeyGenerator)
                .build();
        this.groupingAmountLimit = groupingAmountLimit;
        this.isOrderReversed = isOrderReversed;
        this.isComparingListOfRecordDtosSkipped = isComparingListOfRecordDtosSkipped;
    }

    void assertTopGroupingsAndComparableValues(
            int expectedTopGroupingsSize,
            List<String> expectedGroupingKeys,
            List<T> expectedComparableValues,
            List<List<RecordDto>> expectedListOfRecordDtos) {
        LinkedHashMap<String, List<RecordDto>> actualTopGroupings =
                collectionDataExtractor.getTopGroupings(groupingAmountLimit, isOrderReversed);

        LinkedHashMap<String, T> actualTopGroupingComparableValues =
                collectionDataExtractor.getTopGroupingComparableValues(actualTopGroupings);

        if (Boolean.TRUE.equals(IS_DEBUG_PRINTING_ON)) debug(actualTopGroupings, actualTopGroupingComparableValues);

        assertEquals(actualTopGroupings.size(), actualTopGroupingComparableValues.size());
        assertEquals(expectedTopGroupingsSize, actualTopGroupings.size());
        assertEquals(expectedTopGroupingsSize, actualTopGroupingComparableValues.size());

        IntStream.range(0, actualTopGroupingComparableValues.size()).forEach(index -> {
            assertEquals(
                    expectedGroupingKeys.get(index),
                    new ArrayList<>(actualTopGroupingComparableValues.entrySet()).get(index).getKey(),
                    String.format("at index: %d", index));
            assertEquals(
                    expectedComparableValues.get(index),
                    new ArrayList<>(actualTopGroupingComparableValues.entrySet()).get(index).getValue(),
                    String.format("at index: %d", index));
        });

        IntStream.range(0, actualTopGroupings.size()).forEach(index -> {
            assertEquals(
                    expectedGroupingKeys.get(index),
                    new ArrayList<>(actualTopGroupings.entrySet()).get(index).getKey(),
                    String.format("at index: %d", index));
            if (!isComparingListOfRecordDtosSkipped)
                assertEquals(
                        expectedListOfRecordDtos.get(index),
                        new ArrayList<>(actualTopGroupings.entrySet()).get(index).getValue(),
                        String.format("at index: %d", index));
        });
    }

    private void debug(
            LinkedHashMap<String, List<RecordDto>> actualTopGroupings,
            LinkedHashMap<String, T> actualTopGroupingComparableValues) {
        System.out.println("actualTopGroupings size: " + actualTopGroupings.size());

        System.out.println("actualTopGroupings Grouping Keys: ");
        for (String groupingKey : actualTopGroupings.keySet()) System.out.println(groupingKey);

        if (!isComparingListOfRecordDtosSkipped) {
            System.out.println("actualTopGroupings Values: " + actualTopGroupings.size());
            for (List<RecordDto> recordDtos : actualTopGroupings.values()) System.out.println(recordDtos);
        }

        System.out.println("actualTopGroupingComparableValues size: " + actualTopGroupingComparableValues.size());

        System.out.println("actualTopGroupingComparableValues Grouping Keys: \n");
        for (String groupingKey : actualTopGroupingComparableValues.keySet()) System.out.println(groupingKey);

        System.out.println("actualTopGroupingComparableValues Values: \n");
        for (T value : actualTopGroupingComparableValues.values()) System.out.println(value);
    }
}
