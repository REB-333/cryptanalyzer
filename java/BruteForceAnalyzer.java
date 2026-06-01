/**
 * Класс для взлома шифра методом перебора всех ключей
 * Пробует все возможные сдвиги и выбирает лучший по эвристике
 */
public class BruteForceAnalyzer {

    private final Cipher cipher = new Cipher();

    /**
     * Взлом перебором
     * @param encryptedText зашифрованный текст
     * @return расшифрованный текст (лучший вариант)
     */
    public String bruteForce(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return "";
        }

        int alphabetSize = Alphabet.getSize();
        int bestKey = 0;
        int bestScore = -1;

        System.out.println("Перебираю " + alphabetSize + " ключей...");

        for (int key = 0; key < alphabetSize; key++) {
            String decrypted = cipher.decrypt(encryptedText, key);
            int score = calculateScore(decrypted);

            if (score > bestScore) {
                bestScore = score;
                bestKey = key;
            }

            // Прогресс-бар
            if (key > 0 && key % (alphabetSize / 10) == 0) {
                System.out.print(".");
            }
        }

        System.out.println();
        System.out.println("Найден ключ: " + bestKey);
        System.out.println("Оценка качества: " + bestScore);

        return cipher.decrypt(encryptedText, bestKey);
    }

    /**
     * Оценка "похожести" текста на осмысленный
     */
    private int calculateScore(String text) {
        int score = 0;

        if (text == null || text.isEmpty()) {
            return 0;
        }

        String lowerText = text.toLowerCase();

        // 1. Пробелы — признак слов
        if (text.contains(" ")) score += 20;

        // 2. Пунктуация
        if (text.contains(", ")) score += 5;
        if (text.contains(". ")) score += 5;
        if (text.contains("! ")) score += 3;
        if (text.contains("? ")) score += 3;

        // 3. Частые русские слова
        String[] russianWords = {" и ", " в ", " на ", " с ", " по ", " к ", " у ", " за "};
        for (String word : russianWords) {
            if (lowerText.contains(word)) score += 15;
        }

        // 4. Частые английские слова
        String[] englishWords = {" the ", " and ", " of ", " to ", " in "};
        for (String word : englishWords) {
            if (lowerText.contains(word)) score += 15;
        }

        // 5. Гласные буквы (должны встречаться часто)
        String vowels = "аеёиоуыэюяaeiou";
        int vowelCount = 0;
        for (char c : lowerText.toCharArray()) {
            if (vowels.indexOf(c) != -1) vowelCount++;
        }
        score += Math.min(vowelCount / 10, 20);

        return score;
    }

    /**
     * Простой перебор с выводом всех вариантов
     * Полезно для отладки или демонстрации
     */
    public void bruteForceWithPrint(String encryptedText) {
        System.out.println("\n=== ВСЕ ВАРИАНТЫ РАСШИФРОВКИ ===");

        for (int key = 0; key < Alphabet.getSize(); key++) {
            String result = cipher.decrypt(encryptedText, key);
            System.out.println("Ключ " + key + ": " + result);
            System.out.println("----------------------------------------");
        }
    }
}