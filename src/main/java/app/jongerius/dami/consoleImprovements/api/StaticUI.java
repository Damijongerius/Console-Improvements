package app.jongerius.dami.consoleImprovements.api;

public class StaticUI {
    private static ConsoleTUI tuiInstance;

    public static void setTui(ConsoleTUI tui) {
        tuiInstance = tui;
    }

    public static void printRaw(String ansi) {
        if (tuiInstance != null) {
            tuiInstance.printRaw(ansi + "\n");
        } else {
            // Fallback if TUI not yet initialized
            System.out.println(ansi);
        }
    }
}
