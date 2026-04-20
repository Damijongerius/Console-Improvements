package app.jongerius.dami.consoleImprovements.api;

public class ColorUtils {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static String color(String text, String color) {
        return color + text + RESET;
    }

    public static String gradient(String text, int startR, int startG, int startB, int endR, int endG, int endB) {
        StringBuilder sb = new StringBuilder();
        int len = text.length();
        for (int i = 0; i < len; i++) {
            int r = startR + (endR - startR) * i / len;
            int g = startG + (endG - startG) * i / len;
            int b = startB + (endB - startB) * i / len;
            sb.append(String.format("\u001B[38;2;%d;%d;%dm%c", r, g, b, text.charAt(i)));
        }
        sb.append(RESET);
        return sb.toString();
    }
}
