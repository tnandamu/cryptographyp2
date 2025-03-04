public class part3 {

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

        String key = "rustics";
        String decryptedText = decrypt(ciphertext, key);
        System.out.println(decryptedText);
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
