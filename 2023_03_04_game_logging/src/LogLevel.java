/**
 * @author Andrej Reutow
 * created on 04.03.2023
 */
public enum LogLevel {
    //ANSI_YELLOW
    INFO("\u001B[34m"),
    //ANSI_CYAN
    WARN("\u001B[36m"),
    //ANSI_RED
    ERROR("\u001B[31m");

    private final String color;

    LogLevel(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";
}
