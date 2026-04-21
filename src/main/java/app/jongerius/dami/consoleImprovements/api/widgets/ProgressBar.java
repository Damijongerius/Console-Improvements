package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

/**
 * A widget that displays a horizontal progress bar.
 */
public class ProgressBar implements ConsoleTUI.Widget {
    private final String message;
    private final int total;
    private int current = 0;
    private final int width = 30;

    /**
     * Constructs a new ProgressBar.
     *
     * @param message The label displayed before the progress bar.
     * @param total The total value representing 100% completion.
     */
    public ProgressBar(String message, int total) {
        this.message = message;
        this.total = total;
    }

    /**
     * Sets the current progress value absolutely.
     *
     * @param current The new current value.
     */
    public void setCurrent(int current) {
        this.current = Math.min(current, total);
    }

    /**
     * Increments the current progress by a specific amount.
     *
     * @param amount The amount to increment by.
     */
    public void increment(int amount) {
        this.current = Math.min(current + amount, total);
    }

    @Override
    public String render() {
        int percent = (int) (((double) current / total) * 100);
        int progressBlocks = (int) (width * ((double) current / total));

        StringBuilder sb = new StringBuilder(message).append(" [");
        sb.append("\u001B[32m");
        for (int i = 0; i < progressBlocks; i++) sb.append("━");
        sb.append("\u001B[0m");
        for (int i = progressBlocks; i < width; i++) sb.append("─");
        
        sb.append("] ")
          .append("\u001B[33m").append(percent).append("% ")
          .append("\u001B[0m(").append(current).append("/").append(total).append(")");

        return sb.toString();
    }
}
