package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

import java.util.ArrayList;
import java.util.List;

/**
 * A widget that renders a small, inline chart (sparkline) to represent
 * data trends over time.
 */
public class Sparkline implements ConsoleTUI.Widget {
    private static final String[] BAR_SYMBOLS = {" ", "▂", "▃", "▄", "▅", "▆", "▇", "█"};
    private final List<Double> data = new ArrayList<>();
    private final int maxDataPoints;
    private final String label;
    private final String color;

    /**
     * Constructs a new Sparkline widget.
     *
     * @param label The label to display next to the sparkline.
     * @param color The ANSI color code for the sparkline blocks.
     * @param maxDataPoints The maximum number of historical data points to keep and render.
     */
    public Sparkline(String label, String color, int maxDataPoints) {
        this.label = label;
        this.color = color;
        this.maxDataPoints = maxDataPoints;
    }

    /**
     * Adds a new data value to the sparkline. Older values will be pushed out
     * if the maximum number of data points is exceeded.
     *
     * @param value The new value to add.
     */
    public void addValue(double value) {
        data.add(value);
        if (data.size() > maxDataPoints) {
            data.remove(0);
        }
    }

    @Override
    public String render() {
        if (data.isEmpty()) return "";

        double min = data.stream().min(Double::compare).orElse(0.0);
        double max = data.stream().max(Double::compare).orElse(1.0);
        double range = max - min;
        if (range == 0) range = 1.0;

        StringBuilder sb = new StringBuilder(label).append(": ").append(color);
        for (double val : data) {
            int index = (int) (((val - min) / range) * (BAR_SYMBOLS.length - 1));
            sb.append(BAR_SYMBOLS[index]);
        }
        sb.append("\u001B[0m");

        return sb.toString();
    }
}
