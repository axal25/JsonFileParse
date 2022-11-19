package answer;

import data.collection.collector.CollectionDataCollector;
import data.collection.extractor.CollectionDataExtractor;
import data.collection.extractor.options.SortByOption;
import data.collection.printer.CollectionDataPrinter;
import data.model.dto.RecordDto;
import interfaces.functional.GroupingKeyGenerator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static data.collection.extractor.options.SortByOption.SORT_BY_OPTIONS;

public class QuestionAnswerer {

    public static final GroupingKeyGenerator REGION = recordDto -> String.format(
            "[ \"%s\" ]",
            recordDto.getRegionName());
    public static final GroupingKeyGenerator COUNTRY = recordDto -> String.format(
            "[ \"%s\", \"%s\", \"%s\", \"%s\" ]",
            recordDto.getCountryName(),
            recordDto.getCountryShortName(),
            recordDto.getCountryNameCode(),
            recordDto.getCountryCode());

    public static void answerAllQuestions(String resourcePathToFile, Integer groupingAmountLimit) {
        answerAllQuestions(
                resourcePathToFile,
                groupingAmountLimit,
                "Regions",
                REGION,
                true
        );
        answerAllQuestions(
                resourcePathToFile,
                groupingAmountLimit,
                "Countries",
                COUNTRY,
                false
        );
    }

    private static void answerAllQuestions(
            String resourcePathToFile,
            Integer groupingAmountLimit,
            String groupingKeyDescription,
            GroupingKeyGenerator groupingKeyGenerator,
            Boolean isOrderReversed) {
        CollectionDataCollector collectionDataCollector = new CollectionDataCollector(resourcePathToFile);
        SORT_BY_OPTIONS.forEach(
                sortByOption -> {
                    System.out.println();
                    answerSingleQuestion(
                            collectionDataCollector,
                            groupingAmountLimit,
                            groupingKeyDescription,
                            groupingKeyGenerator,
                            isOrderReversed,
                            sortByOption
                    );
                });
    }

    private static <T extends Comparable<T>> void answerSingleQuestion(
            CollectionDataCollector collectionDataCollector,
            Integer groupingAmountLimit,
            String groupingKeyDescription,
            GroupingKeyGenerator groupingKeyGenerator,
            Boolean isOrderReversed,
            SortByOption<T> sortByOption) {
        CollectionDataExtractor<T> collectionDataExtractor =
                CollectionDataExtractor.Builder.<T>newBuilder()
                        .setRecordDtosComparator(sortByOption.getComparableValueExtractor())
                        .setCollectionDataCollector(collectionDataCollector)
                        .setGroupingKeyGenerator(groupingKeyGenerator)
                        .build();

        LinkedHashMap<String, List<RecordDto>> topGroupings =
                collectionDataExtractor.getTopGroupings(groupingAmountLimit, isOrderReversed);
        Map<String, T> topGroupingComparableValues = collectionDataExtractor.getTopGroupingComparableValues(topGroupings);

        CollectionDataPrinter<T> collectionDataPrinter =
                new CollectionDataPrinter<>(
                        topGroupings,
                        topGroupingComparableValues,
                        groupingAmountLimit,
                        isOrderReversed,
                        groupingKeyDescription,
                        sortByOption.getDecisiveFieldDescription());

        collectionDataPrinter.printTopGroupings();
    }
}
