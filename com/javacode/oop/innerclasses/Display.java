package com.javacode.oop.innerclasses;

public class Display {
    private static final int DISPLAY_HEIGHT = 1920;
    private static final int DISPLAY_WIDTH = 1280;
    private int x = 0;

    public Display() {
        Pixel pixel = new Pixel(10, 10, Pixel.Color.BLUE);

    }

    // Если какой то класс не может существовать вне какого то другого класса
    // то его обьявляют прямо внутри этого самого класса
    // в нашем случае это пиксель, который не может быть использован
    // без дисплея
    private class Pixel {
        private int x;
        private int y;
        private Color color;

        // Несмотря на то что поля класса Display имеют доступ private static
        // мы можем к ним обращаться из вложенного класса
        private Pixel(int x, int y, Color color) {

            if (0 <= x && x <= DISPLAY_WIDTH && 0 <= y &&
                    y <= DISPLAY_HEIGHT) {
                this.x = x;
                this.y = y;
                this.color = color;
                System.out.println("Creating " + color + " pixel at (" + x + ","
                        + y + ")");
            }else {
                // обрабатываем ошибку
                throw new IllegalArgumentException("Incorrect pixel position");
            }
        }
        // Немного об области видимости. Данная функция выводит на экран
        // значения переменной х, трех разных областей видимости
        // первый вывод, это то что мы непосредственно передаем в метод
        // в нашем случае это "456", второй это переменная х в экземпляре класса
        // Pixel к которому мы обращаетмся словом this
        // третий это обращение к полю класса Display к которому мы обращаемся как
        // Display.this, вывод будет такой:
//        x = 456
//        this.x = 123
//        Display.this.x = 0
        public void testScope(int x) {
            System.out.println("x = "+x);
            System.out.println("this.x = "+this.x);
            System.out.println("Display.this.x = "+Display.this.x);
    }

    // то же в целом касается и нашего перечисления, оно не имеет смысла
    // вне класса дисплей, поэтому инициализируем его здесь же
    public enum Color {
        RED, GREEN, BLUE, CYAN, MAGENTA, YELLOW, BLACK;
    }


    }


}
