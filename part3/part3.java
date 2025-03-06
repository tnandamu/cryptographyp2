// Tarun Nandamudi, Anne Fu, Dhruv Shah, Stephen Argauer

/* 
 * The following code can be run in a Java Project and attempts to decrypt a ciphertext 
 * encrypted by the Vigenère Cipher.
 */
public class part3 {
    // English letter frequencies
    private static final double[] ENGLISH_FREQ = {
        0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020, 0.061,
        0.070, 0.002, 0.008, 0.040, 0.024, 0.067, 0.075, 0.019,
        0.001, 0.060, 0.063, 0.091, 0.028, 0.010, 0.023, 0.001,
        0.020, 0.001
    };

    /*
     * Main method: Defines the ciphertext, Determines the encryption key length.
     * Recovers the encryption key, Decrypts the message, and Prints 
     * the key and the decrypted text.
     */
    public static void main(String[] args) {
        String ciphertext = 
                "KBWWMRSINEXVVGWDMLBKUVBSLJGWEUFWEKDCUDPIAKSYUHUOAKNWWBQHIILXKVAEALAMNA" +
                "SYJMGCFUMWVCTAKSGYBJGJYOAWOOVMWKDGAELWVMPLDIFMPUZFQWOMTOVBSOMQFRHWPAES" +
                "CYKXMPERCFLBTWRGHKWFMTNKTVFKVLNBKGKUYKBOPWUCFTECQKBSMOKNVMMLMTKJIDXKQF" +
                "KLGEWXWIUUVMUKKILAMKJUULTIUSIYKNTVDRQWGNQJTYEXVVAJMGFMVADYKNVCTCYLHZGU" +
                "FPWKBJWTIFMMPLFZWEMELIIFBKEGDGMGQESKCGGAHJFGLAMVWTBFHTQYPJJHDKVVLWOMPA" +
                "ELWLXQFJYLHIEGLLLHZFWIIJWCNQROLAWTAQYVPITJRHLBAUMVXTRIHWUYJTTLMUAWYWTW" +
                "OUEITGERHQVWOELHAVIVAFHKLMTNZWWLVQOVHUKGRLTYJMIKFTIEFCPATULBWPKSSVXNCM" +
                "CNOBBJLYYCXGPWTYKLITQKIVXKTQGNLAMEGDGMGQESKCGGAUGCYDRQPLYYZTVFKFZLAMGF" +
                "UOKXZVZZMSIXNAVMTHBJOYYFMPGVRNSBAKFDILBWPGMYJXTGUKLGGQEFVNOHZMKFLSMZGK" +
                "KIFTVGDVWLKWPATXWOQEWZZLAMEGDGMGQESKCGGARJFPAWMTAJMWKDGVNCLAIYSILSGBUW" +
                "VEAGOVZFMWVWOELHAVIVAFHKMPGHIINBLGJTUFGWVHIINBLGLYYVTBCTVWSNAGAKBSLLGK" +
                "ZAFXLVZVNWVPPGCIYRAWUYNZTBKLTUFGWVTVUUVMUKVXTRIPQKBAKLRSINQPMFGEILAIXW" +
                "RHQLQNNVLTNTNWKMSGLVZVXALKWKJCGGAYAKBAGBJWVRWVCVAMYTKIPUYUJXAVACFGGOQA" +
                "EAOAQNWKBWKMJSJHGMGGLSYWGIFWTCKBWPOYYLAMTLFMWXSNWXCKEIVAFHOXUWKKQGKSYA" +
                "KBUHVIJVMKBVFMJNJRIESUYEBKUHICNTKAYIIMIACFUILAMTKKIUKIHLRHSIXTGRWZMPCL" +
                "RXVKMUKVMSETQXKBWFCNLZJDXKQEGYLBVIUFHUXZPKKBSMPCNVVWXVVZVZGVCUGWMGFCEZ" +
                "UYTTBGTLNOXKCFRFDTOTWVNZTBYWNCDEVGWUIFZWKFXBGGMULRHVBVHGIGWWXWTCCUWMDS" +
                "KYSUWWLYIOUMULKIHKWVWTNDBJGJKSSGLUWTOJBBAAEVGMPQMIFSPACFUIMKBGUYHGEWIQ";
        
        int keyLength = getKeyLength(ciphertext);
        String key = getKey(ciphertext, keyLength);
        System.out.println("Found key: " + key);
        
        String decryptedText = decrypt(ciphertext, key);
        System.out.println(decryptedText);
    }

/*
 * Determines the key length for the Vigenère Cipher
 */
    private static int getKeyLength(String ciphertext) {
        // Expected IC for English text
        final double ENGLISH_IC = 0.065;
        
        double bestDifference = Double.MAX_VALUE;
        int bestLength = 0;
        
        System.out.println("Testing potential key lengths:");
        
        // Try key lengths 6 to 8
        for (int len = 6; len < 9; len++) {
            System.out.println("Key Length of " + len);
            String[] subsequences = new String[len];
            double[] ic = new double[len];
            double sumIC = 0.0;
            
            // Extract subsequences and calculate IC for each
            for (int i = 0; i < len; i++) {
                subsequences[i] = extractSubsequence(ciphertext, len, i);
                ic[i] = calculateIC(subsequences[i]);
                sumIC += ic[i];
                System.out.println("Substring " + (i+1) + " with Index of Coincidence of " + ic[i] + ": " + subsequences[i]);
            }
            
            // Calculate average IC for this key length
            double avgIC = sumIC / len;
            double difference = Math.abs(avgIC - ENGLISH_IC);
            
            System.out.printf("Key length %d: Average IC = %.6f (difference from expected: %.6f)%n%n", len, avgIC, difference);
            
            // Check if this is better than previous best
            if (difference < bestDifference) {
                bestDifference = difference;
                bestLength = len;
            }
        }
        
        System.out.printf("Best key length: %d (Average IC closest to %.6f)%n", bestLength, ENGLISH_IC);
        
        return bestLength;
    }

