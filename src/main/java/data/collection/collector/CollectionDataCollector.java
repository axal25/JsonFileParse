package data.collection.collector;

import data.model.dto.RecordDto;
import data.model.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import read.FileReadException;
import read.JsonReader;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class CollectionDataCollector {

    private final List<RecordDto> collection;

    public CollectionDataCollector(String resourcePathToFile) {
        this(getRecordDtos(resourcePathToFile));
    }

    private static List<RecordDto> getRecordDtos(String resourcePathToFile) {
        return getRecords(resourcePathToFile)
                .stream().map(RecordDto::new)
                .collect(toList());
    }

    private static List<Record> getRecords(String resourcePathToFile) {
        try {
            return new JsonReader().readValues(resourcePathToFile, Record.class);
        } catch (FileReadException e) {
            throw new FailedReadException(e);
        }
    }

    private static class FailedReadException extends IllegalStateException {
        FailedReadException(FileReadException e) {
            super(String.format("Couldn't create collection of %s.", RecordDto.class.getSimpleName()), e);
        }
    }
}
