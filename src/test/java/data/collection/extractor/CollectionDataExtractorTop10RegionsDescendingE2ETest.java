package data.collection.extractor;

import consants.ConstantFilePaths;
import data.collection.extractor.options.SortByOption;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static answer.QuestionAnswerer.REGION;


@Nested
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("CollectionDataExtractor for 10 Regions Descending End-to-End Test")
public class CollectionDataExtractorTop10RegionsDescendingE2ETest {

    private static <T extends Comparable<T>> CollectionDataExtractorAsserter<T> getCollectionDataExtractorE2ETester(
            SortByOption<T> sortByOption) {
        return new CollectionDataExtractorAsserter<>(
                ConstantFilePaths.ORIGINAL_WORLD_BANK_JSON,
                sortByOption,
                REGION,
                10,
                true,
                true);
    }

    @Test
    void topGroupings_10_descending_region_projectCount() {
        List<String> expectedGroupingKeys = List.of(
                "[ \"Africa\" ]",
                "[ \"East Asia and Pacific\" ]",
                "[ \"Europe and Central Asia\" ]",
                "[ \"South Asia\" ]",
                "[ \"Middle East and North Africa\" ]",
                "[ \"Latin America and Caribbean\" ]",
                "[ \"Other\" ]"
        );
        List<Integer> expectedComparableValues = List.of(
                152,
                100,
                74,
                65,
                54,
                53,
                2
        );

        getCollectionDataExtractorE2ETester(SortByOption.PROJECT_COUNT)
                .assertTopGroupingsAndComparableValues(
                        7,
                        expectedGroupingKeys,
                        expectedComparableValues,
                        null);
    }

    @Test
    void topGroupings_10_descending_region_totalProjectCostAmount() {
        List<String> expectedGroupingKeys = List.of(
                "[ \"Africa\" ]",
                "[ \"East Asia and Pacific\" ]",
                "[ \"South Asia\" ]",
                "[ \"Europe and Central Asia\" ]",
                "[ \"Latin America and Caribbean\" ]",
                "[ \"Middle East and North Africa\" ]",
                "[ \"Other\" ]");
        List<BigDecimal> expectedComparableValues = List.of(
                new BigDecimal("8778410000"),
                new BigDecimal("6652710000"),
                new BigDecimal("5793960000"),
                new BigDecimal("5402150000"),
                new BigDecimal("5079500000"),
                new BigDecimal("2434000000"),
                new BigDecimal("0")
        );

        getCollectionDataExtractorE2ETester(SortByOption.TOTAL_PROJECT_COST_AMOUNT)
                .assertTopGroupingsAndComparableValues(
                        7,
                        expectedGroupingKeys,
                        expectedComparableValues,
                        null);
    }

    @Test
    void topGroupings_10_descending_region_lendingProjectCost() {
        List<String> expectedGroupingKeys = List.of(
                "[ \"South Asia\" ]",
                "[ \"Africa\" ]",
                "[ \"East Asia and Pacific\" ]",
                "[ \"Latin America and Caribbean\" ]",
                "[ \"Europe and Central Asia\" ]",
                "[ \"Middle East and North Africa\" ]",
                "[ \"Other\" ]");
        List<BigDecimal> expectedComparableValues = List.of(
                new BigDecimal("20660330000"),
                new BigDecimal("17090020000"),
                new BigDecimal("16713170000"),
                new BigDecimal("9625370000"),
                new BigDecimal("8490770000"),
                new BigDecimal("4760370000"),
                new BigDecimal("22010000")
        );

        getCollectionDataExtractorE2ETester(SortByOption.LENDING_PROJECT_COST)
                .assertTopGroupingsAndComparableValues(
                        7,
                        expectedGroupingKeys,
                        expectedComparableValues,
                        null);
    }

    @Test
    void topGroupings_10_descending_region_totalIdaAndIbrdCommitmentAmount() {
        List<String> expectedGroupingKeys = List.of(
                "[ \"Africa\" ]",
                "[ \"East Asia and Pacific\" ]",
                "[ \"South Asia\" ]",
                "[ \"Europe and Central Asia\" ]",
                "[ \"Latin America and Caribbean\" ]",
                "[ \"Middle East and North Africa\" ]",
                "[ \"Other\" ]");
        List<BigDecimal> expectedComparableValues = List.of(
                new BigDecimal("9392040000"),
                new BigDecimal("7352750000"),
                new BigDecimal("6189970000"),
                new BigDecimal("5527620000"),
                new BigDecimal("5167620000"),
                new BigDecimal("2720810000"),
                new BigDecimal("6120000")
        );

        getCollectionDataExtractorE2ETester(SortByOption.TOTAL_IDA_AND_IBRD_COMMITMENT_AMOUNT)
                .assertTopGroupingsAndComparableValues(
                        7,
                        expectedGroupingKeys,
                        expectedComparableValues,
                        null);
    }

    @Test
    void topGroupings_10_descending_region_projectCount_idaCommitmentAmount() {
        List<String> expectedGroupingKeys = List.of(
                "[ \"Africa\" ]",
                "[ \"South Asia\" ]",
                "[ \"East Asia and Pacific\" ]",
                "[ \"Europe and Central Asia\" ]",
                "[ \"Latin America and Caribbean\" ]",
                "[ \"Middle East and North Africa\" ]",
                "[ \"Other\" ]");
        List<BigDecimal> expectedComparableValues = List.of(
                new BigDecimal("8729410000"),
                new BigDecimal("4916260000"),
                new BigDecimal("2652210000"),
                new BigDecimal("696500000"),
                new BigDecimal("447300000"),
                new BigDecimal("269000000"),
                new BigDecimal("0")
        );

        getCollectionDataExtractorE2ETester(SortByOption.IDA_COMMITMENT_AMOUNT)
                .assertTopGroupingsAndComparableValues(
                        7,
                        expectedGroupingKeys,
                        expectedComparableValues,
                        null);
    }

    @Test
    void topGroupings_10_descending_region_ibrdCommitmentAmount() {
        List<String> expectedGroupingKeys = List.of(
                "[ \"Europe and Central Asia\" ]",
                "[ \"Latin America and Caribbean\" ]",
                "[ \"East Asia and Pacific\" ]",
                "[ \"Middle East and North Africa\" ]",
                "[ \"South Asia\" ]",
                "[ \"Africa\" ]",
                "[ \"Other\" ]");
        List<BigDecimal> expectedComparableValues = List.of(
                new BigDecimal("4705650000"),
                new BigDecimal("4632200000"),
                new BigDecimal("4000500000"),
                new BigDecimal("2165000000"),
                new BigDecimal("877700000"),
                new BigDecimal("49000000"),
                new BigDecimal("0")
        );

        getCollectionDataExtractorE2ETester(SortByOption.IBRD_COMMITMENT_AMOUNT)
                .assertTopGroupingsAndComparableValues(
                        7,
                        expectedGroupingKeys,
                        expectedComparableValues,
                        null);
    }
}
