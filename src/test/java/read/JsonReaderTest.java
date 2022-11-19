package read;

import consants.ConstantFilePaths;
import data.model.JsonObject;
import data.model.entity.Record;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static read.JsonReaderTest.JsonStringFieldsUtility.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for JsonReader and Record mapping object")
public class JsonReaderTest {

    static class JsonStringFieldsUtility {

        private static final String PATTERN_STRING = "[A-Za-z-/_,\\s]*";
        private static final String PATTERN_NUMBER = "[0-9]*";
        private static final String PATTERN_WHITESPACE = "[\\s]*";
        private static final String PATTERN_FORMAT_SECTORS_OR_THEMES = new StringBuilder()
                .append("\"")
                .append("%s")
                .append(PATTERN_NUMBER)
                .append("\"")
                .append(PATTERN_WHITESPACE)
                .append(":")
                .append(PATTERN_WHITESPACE)
                .append("\\{")
                .append(PATTERN_WHITESPACE)
                .append("\"Name\"")
                .append(PATTERN_WHITESPACE)
                .append(":")
                .append(PATTERN_WHITESPACE)
                .append("\"")
                .append(PATTERN_STRING)
                .append("\",")
                .append(PATTERN_WHITESPACE)
                .append("\"Percent\"")
                .append(PATTERN_WHITESPACE)
                .append(":")
                .append(PATTERN_WHITESPACE)
                .append(PATTERN_NUMBER)
                .append(PATTERN_WHITESPACE)
                .append("}")
                .toString();

        private static final List<Pattern> PATTERNS_UNKNOWN_FIELDS = List.of(
                Pattern.compile(String.format(PATTERN_FORMAT_SECTORS_OR_THEMES, "sector")),
                Pattern.compile(String.format(PATTERN_FORMAT_SECTORS_OR_THEMES, "theme"))
        );

        static final List<Pattern> PATTERNS_CLEAN_UP_WITH_SPACES = List.of(
                Pattern.compile(", (?=, )"),
                Pattern.compile(" (?= )"),
                Pattern.compile(",(?= })")
        );

        static final List<Pattern> PATTERNS_CLEAN_UP_WITHOUT_SPACES = List.of(
                Pattern.compile(",(?=,)"),
                Pattern.compile(",(?=})")
        );

        static String getJsonStringWithoutUnknownFields(String jsonStringWithAllFields, List<Pattern> patternsCleanUp) {
            AtomicReference<String> jsonStringAR = new AtomicReference<>(jsonStringWithAllFields);
            PATTERNS_UNKNOWN_FIELDS.forEach(pattern ->
                    jsonStringAR.set(pattern.matcher(jsonStringAR.get()).replaceAll("")));
            patternsCleanUp.forEach(pattern -> {
                String prev;
                do {
                    prev = jsonStringAR.get();
                    jsonStringAR.set(pattern.matcher(prev).replaceAll(""));
                } while (!Objects.equals(prev, jsonStringAR.get()));
            });
            return jsonStringAR.get();
        }

        static String getJsonStringWithoutNullFields(String jsonStringWithNullFields) {
            return Pattern.compile(String.format("\"%s\" : null, ", PATTERN_STRING)).matcher(jsonStringWithNullFields).replaceAll("");
        }

        static String getWithConsistentFieldApprovalFy(String jsonStringWithInconsistentFieldApprovalFy) {
            Pattern approvalfyLookBehind = Pattern.compile("(?<=\"approvalfy\" : [0-9]{0,4})\"");
            jsonStringWithInconsistentFieldApprovalFy =
                    approvalfyLookBehind.matcher(jsonStringWithInconsistentFieldApprovalFy).replaceAll("");
            jsonStringWithInconsistentFieldApprovalFy =
                    approvalfyLookBehind.matcher(jsonStringWithInconsistentFieldApprovalFy).replaceAll("");
            return jsonStringWithInconsistentFieldApprovalFy;
        }
    }

    @Test
    void readValue_1Entity_toJsonString_equals_fileContents() throws IOException, FileReadException {
        Record record = (Record) new JsonReader().readValue(ConstantFilePaths.ORIGINAL_WORLD_BANK_JSON, Record.class);
        String fileContentsSingleObjectJsonString = getJsonStringWithoutUnknownFields(
                getFileContentsSingleObject(),
                PATTERNS_CLEAN_UP_WITHOUT_SPACES);
        String recordJsonString = getJsonStringWithoutUnknownFields(
                JsonProvider.getObjectMapper().writeValueAsString(record),
                PATTERNS_CLEAN_UP_WITHOUT_SPACES);
        assertEquals(fileContentsSingleObjectJsonString, recordJsonString);
    }

    private String getFileContentsSingleObject() throws IOException, FileReadException {
        return new JsonReader().readTree(ConstantFilePaths.ORIGINAL_WORLD_BANK_JSON).toString();
    }

    @Test
    void readValues_AllEntities_toJsonString_equals_fileContents() throws IOException, FileReadException {
        List<Record> records = (List<Record>) new JsonReader().readValues(ConstantFilePaths.ORIGINAL_WORLD_BANK_JSON, Record.class);
        String recordsToString = records
                .stream().map(JsonObject::toJsonString)
                .collect(Collectors.joining("\n"));
        recordsToString = String.format("%s\n", recordsToString);
        String recordJsonString = getJsonStringWithoutNullFields(
                getJsonStringWithoutUnknownFields(
                        recordsToString,
                        PATTERNS_CLEAN_UP_WITH_SPACES));

        String fileContentsJsonString = getWithConsistentFieldApprovalFy(
                getJsonStringWithoutUnknownFields(
                        getFileContentsWhole(),
                        PATTERNS_CLEAN_UP_WITH_SPACES));

        assertEquals(fileContentsJsonString, recordJsonString);
    }

    private String getFileContentsWhole() throws IOException, FileReadException {
        StringBuilder fileContents = new StringBuilder();
        try (InputStream inputStream = new JsonReader().getInputStream(ConstantFilePaths.ORIGINAL_WORLD_BANK_JSON);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) fileContents.append(line).append("\n");
        }
        return fileContents.toString();
    }
}
