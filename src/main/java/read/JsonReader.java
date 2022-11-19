package read;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class JsonReader {
    private final ObjectMapper objectMapper = JsonProvider.getObjectMapper();

    public <T> JsonNode readTree(String resourcePathToFile) throws FileReadException, IOException {
        return objectMapper.readTree(getInputStream(resourcePathToFile));
    }

    public <T> T readValue(String resourcePathToFile, Class<T> entityType) throws IOException, FileReadException {
        return objectMapper.readValue(getInputStream(resourcePathToFile), entityType);
    }

    public <T> List<T> readValues(String resourcePathToFile, Class<T> entityCollectionElementType) throws FileReadException {
        return readValuesByParser(
                getInputStream(resourcePathToFile),
                entityCollectionElementType);
    }

    public InputStream getInputStream(String resourcePathToFile) throws FileReadException {
        resourcePathToFile = resourcePathToFile.startsWith("/")
                ? resourcePathToFile
                : String.format("/%s", resourcePathToFile);
        InputStream inputStream = getClass().getResourceAsStream(resourcePathToFile);
        if (inputStream == null) throw new FileReadException(
                InputStream.class,
                String.class,
                resourcePathToFile,
                new NullPointerException());
        return inputStream;
    }

    private <T> List<T> readValuesByParser(InputStream inputStream, Class<T> entityCollectionElementType) throws FileReadException {
        try (JsonParser jsonParser = new JsonFactory().createParser(inputStream)) {
            return readValuesByIterator(jsonParser, entityCollectionElementType);
        } catch (IOException e) {
            throw new FileReadException(
                    JsonParser.class,
                    InputStream.class,
                    inputStream,
                    e);
        }
    }

    private <T> List<T> readValuesByIterator(JsonParser jsonParser, Class<T> entityCollectionElementType) throws FileReadException {
        try (MappingIterator<T> typedMappingIterator = objectMapper.readValues(jsonParser, entityCollectionElementType)) {
            return getList(typedMappingIterator);
        } catch (IOException e) {
            throw new FileReadException(
                    MappingIterator.class,
                    JsonParser.class,
                    jsonParser,
                    e);
        }
    }

    private <T> List<T> getList(MappingIterator<T> typedMappingIterator) {
        List<T> list = new LinkedList<>();
        while (typedMappingIterator.hasNext())
            list.add(typedMappingIterator.next());
        return list;
    }
}
