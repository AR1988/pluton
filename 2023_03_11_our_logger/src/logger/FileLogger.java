package logger;

import logger.utils.FileWriterUtil;

/**
 * @author Andrej Reutow
 * created on 11.03.2023
 */
public class FileLogger {
    private final Class clazz;

    public FileLogger(Class clazz) {
        this.clazz = clazz;
    }

    public void info(String message) {
        print(LoggerLevel.INFO, message);
    }

    public void debug(String message) {
        print(LoggerLevel.DEBUG, message);
    }

    public void warn(String message) {
        print(LoggerLevel.WARNING, message);
    }

    public void error(String message) {
        print(LoggerLevel.ERROR, message);
    }

    void print(LoggerLevel loggerLevel, String message) {
        String logMessage = String.format("[%s] %s (%d-%s): %s",
                loggerLevel,
                this.clazz.getName(),
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                message);
        printToFile(logMessage);
    }

    private void printToFile(String logMessage) {
        FileWriterUtil fileWriterUtil = new FileWriterUtil();
        fileWriterUtil.printToFile("logger.log", logMessage);
    }
}
