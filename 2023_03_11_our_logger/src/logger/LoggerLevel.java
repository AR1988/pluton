package logger;

public enum LoggerLevel {
    DEBUG("\u001B[36m"), INFO("\u001B[34m"), WARNING("\u001B[33m"), ERROR("\u001B[31m");

    private final String color;

    LoggerLevel(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
