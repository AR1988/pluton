package logger;

/**
 * @author Andrej Reutow
 * created on 11.03.2023
 */
public class ConsoleLogger {

    private final Class clazz;

    public ConsoleLogger(Class clazz) {
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

    private void print(LoggerLevel loggerLevel, String message) {
        FileLogger fileLogger = new FileLogger(clazz);
        fileLogger.print(loggerLevel, message);

        String logMessage = String.format("%s[%s] %s (%d-%s): %s \u001B[0m",
                loggerLevel.getColor(),
                loggerLevel,
                this.clazz.getName(),
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                message);
        System.out.println(logMessage);
    }
}
