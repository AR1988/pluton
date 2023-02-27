import java.util.Arrays;
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
public class BattleshipApplication2 {
    private static final int FIELD_SIZE = 5;
    private static final int SHIPS = 8;
    private static final char SET_SHIP = 'X';
    private static final char EMPTY_FIELD = '0';
    private static final char TRY_SET_SHIP = '#';
    public static final String FIELD_IS_NOT_EMPTY_MSG = "Не возможно установить кораблик по координатам %d:%d, Причина: поле %s (%d:%d) уже занято\n";

    private static final char[][] BATTLE_MAP = initMap();

    public static void main(String[] args) {
        fillMap();
        printMap();
    }

    private static void printMap() {
        for (char[] ints : BATTLE_MAP) {
            String row = Arrays.toString(ints)
                    .replace("[", "")
                    .replace(",", "")
                    .replace("]", "");
            System.out.println(row);
            //тоже самое с использованием регулярного выражения
            //System.out.println(Arrays.toString(ints).replaceAll("[,\\[\\]]", ""));
        }
    }

    private static char[][] initMap() {
        char[][] chars = new char[FIELD_SIZE][FIELD_SIZE];
        for (char[] row : chars) {
            Arrays.fill(row, EMPTY_FIELD);
        }
        return chars;
    }

    /**
     * Заполнение кораблей на карте
     */
    public static void fillMap() {
        for (int i = 0; i < SHIPS; i++) {
            System.out.println("Устанавливаю кораблик номер " + (i + 1));
            setShip();
        }
    }

    /**
     * Расставляет корабли на карте боя.
     */
    private static void setShip() {
        boolean canPlaceShip;
        do {
            int xAxis = getAxis();  // Генерируем случайное число для xAxis оси
            int yAxis = getAxis();  // Генерируем случайное число для yAxis оси
            System.out.println("Установка кораблика по координатам: " + xAxis + ":" + yAxis);
            char oldValue = BATTLE_MAP[xAxis][yAxis];
            BATTLE_MAP[xAxis][yAxis] = TRY_SET_SHIP;
            canPlaceShip = validate(xAxis, yAxis);
            if (canPlaceShip) {
                System.out.println("Кораблик установлен по координатам: " + xAxis + ":" + yAxis);
                BATTLE_MAP[xAxis][yAxis] = SET_SHIP;
            } else {
                BATTLE_MAP[xAxis][yAxis] = oldValue;
                System.out.println("!!! Кораблик не установлен по координатам: " + xAxis + ":" + yAxis);
            }
        } while (!canPlaceShip);
    }

    /**
     * Проверяет, можно ли расположить корабль на указанных координатах.
     * Корабли не должны пересекаться или касаться друг друга.
     */
    private static boolean validate(int xAxis, int yAxis) {
        if (BATTLE_MAP[xAxis][yAxis] != EMPTY_FIELD) {                           //проверка, является ячейка по координатам xAxis:yAxis пустая
            boolean leftOk = isLeftEmpty(xAxis, yAxis);    //слева     (270)
            boolean checkBottomLeft = checkBottomLeft(xAxis, yAxis);
            boolean upOk = isUpEmpty(xAxis, yAxis);        //сверху    (0)
            boolean checkBottomRight = checkBottomRight(xAxis, yAxis);
            boolean rightOk = isRightEmpty(xAxis, yAxis);  //справа    (903)
            boolean checkDownRight = checkDownRight(xAxis, yAxis);
            boolean downOk = isDownEmpty(xAxis, yAxis);    //снизу     (180)
            boolean checkDownLeft = checkDownLeft(xAxis, yAxis);
            return leftOk &&
                    rightOk &&
                    downOk &&
                    upOk &&
                    checkBottomLeft &&
                    checkBottomRight &&
                    checkDownLeft &&
                    checkDownRight;
        } else {                                            //если ячейка не пустая, то туда нельзя установить кораблик
            System.out.println("Ячейка по координатам x" + xAxis + ":y" + yAxis + " уже занята");
            return false;                                   //результат проверки
        }
    }


