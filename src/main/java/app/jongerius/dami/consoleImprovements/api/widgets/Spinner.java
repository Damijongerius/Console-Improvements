package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

/**
 * A widget that renders an animated loading spinner.
 * Requires frequent updates (via TUI.update()) to animate.
 */
public class Spinner implements ConsoleTUI.Widget {
    private static final String[] FRAMES = {"⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏"};
    private final String message;
    private int frameIndex = 0;

    /**
     * Constructs a new Spinner.
     *
     * @param message The message to display alongside the spinner.
     */
    public Spinner(String message) {
        this.message = message;
    }

    @Override
    public String render() {
        String frame = FRAMES[frameIndex % FRAMES.length];
        frameIndex++;
        return "\u001B[36m" + frame + "\u001B[0m " + message;
    }
}
