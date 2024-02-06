import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger word3 = new AtomicInteger(0);
    private static AtomicInteger word4 = new AtomicInteger(0);
    private static AtomicInteger word5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> checkPalindromeWords(texts, 3, word3));
        Thread thread2 = new Thread(() -> checkSameLetterWords(texts, 4, word4));
        Thread thread3 = new Thread(() -> checkAscendingWords(texts, 5, word5));

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + word3);
        System.out.println("Красивых слов с длиной 4: " + word4);
        System.out.println("Красивых слов с длиной 5: " + word5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String word) {
        int length = word.length();
        for (int i = 0; i < length / 2; i++) {
            if (word.charAt(i) != word.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static void checkPalindromeWords(String[] texts, int length, AtomicInteger counter) {
        for (String text : texts) {
            if (text.length() == length && isPalindrome(text)) {
                counter.incrementAndGet();
            }
        }
    }

    public static void checkSameLetterWords(String[] texts, int length, AtomicInteger counter) {
        for (String text : texts) {
            if (text.length() == length && text.matches("^(.)\\1*$")) {
                counter.incrementAndGet();
            }
        }
    }

    public static void checkAscendingWords(String[] texts, int length, AtomicInteger counter) {
        for (String text : texts) {
            if (text.length() == length && isAscending(text)) {
                counter.incrementAndGet();
            }
        }
    }

    public static boolean isAscending(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) > word.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }
}