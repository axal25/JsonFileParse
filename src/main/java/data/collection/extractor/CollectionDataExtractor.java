package data.collection.extractor;

import data.collection.collector.CollectionDataCollector;
import data.collection.extractor.comparator.GroupingKeyComparator;
import data.collection.extractor.comparator.RecordDtosComparator;
import data.model.dto.RecordDto;
import interfaces.functional.GroupingKeyGenerator;
import interfaces.functional.RecordDtoListComparableValueExtractor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class CollectionDataExtractor<T extends Comparable<T>> {
    private final CollectionDataCollector collectionDataCollector;
    private final GroupingKeyGenerator groupingKeyGenerator;
    private final RecordDtosComparator<T> recordDtosComparator;
    private final GroupingKeyComparator groupingKeyComparator = new GroupingKeyComparator();

    private CollectionDataExtractor(CollectionDataCollector collectionDataCollector, GroupingKeyGenerator groupingKeyGenerator, RecordDtosComparator<T> recordDtosComparator) {
        this.collectionDataCollector = collectionDataCollector;
        this.groupingKeyGenerator = groupingKeyGenerator;
        this.recordDtosComparator = recordDtosComparator;
    }

    public LinkedHashMap<String, List<RecordDto>> getTopGroupings(
            Integer groupingAmountLimit,
            Boolean isOrderReversed) {
        Map<String, List<RecordDto>> groupingKeyToRecordDtos = collectionDataCollector.getCollection().stream()
                .map(groupingKeyGenerator::get)
                .distinct()
                .collect(toUnmodifiableMap(
                        groupingKey -> groupingKey,
                        groupingKey -> new LinkedList<>()
                ));

        collectionDataCollector.getCollection().forEach(recordDto ->
                groupingKeyToRecordDtos
                        .get(groupingKeyGenerator.get(recordDto))
                        .add(recordDto));

        Comparator<Map.Entry<String, List<RecordDto>>> groupingKeyToRecordDtosComparator =
                (groupingKeyToRecordDtos1, groupingKeyToRecordDtos2) -> {
                    int compareRecordDtos = recordDtosComparator.compare(
                            groupingKeyToRecordDtos1.getValue(),
                            groupingKeyToRecordDtos2.getValue());
                    if (compareRecordDtos == 0) return groupingKeyComparator.compare(
                            groupingKeyToRecordDtos1.getKey(),
                            groupingKeyToRecordDtos2.getKey()
                    );
                    return compareRecordDtos;
                };

        if (Boolean.TRUE.equals(isOrderReversed))
            groupingKeyToRecordDtosComparator = groupingKeyToRecordDtosComparator.reversed();

        Stream<Map.Entry<String, List<RecordDto>>> groupingKeyToRecordDtosStream =
                groupingKeyToRecordDtos.entrySet().stream().sorted(groupingKeyToRecordDtosComparator);

        if (groupingAmountLimit != null)
            groupingKeyToRecordDtosStream = groupingKeyToRecordDtosStream.limit(groupingAmountLimit);

        return groupingKeyToRecordDtosStream.collect(toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (recordDtos1, recordDtos2) -> {
                    throw new GroupingKeyConflictException();
                },
                LinkedHashMap::new));
    }

    public LinkedHashMap<String, T> getTopGroupingComparableValues(Map<String, List<RecordDto>> topGroupings) {
        return topGroupings.entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        groupingKeyToRecordDtos -> recordDtosComparator.getComparableValueExtractor().get(
                                groupingKeyToRecordDtos.getValue()),
                        (recordDtos1, recordDtos2) -> {
                            throw new GroupingKeyConflictException();
                        },
                        LinkedHashMap::new));
    }

    private static class GroupingKeyConflictException extends IllegalStateException {
        GroupingKeyConflictException() {
            super("Not permissible grouping key conflict. Should never happen.");
        }
    }

    @Getter
    public static class Builder<T extends Comparable<T>> {
        private CollectionDataCollector collectionDataCollector;
        private GroupingKeyGenerator groupingKeyGenerator;
        private RecordDtosComparator<T> recordDtosComparator;

        private Builder() {
        }

        public static <T extends Comparable<T>> Builder<T> newBuilder() {
            return new Builder<>();
        }

        public Builder<T> setGroupingKeyGenerator(GroupingKeyGenerator groupingKeyGenerator) {
            this.groupingKeyGenerator = groupingKeyGenerator;
            return this;
        }

        public Builder<T> setCollectionDataCollector(CollectionDataCollector collectionDataCollector) {
            this.collectionDataCollector = collectionDataCollector;
            return this;
        }

        public Builder<T> setRecordDtosComparator(RecordDtoListComparableValueExtractor<T> recordDtosToSumAmount) {
            recordDtosComparator = new RecordDtosComparator<>(recordDtosToSumAmount);
            return this;
        }

        public CollectionDataExtractor<T> build() {
            if (collectionDataCollector == null)
                throw new BuilderIllegalStateException("collectionDataCollector");
            if (groupingKeyGenerator == null)
                throw new BuilderIllegalStateException("groupingKeyGenerator");
            if (recordDtosComparator == null)
                throw new BuilderIllegalStateException("recordDtosComparator");
            return new CollectionDataExtractor<>(
                    collectionDataCollector,
                    groupingKeyGenerator,
                    recordDtosComparator);
        }

        private static class BuilderIllegalStateException extends IllegalStateException {
            private BuilderIllegalStateException(String fieldName) {
                super(String.format(
                        "Field \"%s\" was null. Must be populated before calling method \"build()\".",
                        fieldName));
            }
        }
    }
}
