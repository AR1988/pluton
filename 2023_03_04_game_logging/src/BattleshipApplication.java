import java.util.*;

/**
 * @author Andrej Reutow
 * created on 25.02.2023
 */
public class BattleshipApplication {
    private static final int FIELD_SIZE = 5;
    private static final int SHIPS = 6;
    public static final String FIELD_IS_NOT_EMPTY_MSG = "Не возможно установить кораблик по координатам %d:%d, Причина: поле %s (%d:%d) уже занято";

    private static final String INFO_LVL = "INFO";
    private static final String ERROR_LVL = "ERROR";
    private static final String WARN_LVL = "WARN";
    private static final Map<LogLevel, List<String>> logger = new HashMap();

    private static Map<String, List<String>> logger2 = new HashMap();

    public static void main(String[] args) {
        fillLogger();

        int[][] battleMap = new int[FIELD_SIZE][FIELD_SIZE];
        fillMap(battleMap);
        for (int[] ints : battleMap) {
            String row = "";
            for (int anInt : ints) {
                row += "" + anInt;
            }
            logger.get(LogLevel.INFO).add(row);
        }
        printLog();
    }

    private static void printLog() {
        for (LogLevel value : LogLevel.values()) {
            if (value.equals(LogLevel.WARN)) {
                continue;
            }
            //Key - enum
            List<String> messages = logger.get(value);
            for (String msg : messages) {
                System.out.printf("%s[%s]\t%s\n", value.getColor(), value, msg);
            }
        }
        System.out.println();
    }

    private static void fillLogger() {
        for (LogLevel value : LogLevel.values()) {
            //Key - enum
            logger.put(value, new ArrayList<>());
        }
    }

    /**
     * Заполнение кораблей на карте
     *
     * @param map карта боя
     */
    public static void fillMap(int[][] map) {
        for (int i = 0; i < SHIPS; i++) {
            logger.get(LogLevel.INFO).add("Устанавливаю кораблик номер " + (i + 1));
//            System.out.printf("%s[%s]\t%s\n", LogLevel.INFO.getColor(), LogLevel.INFO, "Устанавливаю кораблик номер " + (i + 1));
//            System.out.printf("%s[%s]\t%s\n", LogLevel.WARN.getColor(), LogLevel.WARN, "Устанавливаю кораблик номер " + (i + 1));
//            System.out.printf("%s[%s]\t%s\n", LogLevel.ERROR.getColor(), LogLevel.ERROR, "Устанавливаю кораблик номер " + (i + 1));
            setShip(map);
        }
    }

    /**
     * Расставляет корабли на карте боя.
     */
    private static void setShip(int[][] map) {
        boolean isValid;
        do {
            int xAxis = getAxis();  // Генерируем случайное число для xAxis оси
            int yAxis = getAxis();  // Генерируем случайное число для yAxis оси
            logger.get(LogLevel.INFO).add("Установка кораблика по координатам: " + xAxis + ":" + yAxis);
            isValid = validate(map, xAxis, yAxis);
            if (isValid) {
                logger.get(LogLevel.INFO).add(("Кораблик установлен по координатам: " + xAxis + ":" + yAxis));
                map[xAxis][yAxis] = 1;
            } else {
                logger.get(LogLevel.WARN).add(("!!! Кораблик не установлен по координатам: " + xAxis + ":" + yAxis));
            }
        } while (!isValid);
    }

    /**
     * Проверяет, можно ли расположить корабль на указанных координатах.
     * Корабли не должны пересекаться или касаться друг друга.
     */
    private static boolean validate(int[][] map, int xAxis, int yAxis) {
        if (map[xAxis][yAxis] == 0) {                           //проверка, является ячейка по координатам xAxis:yAxis пустая
            boolean leftOk = isLeftEmpty(map, xAxis, yAxis);    //слева     (270)
            boolean upOk = isUpEmpty(map, xAxis, yAxis);        //сверху    (0)
            boolean rightOk = isRightEmpty(map, xAxis, yAxis);  //справа    (903)
            boolean downOk = isDownEmpty(map, xAxis, yAxis);    //снизу     (180)
            boolean checkBottomLeft = checkBottomLeft(map, xAxis, yAxis);
            boolean checkBottomRight = checkBottomRight(map, xAxis, yAxis);
            boolean checkDownRight = checkDownRight(map, xAxis, yAxis);
            boolean checkDownLeft = checkDownLeft(map, xAxis, yAxis);
            return leftOk &&                                    //результат проверок
                    rightOk &&
                    downOk &&
                    upOk &&
                    checkBottomLeft &&
                    checkBottomRight &&
                    checkDownLeft &&
                    checkDownRight;
        } else {                                                //если ячейка не пустая, то туда нельзя установить кораблик
            logger.get(LogLevel.WARN).add(("Ячейка по координатам x" + xAxis + ":y" + yAxis + " уже занята"));
            return false;                                       //результат проверки
        }
    }


