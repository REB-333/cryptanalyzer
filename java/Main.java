import java.util.Scanner;

/**
 * Главный класс программы
 * Запускает меню и начинает работу
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        menu.start(scanner);
        scanner.close();
    }
}