    /*
     * Calculates the index of coincidence for a given text
     */
    public static double calculateIC(String text) {
        // Count letter frequencies
        int[] frequencies = new int[26];
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                frequencies[c - 'A']++;
            }
        }
        // Calculate IC
        int totalLength = text.length();
        double sum = 0;
        for (int i = 0; i < 26; i++) {
            // For each letter, add n_i * (n_i - 1) to the sum
            sum += frequencies[i] * (frequencies[i] - 1);
        }
        // Check to prevent division by zero or one
        if (totalLength <= 1) {
            return 0;
        }
        return sum / (totalLength * (totalLength - 1));
    }
    
    /*
     * Determines the key for the Vigenère Cipher
     */
    private static String getKey(String ciphertext, int keyLength){
        char[] key = new char[keyLength];
        
        for (int i = 0; i < keyLength; i++) {
            // extract subsequences for this key length, using the extractSubsequence method
            String subsequence = extractSubsequence(ciphertext, keyLength, i);
            // calc the frequencies with calculateFrequencies method
            double[] freq = calculateFrequencies(subsequence);
            // calc the Mg (match goodness) values for each shift g
            double[] mgValues = new double[26];

            // calculate Mg values
            for (int g = 0; g < 26; g++) {
                double[] shiftedFreq = shiftFrequencies(freq, g);
                mgValues[g] = dotProduct(ENGLISH_FREQ, shiftedFreq);
            }
            // find the shift with maximum Mg value
            int maxShift = 0;
            for (int g = 1; g < 26; g++) {
                if (mgValues[g] > mgValues[maxShift]) {
                    maxShift = g;
                }
            }
            // prints all Mg values for this position
            System.out.printf("Position %d Mg values:\n", i+1);
            for (int g = 0; g < 26; g++) {
                System.out.printf("g=%2d (%c): Mg=%.6f %s\n", g, (char)('A' + g), mgValues[g], (g == maxShift) ? "← MAX" : "");
            }
            // sets key letter based on max shift
            key[i] = (char)('A' + maxShift);
            System.out.printf("\nKey letter for position %d: %c (shift=%d)\n\n", i+1, key[i], maxShift);
        }

        return new String(key);
    }

    /*
     * extracts subsequence for the give offset (i.e. i = 0, 1, etc.)
     */ 
    private static String extractSubsequence(String text, int keyLength, int offset) {
        StringBuilder sb = new StringBuilder();

        // gets every letter from the given i or offset that would appear in the subsequence
        for (int j = offset; j < text.length(); j += keyLength) {
            sb.append(text.charAt(j));
        }
        // returns final string subsequence
        return sb.toString();
    }
    
  /*
   * Calculates frequencies for each letter for the given text
   */
    private static double[] calculateFrequencies(String text) {
        double[] freq = new double[26];
        int total = text.length();
        // gets frequency count of each letter in the text
        for (char c : text.toCharArray()) {
            freq[c - 'A']++;
        }
        // divides the freq count array by the length of subtext to get frequency
        for (int i = 0; i < 26; i++) {
            freq[i] /= total;
        }
        // returns the final frequency array
        return freq;
    }
    
    // Shift frequencies by g, from 0 to 25 and return the new 
    private static double[] shiftFrequencies(double[] freq, int shift) {
        double[] shifted = new double[26];
        
        for (int i = 0; i < 26; i++) {
            shifted[i] = freq[(i + shift) % 26];
        }
        
        return shifted;
    }
    
    // Calculate dot product
    private static double dotProduct(double[] v1, double[] v2) {
        double sum = 0;
        for (int i = 0; i < 26; i++) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }

    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        key = key.toLowerCase();
        int keyLength = key.length();

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);

            if (Character.isLetter(cipherChar)) {
                int cipherIndex = cipherChar - 'A';
                int keyIndex = key.charAt(i % keyLength) - 'a';
                int plainIndex = (cipherIndex - keyIndex + 26) % 26;
                char plainChar = (char) (plainIndex + 'a');
                plaintext.append(plainChar);
            } else {
                plaintext.append(cipherChar);
            }
        }
        return plaintext.toString();
    }
}
