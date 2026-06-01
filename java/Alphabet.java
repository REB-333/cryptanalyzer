import java.util.HashMap;
import java.util.Map;

/**
 * Класс алфавита для шифра Цезаря
 * Содержит все символы, которые могут быть зашифрованы
 * Использует HashMap для быстрого поиска индекса символа
 */
public class Alphabet {

    // Алфавит: русские и английские буквы, знаки препинания, пробел
    private static final char[] CHARS = {
            // Русские буквы (строчные)
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м',
            'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ',
            'ы', 'ь', 'э', 'ю', 'я',
            // Русские буквы (заглавные)
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М',
            'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ',
            'Ы', 'Ь', 'Э', 'Ю', 'Я',
            // Английские буквы (строчные)
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            // Английские буквы (заглавные)
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            // Знаки препинания и пробел (как в документе "Разбор и подсказки")
            '.', ',', '!', '?', ':', ';', '-', '(', ')', '"', '\'', ' ', '\n', '\r'
    };

    // HashMap для быстрого поиска индекса (ускоряет шифрование)
    private static final Map<Character, Integer> CHAR_TO_INDEX = new HashMap<>();

    static {
        for (int i = 0; i < CHARS.length; i++) {
            CHAR_TO_INDEX.put(CHARS[i], i);
        }
    }

    public static int getSize() {
        return CHARS.length;
    }

    public static boolean contains(char c) {
        return CHAR_TO_INDEX.containsKey(c);
    }

    public static int getIndex(char c) {
        Integer index = CHAR_TO_INDEX.get(c);
        return index != null ? index : -1;
    }

    public static char getChar(int index) {
        int normalizedIndex = ((index % getSize()) + getSize()) % getSize();
        return CHARS[normalizedIndex];
    }

    public static char[] getChars() {
        return CHARS.clone();
    }

    /**
     * Получить алфавит для вывода (только русские буквы)
     */
    public static String getRussianAlphabet() {
        return "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    }
}