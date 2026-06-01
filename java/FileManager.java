import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

/**
 * Класс для работы с файлами
 * Использует NIO (как рекомендовано в подсказках)
 */
public class FileManager {

    /**
     * Чтение файла
     * @param filePath путь к файлу
     * @return содержимое файла
     * @throws IOException если ошибка чтения
     */
    public String readFile(String filePath) throws IOException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IOException("Путь к файлу не указан");
        }

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("Файл не найден: " + filePath);
        }

        // Используем Files.readString() из Java 11+
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    /**
     * Запись в файл
     * @param content текст для записи
     * @param filePath путь к файлу
     * @throws IOException если ошибка записи
     */
    public void writeFile(String content, String filePath) throws IOException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IOException("Путь для сохранения не указан");
        }

        if (content == null) {
            content = "";
        }

        Path path = Paths.get(filePath);

        // Создаём директории, если их нет
        Path parent = path.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    /**
     * Проверка существования файла
     */
    public boolean fileExists(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }
        return Files.exists(Paths.get(filePath));
    }
}