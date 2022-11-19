package data.collection.extractor.comparator;

import data.model.dto.RecordDto;
import interfaces.functional.RecordDtoListComparableValueExtractor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Getter
public class RecordDtosComparator<T extends Comparable<T>> implements Comparator<List<RecordDto>> {

    private final RecordDtoListComparableValueExtractor<T> comparableValueExtractor;

    @Override
    public int compare(List<RecordDto> recordDtos1,
                       List<RecordDto> recordDtos2) {
        return Comparator.<T>naturalOrder().compare(
                getCollectionSum(recordDtos1),
                getCollectionSum(recordDtos2));
    }

    private T getCollectionSum(List<RecordDto> recordDtos) {
        return comparableValueExtractor.get(recordDtos);
    }
}
