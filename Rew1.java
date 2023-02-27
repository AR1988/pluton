 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.util.Random;
 
 public class SeaBatle {
   private static final int AMOUNT_OF_SHIPS = 10;
   private static final int AMOUNT_OF_SHOTS = 5;
 
   public static void main(String[] args) throws IOException {
     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     String[][] deskStr = makeDesk();
     String[][] deskStrHide = makeDesk();
     int x, y;
     int hits = 0;
     System.out.println("Кораблей: " + AMOUNT_OF_SHIPS + " | Выстрелов: " + AMOUNT_OF_SHOTS);
 
     for (int i = 0; i < AMOUNT_OF_SHIPS; i++) {
       try {
         placeFigAuto(deskStr);
       } catch (StackOverflowError e) {
         System.err.println("Слишком много кораблей!");
         return;
       }
     }
 
     for (int i = 0; i < AMOUNT_OF_SHOTS; i++) {
       try {
 //        printDesk(deskStr);
         printDesk(deskStrHide);
         System.out.println("Ход " + (i + 1) + ". Введите координаты Вашего удара:");
         do {
           System.out.print("ряд: ");
           x = Integer.parseInt(br.readLine());
         } while (!(x > 0 && x < 11));
         do {
           System.out.print("столбик: ");
           y = Integer.parseInt(br.readLine());
         } while (!(y > 0 && y < 11));
         hits = hitAt(deskStr, x, y, deskStrHide, hits);
       } catch (NumberFormatException e) {
         System.out.println("Неверный ввод!");
         --i;
       }
     }
     finalShow(deskStr);
     printDesk(deskStr);
     System.out.println("[ Количество попаданий: " + hits+" ]");
 
   }
 
   public static void finalShow(String[][] deskStr) {
     for (int i = 0; i < 11; i++) {
       for (int j = 0; j < 11; j++) {
         if (deskStr[i][j].equals("⚫")) {
           deskStr[i][j] = "⬛";
         }
       }
     }
   }
 
   public static int hitAt(String[][] deskStr, int row, int column, String[][] deskStrHide,
                           int hits) {
     if (deskStr[row][column].equals("⛵")) {
       deskStr[row][column] = String.format("%s", "❌");
       deskStrHide[row][column] = String.format("%s", "❌");
       ++hits;
     } else {
       deskStr[row][column] = String.format("%s", "⬜");
       deskStrHide[row][column] = String.format("%s", "⬜");
     }
     return hits;
   }
 
   public static void placeFigAuto(String[][] deskStr) {
     Random rnd = new Random();
     int row = 1 + rnd.nextInt(10);
     int column = 1 + rnd.nextInt(10);
     if (deskStr[row][column].equals("⬛")) {
       deskStr[row][column] = String.format("%s", "⛵");
       zoneAround(deskStr, row, column);
     } else if (deskStr[row][column].equals("⛵") || deskStr[row][column].equals("⚫")) {
       placeFigAuto(deskStr);
     }
   }
 
   public static void zoneAround(String[][] deskStr, int x1, int y1) {
     for (int x2 = 1; x2 < 11; x2++) {
       for (int y2 = 1; y2 < 11; y2++) {
         if ((Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 1)
             || (Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 0)
             || (Math.abs(x1 - x2) == 0 && Math.abs(y1 - y2) == 1)) {
           deskStr[x2][y2] = "⚫";
         }
       }
     }
   }
 
   public static void placeFigAt(String[][] deskStr, int row, int column) {
     if (deskStr[row][column].equals("⬛")) {
       deskStr[row][column] = String.format("%s", "⛵");
     }
   }
 
   public static void printDesk(String[][] deskStr) {
     System.out.println("_____________________________");
     for (int i = 0; i < 11; i++) {
       for (int j = 0; j < 11; j++) {
         System.out.printf("%s", deskStr[i][j]);
       }
       System.out.println();
     }
   }
 
   public static String[][] makeDesk() {
     String[][] deskStr = new String[11][11];
     String numbers = " ➀➁➂➃➄➅➆➇➈➉";
     for (int row = 0; row < 11; row++) {
       for (int column = 0; column < 11; column++) {
         if (row == 0 && column == 0) {
           deskStr[row][column] = String.format("%s", " ⛴ ");
           continue;
         }
         if (row == 0) {
           if (column >= 4 && column < 7) {
             deskStr[row][column] = String.format("%s\u2009\u2009",
                 Character.toString(numbers.charAt(column)));
             continue;
           } else {
             deskStr[row][column] = String.format("%s ", Character.toString(numbers.charAt(column)));
             continue;
           }
         }
         if (column == 0) {
           deskStr[row][column] = String.format("%2s ", Character.toString(numbers.charAt(row)));
           continue;
         }
         deskStr[row][column] = "⬛";
 
 
       }
     }
     return deskStr;
   }
 }
