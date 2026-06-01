/**
 * Класс для взлома шифра статистическим анализом
 * Комбинирует два подхода:
 * 1. По самой частой букве
 * 2. Полноценный анализ с эталонным текстом
 */
public class StatisticalAnalyzer {

    private final Cipher cipher = new Cipher();

    /**
     * Автоматическая расшифровка (упрощённый метод)
     * Определяет сдвиг по самой частой букве, предполагая, что это 'о'
     */
    public String autoDecrypt(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        System.out.println("\n=== СТАТИСТИЧЕСКИЙ АНАЛИЗ ===");

        // Считаем частоту букв в тексте
        int[] frequencies = countRussianLetters(text);

        // Находим самую частую букву
        char mostFrequent = findMostFrequent(frequencies);

        // Предполагаем, что самая частая буква в русском тексте — 'о'
        char expected = 'о';

        int mostIndex = getRussianIndex(mostFrequent);
        int expectedIndex = getRussianIndex(expected);

        // Вычисляем ключ
        int key = mostIndex - expectedIndex;
        if (key < 0) {
            key += 33; // 33 буквы в русском алфавите (с ё)
        }

        System.out.println("Самая частая буква: '" + mostFrequent + "'");
        System.out.println("Предполагаемый ключ: " + key);

        return cipher.decrypt(text, key);
    }

    /**
     * Расшифровка с эталонным текстом (более точный метод)
     * @param encryptedText зашифрованный текст
     * @param referenceText эталонный текст (пример нормального текста)
     * @return расшифрованный текст
     */
    public String decryptWithReference(String encryptedText, String referenceText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return "";
        }

        if (referenceText == null || referenceText.isEmpty()) {
            System.out.println("Предупреждение: эталонный текст не предоставлен, использую упрощённый метод");
            return autoDecrypt(encryptedText);
        }

        System.out.println("\n=== СТАТИСТИЧЕСКИЙ АНАЛИЗ (С ЭТАЛОНОМ) ===");

        // Считаем частоты букв в эталонном тексте
        int[] referenceFreq = countRussianLetters(referenceText);

        // Считаем частоты в зашифрованном тексте
        int[] encryptedFreq = countRussianLetters(encryptedText);

        // Ищем наилучший сдвиг
        int bestShift = findBestShift(referenceFreq, encryptedFreq);

        System.out.println("Найден ключ: " + bestShift);

        return cipher.decrypt(encryptedText, bestShift);
    }

    /**
     * Подсчёт частоты русских букв в тексте (только строчные, без учёта регистра)
     */
    private int[] countRussianLetters(String text) {
        String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        int[] counts = new int[russianAlphabet.length()];

        String lowerText = text.toLowerCase();

        for (char c : lowerText.toCharArray()) {
            int index = russianAlphabet.indexOf(c);
            if (index != -1) {
                counts[index]++;
            }
        }

        return counts;
    }

    /**
     * Находит самую частую букву
     */
    private char findMostFrequent(int[] counts) {
        String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        int maxCount = -1;
        int maxIndex = 0;

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > maxCount) {
                maxCount = counts[i];
                maxIndex = i;
            }
        }

        return russianAlphabet.charAt(maxIndex);
    }

    /**
     * Возвращает индекс русской буквы (0-32)
     */
    private int getRussianIndex(char c) {
        String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        return russianAlphabet.indexOf(Character.toLowerCase(c));
    }

    /**
     * Находит сдвиг, при котором частоты максимально похожи
     */
    private int findBestShift(int[] reference, int[] encrypted) {
        int bestShift = 0;
        double bestCorrelation = -1;

        for (int shift = 0; shift < reference.length; shift++) {
            double correlation = 0;
            for (int i = 0; i < reference.length; i++) {
                int shiftedIndex = (i + shift) % reference.length;
                correlation += reference[i] * encrypted[shiftedIndex];
            }

            if (correlation > bestCorrelation) {
                bestCorrelation = correlation;
                bestShift = shift;
            }
        }

        return bestShift;
    }
}