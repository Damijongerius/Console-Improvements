package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

import java.util.ArrayList;
import java.util.List;

/**
 * A widget that renders multiple other widgets side-by-side or in a grid layout.
 */
public class Grid implements ConsoleTUI.Widget {
    private final List<List<ConsoleTUI.Widget>> rows = new ArrayList<>();
    private final int spacing;

    /**
     * Constructs a new Grid widget.
     *
     * @param spacing The number of spaces to insert between adjacent columns.
     */
    public Grid(int spacing) {
        this.spacing = Math.max(0, spacing);
    }

    /**
     * Adds a new row of widgets to the grid.
     *
     * @param rowWidgets An array or varargs of widgets to display in this row.
     */
    public void addRow(ConsoleTUI.Widget... rowWidgets) {
        List<ConsoleTUI.Widget> row = new ArrayList<>();
        for (ConsoleTUI.Widget w : rowWidgets) {
            row.add(w);
        }
        rows.add(row);
    }

    @Override
    public String render() {
        if (rows.isEmpty()) return "";

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rows.size(); i++) {
            List<ConsoleTUI.Widget> row = rows.get(i);

            // Render each widget in the row
            List<String[]> renderedWidgets = new ArrayList<>();
            int maxLines = 0;

            for (ConsoleTUI.Widget w : row) {
                String rendered = w.render();
                String[] lines = rendered != null ? rendered.split("\n", -1) : new String[]{""};
                renderedWidgets.add(lines);
                maxLines = Math.max(maxLines, lines.length);
            }

            // We need to render side by side, so we build line by line
            for (int lineIdx = 0; lineIdx < maxLines; lineIdx++) {
                for (int widgetIdx = 0; widgetIdx < renderedWidgets.size(); widgetIdx++) {
                    String[] widgetLines = renderedWidgets.get(widgetIdx);
                    String lineContent = lineIdx < widgetLines.length ? widgetLines[lineIdx] : "";

                    result.append(lineContent);

                    // Add spacing if not the last widget in the row
                    if (widgetIdx < renderedWidgets.size() - 1) {
                        // In a real grid, we might pad to the max width of the column.
                        // For a simple side-by-side, we just append spaces, but this can misalignment
                        // if widgets have different line widths. We pad to the widget's max line length.
                        int maxWidgetWidth = 0;
                        for (String l : widgetLines) {
                            maxWidgetWidth = Math.max(maxWidgetWidth, stripAnsi(l).length());
                        }

                        int currentLen = stripAnsi(lineContent).length();
                        int paddingNeeded = maxWidgetWidth - currentLen + spacing;
                        if (paddingNeeded > 0) {
                            result.append(" ".repeat(paddingNeeded));
                        }
                    }
                }
                result.append("\n");
            }
        }

        // Remove trailing newline
        if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }

    private String stripAnsi(String s) {
        if (s == null) return "";
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
