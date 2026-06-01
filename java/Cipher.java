/**
 * Класс для шифрования и расшифровки шифром Цезаря
 * Использует отдельный класс Alphabet для работы с символами
 */
public class Cipher {

    /**
     * Шифрование текста
     * @param text исходный текст
     * @param shift ключ сдвига
     * @return зашифрованный текст
     */
    public String encrypt(String text, int shift) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        // Нормализуем сдвиг (ключ может быть больше размера алфавита)
        int normalizedShift = shift % Alphabet.getSize();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int index = Alphabet.getIndex(c);

            if (index != -1) {
                // Формула из подсказок: (позиция + сдвиг) % размер_алфавита
                int newIndex = (index + normalizedShift) % Alphabet.getSize();
                result.append(Alphabet.getChar(newIndex));
            } else {
                // Символы вне алфавита оставляем без изменений
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * Расшифровка текста
     * @param text зашифрованный текст
     * @param shift ключ сдвига
     * @return расшифрованный текст
     */
    public String decrypt(String text, int shift) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        // Для расшифровки сдвигаем в обратную сторону
        int reverseShift = (Alphabet.getSize() - (shift % Alphabet.getSize())) % Alphabet.getSize();
        return encrypt(text, reverseShift);
    }
}