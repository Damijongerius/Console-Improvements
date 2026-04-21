package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

/**
 * A widget that displays an active timer (stopwatch or countdown).
 * It updates its displayed value based on the system time when rendered.
 */
public class Timer implements ConsoleTUI.Widget {
    private final String label;
    private final long startTimeMillis;
    private Long targetTimeMillis;
    private final String color;

    /**
     * Constructs a stopwatch timer starting from now.
     *
     * @param label The text displayed before the timer.
     * @param color The ANSI color code for the timer digits.
     */
    public Timer(String label, String color) {
        this.label = label;
        this.color = color != null ? color : "";
        this.startTimeMillis = System.currentTimeMillis();
        this.targetTimeMillis = null;
    }

    /**
     * Constructs a countdown timer.
     *
     * @param label The text displayed before the timer.
     * @param color The ANSI color code for the timer digits.
     * @param durationSeconds The number of seconds to countdown from.
     */
    public Timer(String label, String color, int durationSeconds) {
        this.label = label;
        this.color = color != null ? color : "";
        this.startTimeMillis = System.currentTimeMillis();
        this.targetTimeMillis = this.startTimeMillis + (durationSeconds * 1000L);
    }

    @Override
    public String render() {
        long now = System.currentTimeMillis();
        long diff;

        if (targetTimeMillis != null) {
            // Countdown
            diff = targetTimeMillis - now;
            if (diff < 0) diff = 0;
        } else {
            // Stopwatch
            diff = now - startTimeMillis;
        }

        long totalSeconds = diff / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long millis = diff % 1000;

        // Format as MM:SS.mmm
        String timeString = String.format("%02d:%02d.%03d", minutes, seconds, millis);

        return label + ": " + color + timeString + "\u001B[0m";
    }
}