    private static boolean checkBottomLeft(int xAxis, int yAxis) {
        if (xAxis == 0) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        if (yAxis == 0) {
            return true;
        }

        boolean result = BATTLE_MAP[xAxis - 1][yAxis - 1] == EMPTY_FIELD;
        if (!result) {
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху-слева", (xAxis - 1), (yAxis - 1));
        }
        return result;
    }

    private static boolean checkBottomRight(int xAxis, int yAxis) {
        if (xAxis == 0) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        if (yAxis == FIELD_SIZE - 1) {
            return true;
        }
        boolean result = BATTLE_MAP[xAxis - 1][yAxis + 1] == EMPTY_FIELD;
        if (!result) {
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху-справа", (xAxis - 1), (yAxis + 1));
        }
        return result;
    }

    private static boolean checkDownLeft(int xAxis, int yAxis) {
        if (xAxis == FIELD_SIZE - 1) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        if (yAxis == 0) {
            return true;
        }
        boolean result = BATTLE_MAP[xAxis + 1][yAxis - 1] == EMPTY_FIELD;
        if (!result) {
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "снизу-слева", (xAxis + 1), (yAxis - 1));
        }
        return result;
    }

    private static boolean checkDownRight(int xAxis, int yAxis) {
        if (xAxis == FIELD_SIZE - 1) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        if (yAxis == FIELD_SIZE - 1) {
            return true;
        }
        boolean result = BATTLE_MAP[xAxis + 1][yAxis + 1] == EMPTY_FIELD;
        if (!result) {
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "снизу-право", (xAxis + 1), (yAxis + 1));
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
     * @param xAxis координаты X
     * @param yAxis координаты Y
     * @return true если после слева от координат xAxis:yAxis пустое (0)
     */
    private static boolean isLeftEmpty(int xAxis, int yAxis) {
        if (yAxis == 0) {           // если координаты x это край, то проверять значения от края нет смысла
            return true;
        }
        boolean result = BATTLE_MAP[xAxis][yAxis - 1] == EMPTY_FIELD;
        if (!result) {
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "слева", xAxis, (yAxis - 1));
        }
        return result;

    }

    /**
     * Проверка сверху от координат
     *
     * @param xAxis координаты X
     * @param yAxis координаты Y
     * @return true если после сверху от координат xAxis:yAxis пустое (0)
     */
    private static boolean isUpEmpty(int xAxis, int yAxis) {
        if (xAxis == 0) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        boolean result = BATTLE_MAP[xAxis - 1][yAxis] == EMPTY_FIELD;
        if (!result) {
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "сверху", (xAxis - 1), yAxis);
        }
        return result;
    }

    /**
     * Проверка справа от координат
     *
     * @param xAxis координаты X
     * @param yAxis координаты Y
     * @return true если после справа от координат xAxis:yAxis пустое (0)
     */
    private static boolean isRightEmpty(int xAxis, int yAxis) {
        if (yAxis == FIELD_SIZE - 1) {           // если координаты x это край, то проверять значения от края нет смысла
            return true;
        }
        boolean result = BATTLE_MAP[xAxis][yAxis + 1] == EMPTY_FIELD;
        if (!result) {
            System.out.printf(FIELD_IS_NOT_EMPTY_MSG, xAxis, yAxis, "справа", xAxis, (yAxis - 1));
        }
        return result;

    }

    /**
     * Проверка снизу от координат
     *
     * @param xAxis координаты X
     * @param yAxis координаты Y
     * @return true если после снизу от координат xAxis:yAxis пустое (0)
     */
    private static boolean isDownEmpty(int xAxis, int yAxis) {
        if (xAxis == FIELD_SIZE - 1) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;        //
        }
        boolean result = BATTLE_MAP[xAxis + 1][yAxis] == EMPTY_FIELD;
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
