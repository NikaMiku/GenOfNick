import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger word3 = new AtomicInteger(0);
    private static AtomicInteger word4 = new AtomicInteger(0);
    private static AtomicInteger word5 = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + (int) (Math.random() * 3));
        }

        for (String text : texts) {
            new Thread(() -> checkBeauty(text)).start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println("Красивых слов с длиной 3: " + word3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + word4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + word5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt((int) (Math.random() * letters.length())));
        }
        return text.toString();
    }

    public static void checkBeauty(String word) {
        if (isPalindrome(word) || isSameLetterWord(word) || isIncreasingLetters(word)) {
            if (word.length() == 3) {
                word3.incrementAndGet();
            } else if (word.length() == 4) {
                word4.incrementAndGet();
            } else if (word.length() == 5) {
                word5.incrementAndGet();
            }
        }
    }

    public static boolean isPalindrome(String word) {
        return word.equals(new StringBuilder(word).reverse().toString());
    }

    public static boolean isSameLetterWord(String word) {
        return word.matches("^([a-c])\\1*$");
    }

    public static boolean isIncreasingLetters(String word) {
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) < word.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}