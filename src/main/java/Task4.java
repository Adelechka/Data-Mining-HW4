import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.MurmurHash3;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;

public class Task4 {
    private static double PRECISION = 0.0001;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = getWordsForCBF(scanner.nextLine());
        int m = words.length;
        int n = (int) countLengthOfCBFArray(m);
        int k = (int) countOptionalAmountOfHashFunctions(n, m);
        int[] arrayCBF = new int[(int) countLengthOfCBFArray(m)];
        Arrays.fill(arrayCBF, 0);

        for (String word : words) {
            addItemToCBFArray(k, word, arrayCBF);
        }


        for (int i = 0; i < 10; i++) {
            System.out.println("Print your word to check it in post: ");
            String word = scanner.nextLine();

            System.out.println("word '" + word + "'" + (isWordInCBFArray(k, word, arrayCBF) ? " is ": " isn't " ) + "in post.");
        }
    }

    // k - hash functions
    private static double countOptionalAmountOfHashFunctions(int n, int m) {
        return (double) n / (double) m * Math.log(2);
    }

    // n - length of CBF array
    private static double countLengthOfCBFArray(int m) {
        return Math.log(PRECISION) / Math.log(0.62) * m;
    }

    // m - text len
    private static String[] getWordsForCBF(String text) {
        return text.split(" ");
    }

    private static void addItemToCBFArray(int k, String word, int[] arrayCBF) {
       for (int i = 0; i < k; i++) {
           long hash = Math.abs(MurmurHash3.hash32(Hashing.adler32().hashString(word, Charset.forName("UTF-8")).padToLong(), i));
           arrayCBF[(int) (hash % arrayCBF.length)] += 1;
       }
    }

    private static boolean isWordInCBFArray(int k, String word, int[] arrayCBF) {
        for (int i = 0; i < k; i++) {
            long hash = Math.abs(MurmurHash3.hash32(Hashing.adler32().hashString(word, Charset.forName("UTF-8")).padToLong(), i));

            if (arrayCBF[(int) (hash % arrayCBF.length)] == 0) {
                return false;
            }
        }
        return true;
    }
}
