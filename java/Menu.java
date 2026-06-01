import java.util.Scanner;
import java.io.IOException;

/**
 * Класс меню программы
 * Отвечает за взаимодействие с пользователем
 */
public class Menu {

    private final Cipher cipher = new Cipher();
    private final FileManager fileManager = new FileManager();
    private final Validator validator = new Validator();
    private final BruteForceAnalyzer bruteForce = new BruteForceAnalyzer();
    private final StatisticalAnalyzer statisticalAnalyzer = new StatisticalAnalyzer();

    public void start(Scanner scanner) {
        while (true) {
            printMenu();

            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                System.out.println("\nСпасибо за использование программы! До свидания!");
                break;
            }

            try {
                switch (choice) {
                    case "1" -> encryptMode(scanner);
                    case "2" -> decryptMode(scanner);
                    case "3" -> bruteForceMode(scanner);
                    case "4" -> statisticalMode(scanner);
                    case "5" -> fileMode(scanner);
                    default -> System.out.println("Неверный выбор! Введите число от 0 до 5.");
                }
            } catch (IOException e) {
                System.out.println("Ошибка при работе с файлом: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            System.out.println("\nНажмите Enter для продолжения...");
            scanner.nextLine();
        }
    }

    private void printMenu() {
        System.out.println("\n==========================================");
        System.out.println("         КРИПТОАНАЛИЗАТОР               ");
        System.out.println("           Шифр Цезаря                  ");
        System.out.println("==========================================");
        System.out.println("1. Зашифровать текст");
        System.out.println("2. Расшифровать текст (с ключом)");
        System.out.println("3. Взломать перебором (Brute Force)");
        System.out.println("4. Взломать статистическим анализом");
        System.out.println("5. Работа с файлом");
        System.out.println("0. Выйти");
        System.out.println("------------------------------------------");
    }

    private void encryptMode(Scanner scanner) {
        System.out.print("Введите текст: ");
        String text = scanner.nextLine();

        System.out.print("Введите ключ (сдвиг): ");
        int key = Integer.parseInt(scanner.nextLine());

        String result = cipher.encrypt(text, key);
        System.out.println("Результат: " + result);
    }

    private void decryptMode(Scanner scanner) {
        System.out.print("Введите текст: ");
        String text = scanner.nextLine();

        System.out.print("Введите ключ (сдвиг): ");
        int key = Integer.parseInt(scanner.nextLine());

        String result = cipher.decrypt(text, key);
        System.out.println("Результат: " + result);
    }

    private void bruteForceMode(Scanner scanner) {
        System.out.print("Введите зашифрованный текст: ");
        String text = scanner.nextLine();

        System.out.print("Показать все варианты? (да/нет): ");
        String showAll = scanner.nextLine();

        if (showAll.equalsIgnoreCase("да")) {
            bruteForce.bruteForceWithPrint(text);
        } else {
            String result = bruteForce.bruteForce(text);
            System.out.println("Результат: " + result);
        }
    }

    private void statisticalMode(Scanner scanner) {
        System.out.print("Введите зашифрованный текст: ");
        String text = scanner.nextLine();

        System.out.print("Использовать эталонный текст? (да/нет): ");
        String useReference = scanner.nextLine();

        String result;
        if (useReference.equalsIgnoreCase("да")) {
            System.out.print("Введите эталонный текст: ");
            String reference = scanner.nextLine();
            result = statisticalAnalyzer.decryptWithReference(text, reference);
        } else {
            result = statisticalAnalyzer.autoDecrypt(text);
        }

        System.out.println("Результат: " + result);
    }

    private void fileMode(Scanner scanner) throws IOException {
        System.out.print("Введите путь к файлу: ");
        String path = scanner.nextLine();

        // Валидация файла
        String error = validator.validateInputFile(path);
        if (error != null) {
            System.out.println("Ошибка: " + error);
            return;
        }

        System.out.println("Читаем файл...");
        String content = fileManager.readFile(path);

        System.out.println("Содержимое файла (первые 200 символов):");
        System.out.println(content.substring(0, Math.min(200, content.length())));
        System.out.println("...");

        System.out.print("\nВыберите действие (1 - шифровать, 2 - расшифровать, 3 - взломать): ");
        String action = scanner.nextLine();

        System.out.print("Введите ключ (0 - автоматический подбор): ");
        int key = Integer.parseInt(scanner.nextLine());

        String result;

        if (key == 0) {
            // Автоматический подбор
            System.out.print("Метод (1 - brute force, 2 - статанализ): ");
            String method = scanner.nextLine();

            if (method.equals("1")) {
                result = bruteForce.bruteForce(content);
            } else {
                result = statisticalAnalyzer.autoDecrypt(content);
            }
        } else {
            // Ручной режим
            if (action.equals("1")) {
                result = cipher.encrypt(content, key);
                System.out.println("Шифрование с ключом " + key);
            } else {
                result = cipher.decrypt(content, key);
                System.out.println("Расшифровка с ключом " + key);
            }
        }

        String outputFile = "result_" + System.currentTimeMillis() + ".txt";
        fileManager.writeFile(result, outputFile);

        System.out.println("Результат сохранён в файл: " + outputFile);

        // Показываем фрагмент результата
        System.out.println("\nФрагмент результата:");
        System.out.println(result.substring(0, Math.min(300, result.length())));
        if (result.length() > 300) {
            System.out.println("...(текст сокращён)");
        }
    }
}