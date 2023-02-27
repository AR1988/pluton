import java.util.Random;

/**
 * @author Andrej Reutow
 * created on 25.02.2023
 */
//todo 1 Ввод с клавиатуры
//todo 2 Добавить ограничение попыток установки корабликов
//todo 3 !***!добавить проверки по диагонали
//todo 4 рефакторинг кода
//todo 5 !****! переписать используя всю мощь ООП
/*
    Текс задачи временно утерян ;), но обещал вернуться!
 */
public class BattleshipApplication {
    private static final int FIELD_SIZE = 5;
    private static final int SHIPS = 8;
    public static final String FIELD_IS_NOT_EMPTY_MSG = "Не возможно установить кораблик по координатам %d:%d, Причина: поле %s (%d:%d) уже занято\n";

    public static void main(String[] args) {
        int[][] result = new int[FIELD_SIZE][FIELD_SIZE];
        fillMap(result);
        for (int[] ints : result) {
            for (int anInt : ints) {
                System.out.print(anInt);
            }
            System.out.println();
        }
    }

    /**
     * Заполнение кораблей на карте
     *
     * @param map карта боя
     */
    public static void fillMap(int[][] map) {
        for (int i = 0; i < SHIPS; i++) {
            if (i == 0) {
                map[0][0] = 1;
                continue;
            }
            System.out.println("Устанавливаю кораблик номер " + (i + 1));
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
            System.out.println("Установка кораблика по координатам: " + xAxis + ":" + yAxis);
            isValid = validate(map, xAxis, yAxis);
            if (isValid) {
                // System.out.println("Кораблик установлен по координатам: " + xAxis + ":" + yAxis);
                map[xAxis][yAxis] = 1;
            } else {
                System.out.println("!!! Кораблик не установлен по координатам: " + xAxis + ":" + yAxis);
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
            System.out.println("Ячейка по координатам x" + xAxis + ":y" + yAxis + " уже занята");
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
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху-слева", (xAxis - 1), (yAxis - 1));
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
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху-справа", (xAxis - 1), (yAxis + 1));
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
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "снизу-слева", (xAxis + 1), (yAxis - 1));
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
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "снизу-cправа", (xAxis + 1), (yAxis + 1));
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
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "слева", xAxis, (yAxis - 1));
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
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху", (xAxis - 1), yAxis);
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
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "справа", xAxis, (yAxis - 1));
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
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху", xAxis, (yAxis - 1));
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
