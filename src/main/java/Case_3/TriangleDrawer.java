package Case_3;

public class TriangleDrawer {
    // Константи для легкого налаштування та читабельності
    private static final int TRIANGLE_HEIGHT = 7;
    private static final String SYMBOL_STAR = "*";
    private static final String SYMBOL_SPACE = " ";

    public static void main(String[] args) {
        drawTriangle(TRIANGLE_HEIGHT);
    }
    private static void drawTriangle(int height) {
        int currentRow = 1;
        do {
            printRepeatedString(height - currentRow, SYMBOL_SPACE);
            if (currentRow == 1) {
                drawTop();
            } else if (currentRow == height) {
                drawBase(height);
            } else {
                drawHollowRow(currentRow);
            }

            // Перехід на новий рядок
            System.out.println();
            currentRow++;

        } while (currentRow <= height);
    }
    private static void drawTop() {
        System.out.print(SYMBOL_STAR);
    }
    private static void drawBase(int size) {
        int i = 1;
        do {
            System.out.print(SYMBOL_STAR);
            if (i < size) {
                System.out.print(SYMBOL_SPACE);
            }
            i++;
        } while (i <= size);
    }
    private static void drawHollowRow(int rowNumber) {
        System.out.print(SYMBOL_STAR); // Ліва стінка
        int innerSpaces = 2 * (rowNumber - 1) - 1;
        printRepeatedString(innerSpaces, SYMBOL_SPACE);

        System.out.print(SYMBOL_STAR); // Права стінка
    }
    private static void printRepeatedString(int count, String str) {
        if (count <= 0) return;

        int i = 0;
        do {
            System.out.print(str);
            i++;
        } while (i < count);
    }
}