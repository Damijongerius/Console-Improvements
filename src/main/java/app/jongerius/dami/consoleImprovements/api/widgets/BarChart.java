package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A widget that displays a horizontal bar chart.
 */
public class BarChart implements ConsoleTUI.Widget {
    private final String title;
    private final Map<String, Double> data = new LinkedHashMap<>();
    private final int maxHeight = 5;

    /**
     * Constructs a new BarChart with the given title.
     *
     * @param title The title displayed above the chart.
     */
    public BarChart(String title) {
        this.title = title;
    }

    /**
     * Sets or updates the value for a specific label in the chart.
     *
     * @param label The label of the bar.
     * @param value The value of the bar.
     */
    public void setValue(String label, double value) {
        data.put(label, value);
    }

    @Override
    public String render() {
        if (data.isEmpty()) return "";

        double max = data.values().stream().max(Double::compare).orElse(1.0);
        if (max == 0) max = 1.0;

        StringBuilder sb = new StringBuilder("\u001B[1m").append(title).append("\u001B[0m: ");
        
        // Simple horizontal bar chart for now, but with nice blocks
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double value = entry.getValue();
            int width = (int) ((value / max) * 10);
            
            sb.append(entry.getKey()).append(" [");
            for (int i = 0; i < 10; i++) {
                if (i < width) sb.append("\u001B[32m█\u001B[0m");
                else sb.append("\u001B[37m░\u001B[0m");
            }
            sb.append("] ").append(String.format("%.1f", value)).append("  ");
        }

        return sb.toString();
    }
}
