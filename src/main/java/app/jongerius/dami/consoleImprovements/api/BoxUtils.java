package app.jongerius.dami.consoleImprovements.api;

/**
 * Utility class for quickly printing styled boxes directly to the console.
 */
public class BoxUtils {

    /**
     * Prints a box with a title, content, and a specified color border.
     *
     * @param title The title of the box.
     * @param content The inner text content.
     * @param color The ANSI color string for the border and title.
     */
    public static void printBox(String title, String content, String color) {
        int width = Math.max(title.length(), content.length()) + 4;
        org.bukkit.command.ConsoleCommandSender console = org.bukkit.Bukkit.getConsoleSender();
        
        // Top border with title
        console.sendMessage(color + "┏" + "━".repeat(2) + " " + ColorUtils.BOLD + title + " " + color + "━".repeat(width - title.length() - 5) + "┓" + ColorUtils.RESET);
        
        // Content
        console.sendMessage(color + "┃ " + ColorUtils.RESET + padRight(content, width - 3) + color + "┃" + ColorUtils.RESET);
        
        // Bottom border
        console.sendMessage(color + "┗" + "━".repeat(width - 1) + "┛" + ColorUtils.RESET);
    }

    /**
     * Prints a box with a title and content, applying a smooth RGB gradient across the lines.
     *
     * @param title The title of the box.
     * @param content The inner text content.
     * @param startR Start color red value (0-255).
     * @param startG Start color green value (0-255).
     * @param startB Start color blue value (0-255).
     * @param endR End color red value (0-255).
     * @param endG End color green value (0-255).
     * @param endB End color blue value (0-255).
     */
    public static void printGradientBox(String title, String content, int startR, int startG, int startB, int endR, int endG, int endB) {
        int width = Math.max(title.length(), content.length()) + 4;
        org.bukkit.command.ConsoleCommandSender console = org.bukkit.Bukkit.getConsoleSender();

        String top = "┏" + "━".repeat(2) + " " + title + " " + "━".repeat(width - title.length() - 5) + "┓";
        console.sendMessage(ColorUtils.gradient(top, startR, startG, startB, endR, endG, endB));

        console.sendMessage(ColorUtils.gradient("┃ ", startR, startG, startB, endR, endG, endB) + 
            ColorUtils.RESET + padRight(content, width - 3) + 
            ColorUtils.gradient("┃", endR, endG, endB, endR, endG, endB));

        String bottom = "┗" + "━".repeat(width - 1) + "┛";
        console.sendMessage(ColorUtils.gradient(bottom, startR, startG, startB, endR, endG, endB));
    }

    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
