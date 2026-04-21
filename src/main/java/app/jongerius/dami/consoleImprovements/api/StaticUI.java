package app.jongerius.dami.consoleImprovements.api;

/**
 * A static utility class to access the initialized ConsoleTUI instance
 * from anywhere in the application, and to safely print raw ANSI strings.
 */
public class StaticUI {
    private static ConsoleTUI tuiInstance;

    /**
     * Sets the active ConsoleTUI instance.
     *
     * @param tui The ConsoleTUI instance to set.
     */
    public static void setTui(ConsoleTUI tui) {
        tuiInstance = tui;
    }

    /**
     * Prints a raw ANSI string to the console, bypassing Bukkit logger prefixes.
     * Uses the active TUI instance if available; otherwise falls back to System.out.
     *
     * @param ansi The ANSI string to print.
     */
    public static void printRaw(String ansi) {
        if (tuiInstance != null) {
            tuiInstance.printRaw(ansi + "\n");
        } else {
            // Fallback if TUI not yet initialized
            System.out.println(ansi);
        }
    }
}
