package logger.utils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Andrej Reutow
 * created on 11.03.2023
 */
public class FileWriterUtil {

    private FileWriter fileWriter;

    public void printToFile(String path, String message) {
        try {
            this.fileWriter = new FileWriter(path, true);
            this.fileWriter.write(message + "\n");
            this.fileWriter.flush();
            this.fileWriter.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
