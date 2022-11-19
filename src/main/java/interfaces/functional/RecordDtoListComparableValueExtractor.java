package interfaces.functional;

import data.model.dto.RecordDto;

import java.util.List;

public interface RecordDtoListComparableValueExtractor<T extends Comparable<T>> extends FI1ArgGetterReturnsNonVoid<List<RecordDto>, T> {
}
