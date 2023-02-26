import java.util.Random;

/**
 * @author Andrej Reutow
 * created on 25.02.2023
 */
//todo 1 Ввод с клавиатуры
//todo 2 Проверить можно ли разместить указанное количество кораблей на карте
public class BattleshipApplication {
    private static final int FIELD_SIZE = 3;
    private static final int SHIPS = 4;

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
            System.out.println("Устанавливаю кораблик номер " + i);
            setShip(map);
        }
    }

    /**
     * Расставляет корабли на карте боя.
     */
    private static void setShip(int[][] map) {
        boolean isValid;
        do {
            int xAxis = getAxis();
            int yAxis = getAxis();
            System.out.println("Ось х:" + xAxis + ", Y:" + yAxis);
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
//        boolean result = false;
//        if (map[xAxis][yAxis] == 0) {
//            result = true;
//        } else {
//            result = false;
//        }

        if (map[xAxis][yAxis] == 0) {
//            boolean isPlaceValid = false;
//            result = xAxis > 0 && map[xAxis - 1][yAxis] == 0;
//            if (xAxis >= 0 && map[xAxis - 1][yAxis] == 0) {
//                isPlaceValid = true;
//            } else if (xAxis < FIELD_SIZE - 1 && map[xAxis + 1][yAxis] == 0) {
//                isPlaceValid = true;
//            } else if (yAxis > 0 && map[xAxis][yAxis - 1] == 0) {
//                isPlaceValid = true;
//            } else if (yAxis < FIELD_SIZE - 1 && map[xAxis][yAxis + 1] == 0) {
//                isPlaceValid = true;
//            } else {
//                isPlaceValid = false;
//            }
//            return isPlaceValid;
            //todo fix it
            return (xAxis > 0 && map[xAxis - 1][yAxis] == 0) &&
                    (xAxis < FIELD_SIZE - 1 && map[xAxis + 1][yAxis] == 0) &&
                    (yAxis > 0 && map[xAxis][yAxis - 1] == 0) &&
                    (yAxis < FIELD_SIZE - 1 && map[xAxis][yAxis + 1] == 0);
        } else {
            return false;
        }
    }

    /**
     * Генерирует случайное число от 0 до константы FIELD_SIZE
     *
     * @return число от 0 до константы FIELD_SIZE
     */
    private static int getAxis() {
        Random random = new Random();
        // range 0 - 9
        return random.nextInt(FIELD_SIZE);
    }
}
