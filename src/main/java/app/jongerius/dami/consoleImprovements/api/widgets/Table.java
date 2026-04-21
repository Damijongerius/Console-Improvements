package app.jongerius.dami.consoleImprovements.api.widgets;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to build and print a formatted table with borders directly to the console.
 * Note: This is not currently a TUI Widget, but a static rendering utility.
 */
public class Table {
    private final List<String> headers;
    private final List<List<String>> rows = new ArrayList<>();
    private final String borderColor = "\u001B[37m"; // Gray
    private final String headerColor = "\u001B[36;1m"; // Bold Cyan

    /**
     * Constructs a new Table with the specified headers.
     *
     * @param headers The column headers.
     */
    public Table(List<String> headers) {
        this.headers = headers;
    }

    /**
     * Adds a row of data to the table.
     *
     * @param row A list of strings corresponding to each column.
     */
    public void addRow(List<String> row) {
        rows.add(row);
    }

    /**
     * Renders and prints the constructed table directly to the console.
     */
    public void print() {
        if (headers.isEmpty()) return;

        int[] columnWidths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            columnWidths[i] = headers.get(i).length();
        }

        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                columnWidths[i] = Math.max(columnWidths[i], row.get(i).length());
            }
        }

        org.bukkit.command.ConsoleCommandSender console = org.bukkit.Bukkit.getConsoleSender();

        // Top Border
        console.sendMessage(renderDivider(columnWidths, "┌", "┬", "┐"));

        // Headers
        StringBuilder headerLine = new StringBuilder(borderColor + "│" + "\u001B[0m");
        for (int i = 0; i < headers.size(); i++) {
            headerLine.append(" ").append(headerColor).append(padRight(headers.get(i), columnWidths[i])).append("\u001B[0m ").append(borderColor).append("│").append("\u001B[0m");
        }
        console.sendMessage(headerLine.toString());

        // Header Divider
        console.sendMessage(renderDivider(columnWidths, "├", "┼", "┤"));

        // Rows
        for (List<String> row : rows) {
            StringBuilder rowLine = new StringBuilder(borderColor + "│" + "\u001B[0m");
            for (int i = 0; i < row.size(); i++) {
                rowLine.append(" ").append(padRight(row.get(i), columnWidths[i])).append(" ").append(borderColor).append("│").append("\u001B[0m");
            }
            console.sendMessage(rowLine.toString());
        }

        // Bottom Border
        console.sendMessage(renderDivider(columnWidths, "└", "┴", "┘"));
    }

    private String renderDivider(int[] widths, String left, String middle, String right) {
        StringBuilder sb = new StringBuilder(borderColor + left);
        for (int i = 0; i < widths.length; i++) {
            sb.append("─".repeat(widths[i] + 2));
            if (i < widths.length - 1) {
                sb.append(middle);
            }
        }
        sb.append(right + "\u001B[0m");
        return sb.toString();
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
