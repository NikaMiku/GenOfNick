import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count3 = new AtomicInteger(0);
    public static AtomicInteger count4 = new AtomicInteger(0);
    public static AtomicInteger count5 = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if(isPalindrome(text)) {
                    count3.incrementAndGet();
                }
            }
        });
        Thread thread4 = new Thread(() -> {
            for (String text : texts) {
                if(isSameCharacter(text)) {
                    count4.incrementAndGet();
                }
            }
        });
        Thread thread5 = new Thread(() -> {
            for (String text : texts) {
                if(isSorted(text)) {
                    count5.incrementAndGet();
                }
            }
        });
        thread3.start();
        thread4.start();
        thread5.start();
        thread3.join();
        thread4.join();
        thread5.join();
        System.out.println("Красивых слов с длинной 3: " + count3.get() + " шт");
        System.out.println("Красивых слов с длинной 4: " + count4.get() + " шт");
        System.out.println("Красивых слов с длинной 5: " + count5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        StringBuilder reversedText = new StringBuilder(text).reverse();
        return text.equals(reversedText.toString());
    }

    public static boolean isSameCharacter(String text) {
        char firstChar = text.charAt(0);
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSorted(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}