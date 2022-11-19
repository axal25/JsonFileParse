package data.collection.extractor;

import data.model.dto.RecordDto;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class InputRecordDtosGenerator {

    static List<RecordDto> generateInputRecordDtos(int recordDtoAmount) {
        return IntStream.range(0, recordDtoAmount)
                .mapToObj(i -> generateMultipleRecordDtos(i + 1))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private static List<RecordDto> generateMultipleRecordDtos(int recordDtoNumber) {
        return IntStream.range(0, recordDtoNumber)
                .mapToObj(i -> generateSingleRecord(recordDtoNumber))
                .collect(toList());
    }

    private static RecordDto generateSingleRecord(int recordDtoNumber) {
        RecordDto recordDto = new RecordDto();
        recordDto.setCountryName(String.format("COUNTRY_NAME_%d", recordDtoNumber));
        recordDto.setCountryShortName(String.format("COUNTRY_SHORT_NAME_%d", recordDtoNumber));
        recordDto.setCountryNameCode(String.format("COUNTRY_NAME_CODE_%d", recordDtoNumber));
        recordDto.setCountryCode(String.format("COUNTRY_CODE_%d", recordDtoNumber));

        recordDto.setRegionName(String.format("REGION_NAME_%d", recordDtoNumber));

        recordDto.setTotalProjectCostAmount(String.valueOf(recordDtoNumber));
        recordDto.setLendingProjectCost(String.valueOf(recordDtoNumber));
        recordDto.setTotalIdaAndIbrdCommitmentAmount(String.valueOf(recordDtoNumber));
        recordDto.setIdaCommitmentAmount(String.valueOf(recordDtoNumber));
        recordDto.setIbrdCommitmentAmount(String.valueOf(recordDtoNumber));
        return recordDto;
    }
}