    private static boolean checkBottomLeft(int[][] map, int xAxis, int yAxis) {
        if (xAxis == 0) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        if (yAxis == 0) {
            return true;
        }

        boolean result = map[xAxis - 1][yAxis - 1] == 0;
        if (!result) {
            //            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху-слева", (xAxis - 1), (yAxis - 1));
            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху-слева", (xAxis - 1), (yAxis - 1)));
        }
        return result;
    }

    private static boolean checkBottomRight(int[][] map, int xAxis, int yAxis) {
        if (xAxis == 0) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        if (yAxis == FIELD_SIZE - 1) {
            return true;
        }
        boolean result = map[xAxis - 1][yAxis + 1] == 0;
        if (!result) {
            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху-справа", (xAxis - 1), (yAxis + 1)));
        }
        return result;
    }

    private static boolean checkDownLeft(int[][] map, int xAxis, int yAxis) {
        if (xAxis == FIELD_SIZE - 1) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        if (yAxis == 0) {
            return true;
        }
        boolean result = map[xAxis + 1][yAxis - 1] == 0;
        if (!result) {
            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "снизу-слева", (xAxis + 1), (yAxis - 1)));
        }
        return result;
    }

    private static boolean checkDownRight(int[][] map, int xAxis, int yAxis) {
        if (xAxis == FIELD_SIZE - 1) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        if (yAxis == FIELD_SIZE - 1) {
            return true;
        }
        boolean result = map[xAxis + 1][yAxis + 1] == 0;
        if (!result) {
            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "снизу-cправа", (xAxis + 1), (yAxis + 1)));
        }
        return result;
    }

    /**
     * Проверка слева от координат
     * <pre>
     * <b>Пример 1:</b>
     *  xAxis = 1, yAxis = 1
     *     0 0 0
     *     0 X 0
     *     0 0 0
     *     result = true
     * <b>Пример 2:</b>
     * xAxis = 1, yAxis = 2
     *     0 0 0
     *     0 1 X
     *     0 0 0
     *     result = false
     * </pre>
     *
     * @param map   поле боя
     * @param xAxis координаты X
     * @param yAxis координаты Y
     * @return true если после слева от координат xAxis:yAxis пустое (0)
     */
    private static boolean isLeftEmpty(int[][] map, int xAxis, int yAxis) {
        if (yAxis == 0) {           // если координаты x это край, то проверять значения от края нет смысла
            return true;
        }
        boolean result = map[xAxis][yAxis - 1] == 0;
        if (!result) {
            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "слева", xAxis, (yAxis - 1)));
        }
        return result;

    }

    /**
     * Проверка сверху от координат
     *
     * @param map   поле боя
     * @param xAxis координаты X
     * @param yAxis координаты Y
     * @return true если после сверху от координат xAxis:yAxis пустое (0)
     */
    private static boolean isUpEmpty(int[][] map, int xAxis, int yAxis) {
        if (xAxis == 0) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        boolean result = map[xAxis - 1][yAxis] == 0;
        if (!result) {
            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху", (xAxis - 1), yAxis));
        }
        return result;
    }

    /**
     * Проверка справа от координат
     *
     * @param map   поле боя
     * @param xAxis координаты X
     * @param yAxis координаты Y
     * @return true если после справа от координат xAxis:yAxis пустое (0)
     */
    private static boolean isRightEmpty(int[][] map, int xAxis, int yAxis) {
        if (yAxis == FIELD_SIZE - 1) {           // если координаты x это край, то проверять значения от края нет смысла
            return true;
        }
        boolean result = map[xAxis][yAxis + 1] == 0;
        if (!result) {
            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "справа", xAxis, (yAxis - 1)));
        }
        return result;

    }

    /**
     * Проверка снизу от координат
     *
     * @param map   поле боя
     * @param xAxis координаты X
     * @param yAxis координаты Y
     * @return true если после снизу от координат xAxis:yAxis пустое (0)
     */
    private static boolean isDownEmpty(int[][] map, int xAxis, int yAxis) {
        if (xAxis == FIELD_SIZE - 1) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;        //
        }
        boolean result = map[xAxis + 1][yAxis] == 0;
        if (!result) {
            logger.get(LogLevel.WARN).add(String.format(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху", xAxis, (yAxis - 1)));
        }
        return result;
    }

    /**
     * Генерирует случайное число от 0 до константы FIELD_SIZE
     *
     * @return число от 0 до константы FIELD_SIZE
     */
    private static int getAxis() {
        // можно сократить запись
        // return new Random().nextInt(FIELD_SIZE)
        Random random = new Random();
        return random.nextInt(FIELD_SIZE);
    }
}
