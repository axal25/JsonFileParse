package data.collection.extractor;

import data.collection.extractor.options.SortByOption;
import data.model.dto.RecordDto;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static answer.QuestionAnswerer.COUNTRY;
import static java.util.stream.Collectors.toList;

@Nested
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("CollectionDataExtractor for 15 Countries Ascending Test")
public class CollectionDataExtractorTop15CountriesAscendingTest {

    private static final int RECORD_DTO_AMOUNT = 50;

    private static final List<RecordDto> INPUT_RECORD_DTOS = InputRecordDtosGenerator.generateInputRecordDtos(RECORD_DTO_AMOUNT);
    private static final List<Integer> expectedComparableValuesInteger = IntStream.range(0, RECORD_DTO_AMOUNT).mapToObj(i -> i + 1).collect(toList());
    private static final List<BigDecimal> expectedComparableValuesBigDecimal = expectedComparableValuesInteger.stream()
            .map(i -> new BigDecimal(i * i))
            .collect(toList());
    private static final List<String> expectedGroupingKeys = expectedComparableValuesInteger.stream()
            .map(i -> String.format(
                    "[ \"COUNTRY_NAME_%d\", \"COUNTRY_SHORT_NAME_%d\", \"COUNTRY_NAME_CODE_%d\", \"COUNTRY_CODE_%d\" ]",
                    i, i, i, i))
            .collect(toList());

    private static <T extends Comparable<T>> CollectionDataExtractorAsserter<T> getCollectionDataExtractorE2ETester(SortByOption<T> sortByOption) {
        return new CollectionDataExtractorAsserter<>(
                INPUT_RECORD_DTOS,
                sortByOption,
                COUNTRY,
                RECORD_DTO_AMOUNT,
                false,
                true);
    }

    @Test
    void topGroupings_15_descending_country_projectCount() {
        getCollectionDataExtractorE2ETester(SortByOption.PROJECT_COUNT)
                .assertTopGroupingsAndComparableValues(
                        RECORD_DTO_AMOUNT,
                        expectedGroupingKeys,
                        expectedComparableValuesInteger,
                        null);
    }

    @Test
    void topGroupings_15_descending_country_totalProjectCostAmount() {
        getCollectionDataExtractorE2ETester(SortByOption.TOTAL_PROJECT_COST_AMOUNT)
                .assertTopGroupingsAndComparableValues(
                        RECORD_DTO_AMOUNT,
                        expectedGroupingKeys,
                        expectedComparableValuesBigDecimal,
                        null);
    }

    @Test
    void topGroupings_15_descending_country_lendingProjectCost() {
        getCollectionDataExtractorE2ETester(SortByOption.LENDING_PROJECT_COST)
                .assertTopGroupingsAndComparableValues(
                        RECORD_DTO_AMOUNT,
                        expectedGroupingKeys,
                        expectedComparableValuesBigDecimal,
                        null);
    }

    @Test
    void topGroupings_15_descending_country_totalIdaAndIbrdCommitmentAmount() {
        getCollectionDataExtractorE2ETester(SortByOption.TOTAL_IDA_AND_IBRD_COMMITMENT_AMOUNT)
                .assertTopGroupingsAndComparableValues(
                        RECORD_DTO_AMOUNT,
                        expectedGroupingKeys,
                        expectedComparableValuesBigDecimal,
                        null);
    }

    @Test
    void topGroupings_15_descending_country_idaCommitmentAmount() {
        getCollectionDataExtractorE2ETester(SortByOption.IDA_COMMITMENT_AMOUNT)
                .assertTopGroupingsAndComparableValues(
                        RECORD_DTO_AMOUNT,
                        expectedGroupingKeys,
                        expectedComparableValuesBigDecimal,
                        null);
    }

    @Test
    void topGroupings_15_descending_country_ibrdCommitmentAmount() {
        getCollectionDataExtractorE2ETester(SortByOption.IBRD_COMMITMENT_AMOUNT)
                .assertTopGroupingsAndComparableValues(
                        RECORD_DTO_AMOUNT,
                        expectedGroupingKeys,
                        expectedComparableValuesBigDecimal,
                        null);
    }
}
