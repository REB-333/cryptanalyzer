import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для валидации входных данных
 * Проверяет файлы, ключи, безопасность
 */
public class Validator {

    /**
     * Проверяет, существует ли файл и является ли он файлом (не директорией)
     */
    public boolean isFileExists(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        Path path = Paths.get(filePath);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    /**
     * Проверка ключа (должен быть неотрицательным)
     */
    public boolean isValidKey(int key) {
        return key >= 0;
    }

    /**
     * Защита от перезаписи системных файлов
     */
    public boolean isSafeFilename(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        String lowerPath = filePath.toLowerCase();
        String[] dangerous = {".bash_profile", "hosts", "passwd", "shadow", "c:\\windows", "/etc/"};

        for (String d : dangerous) {
            if (lowerPath.contains(d)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Полная проверка входного файла
     */
    public String validateInputFile(String filePath) {
        if (!isFileExists(filePath)) {
            return "Файл не существует: " + filePath;
        }
        if (!isSafeFilename(filePath)) {
            return "Недопустимый файл: " + filePath;
        }
        return null;
    }

    /**
     * Полная проверка выходного файла
     */
    public String validateOutputFile(String filePath, String inputFile) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return "Путь для сохранения не указан";
        }
        if (!isSafeFilename(filePath)) {
            return "Недопустимый путь: " + filePath;
        }
        if (filePath.equals(inputFile)) {
            return "Выходной файл не может совпадать с входным";
        }
        return null;
    }

    /**
     * Проверка, что текст не пустой
     */
    public boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}