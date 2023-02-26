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
    private static final int SHIPS = 20;

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
            System.out.println("Устанавливаю кораблик номер " + i + 1);
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
                System.out.println("Кораблик установлен по координатам: " + xAxis + ":" + yAxis);
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
            boolean rightOk = isRigthEmpty(map, xAxis, yAxis);  //справа    (90)
            boolean downOk = isDownEmpty(map, xAxis, yAxis);    //снизу     (180)
            return leftOk && rightOk && downOk && upOk;         //результат проверок
        } else {                                                //если ячейка не пустая, то туда нельзя установить кораблик
            return false;                                       //результат проверки
        }
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
     * @param map поле боя
     * @param xAxis   координаты X
     * @param yAxis   координаты Y
     * @return true если после слева от координат xAxis:yAxis пустое (0)
     */
    private static boolean isLeftEmpty(int[][] map, int xAxis, int yAxis) {
        if (xAxis == 0) {           // если координаты x это край, то проверять значения от края нет смысла
            return true;
        }
        return map[xAxis - 1][yAxis] == 0;
    }

    /**
     * Проверка сверху от координат
     *
     * @param map поле боя
     * @param xAxis   координаты X
     * @param yAxis   координаты Y
     * @return true если после сверху от координат xAxis:yAxis пустое (0)
     */
    private static boolean isUpEmpty(int[][] map, int xAxis, int yAxis) {
        if (yAxis == FIELD_SIZE - 1) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;
        }
        return map[xAxis][yAxis + 1] == 0;
    }

    /**
     * Проверка справа от координат
     *
     * @param map поле боя
     * @param xAxis   координаты X
     * @param yAxis   координаты Y
     * @return true если после справа от координат xAxis:yAxis пустое (0)
     */
    private static boolean isRigthEmpty(int[][] map, int xAxis, int yAxis) {
        if (xAxis == FIELD_SIZE - 1) {           // если координаты x это край, то проверять значения от края нет смысла
            return true;
        }
        return map[xAxis + 1][yAxis] == 0;
    }

    /**
     * Проверка снизу от координат
     *
     * @param map поле боя
     * @param xAxis   координаты X
     * @param yAxis   координаты Y
     * @return true если после снизу от координат xAxis:yAxis пустое (0)
     */
    private static boolean isDownEmpty(int[][] map, int xAxis, int yAxis) {
        if (yAxis == 0) {           // если координаты y это край, то проверять значения от края нет смысла
            return true;        //
        }
        return map[xAxis][yAxis - 1] == 0;
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
