package read;

import java.io.File;
import java.io.FileInputStream;

public class FileReadException extends Exception {

    public FileReadException(Class<?> failedToBeCreatedClass,
                             Class<?> failureCausingParameterClass,
                             Object failureCausingParameterValue,
                             Exception cause) {
        super(
                String.format(
                        "Failed to create %s from %s: \"%s\". ",
                        failedToBeCreatedClass.getSimpleName(),
                        failureCausingParameterClass.getSimpleName(),
                        failureCausingParameterValue),
                cause);
    }
}
