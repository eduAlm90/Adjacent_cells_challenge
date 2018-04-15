package challenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static void printTitle(String title) {
        System.out.println(Utils.TypographicalEmphasis.BOLD.getAnsiCode()
                + "\n[" + title + "]"
                + Utils.TypographicalEmphasis.NORMAL.getAnsiCode());
    }

    public static String readLineFromConsole(String strPrompt) {
        try {
            System.out.println(strPrompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static boolean confirm(String sMessage) {
        String strConfirm;
        boolean result;
        do {
            strConfirm = Utils.readLineFromConsole("\n" + sMessage + "\n");
            if (strConfirm == null) {
                strConfirm = "";
            }
            result = "y".equalsIgnoreCase(strConfirm);
        } while (!"y".equalsIgnoreCase(strConfirm) && !"n".equalsIgnoreCase(strConfirm));
        return result;
    }

    public static Object showAndSelect(List<?> list, String sHeader) {
        showList(list, sHeader);
        return chooseObject(list);
    }

    public static void showList(List<?> list, String sHeader) {
        System.out.println(sHeader);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        if (list.isEmpty()) {
            System.out.println("(Empty list)");
        }
        System.out.println("");
        System.out.println("0 - Cancel");
    }

    public static Object chooseObject(List<?> list) {
        String option;
        int nOption;
        do {
            option = Utils.readLineFromConsole("Choose option: ");
            nOption = new Integer(option);
        } while (nOption < 0 || nOption > list.size());

        if (nOption == 0) {
            return null;
        } else {
            return list.get(nOption - 1);
        }
    }

    public static Set<String> addStringsToSet(int min, int max, String itemToAdd) {

        Set<String> keyL = new HashSet<>();
        System.out.printf("%s ADD (%d to %d), 0 to terminate:%n", itemToAdd, min, max);
        do {
            String key = readLineFromConsole(itemToAdd + ": ");
            if (key == null) {
                System.err.println("Invalid input");
            } else if (key.equals("0") && keyL.size() >= min) {
                break;
            } else if (!key.equals("0")) {
                keyL.add(key);

            } else if (key.equals("0")) {
                return keyL;
            }
        } while (keyL.size() < max);
        return keyL;
    }

    public enum TypographicalEmphasis {
        NORMAL("\033[0;0m"),
        BOLD("\033[0;1m");

        private String ansiCode;

        TypographicalEmphasis(String ansiCode) {
            this.ansiCode = ansiCode;
        }

        public String getAnsiCode() {
            return ansiCode;
        }
    }
}
