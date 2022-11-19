package data.collection.extractor.options;

import interfaces.functional.RecordDtoListComparableValueExtractor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class SortByOption<T extends Comparable<T>> {
    public static final SortByOption<Integer> PROJECT_COUNT = new SortByOption<>("Amount of Projects", List::size);
    public static final SortByOption<BigDecimal> TOTAL_PROJECT_COST_AMOUNT = new SortByOption<>(
            "Total Project Cost Amount",
            recordDtos -> recordDtos.stream()
                    .map(recordDto -> new BigDecimal(recordDto.getTotalProjectCostAmount()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
    public static final SortByOption<BigDecimal> LENDING_PROJECT_COST = new SortByOption<>(
            "Lending Project Cost",
            recordDtos -> recordDtos.stream()
                    .map(recordDto -> new BigDecimal(recordDto.getLendingProjectCost()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
    public static final SortByOption<BigDecimal> TOTAL_IDA_AND_IBRD_COMMITMENT_AMOUNT = new SortByOption<>(
            "Total IDA and IBRD Commitment Amount",
            recordDtos -> recordDtos.stream()
                    .map(recordDto -> new BigDecimal(recordDto.getTotalIdaAndIbrdCommitmentAmount()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
    public static final SortByOption<BigDecimal> IDA_COMMITMENT_AMOUNT = new SortByOption<>(
            "IDA Commitment Amount",
            recordDtos -> recordDtos.stream()
                    .map(recordDto -> new BigDecimal(recordDto.getIdaCommitmentAmount()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
    public static final SortByOption<BigDecimal> IBRD_COMMITMENT_AMOUNT = new SortByOption<>(
            "IBRD Commitment Amount",
            recordDtos -> recordDtos.stream()
                    .map(recordDto -> new BigDecimal(recordDto.getIbrdCommitmentAmount()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
    public static final List<SortByOption<?>> SORT_BY_OPTIONS = List.of(
            PROJECT_COUNT,
            TOTAL_PROJECT_COST_AMOUNT,
            LENDING_PROJECT_COST,
            TOTAL_IDA_AND_IBRD_COMMITMENT_AMOUNT,
            IDA_COMMITMENT_AMOUNT,
            IBRD_COMMITMENT_AMOUNT
    );

    private final String decisiveFieldDescription;
    private final RecordDtoListComparableValueExtractor<T> comparableValueExtractor;
}
