package app.jongerius.dami.consoleImprovements.api;

public class BoxUtils {
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
