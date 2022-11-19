package data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import read.JsonProvider;

public interface JsonObject {

    default String toJsonString() {
        try {
            return JsonProvider.getObjectMapper()
                    .writer(JsonProvider.getPrinterWithSpaces())
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    default String toJsonPrettyString() {
        try {
            return JsonProvider.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
