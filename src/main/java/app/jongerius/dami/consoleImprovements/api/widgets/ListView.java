package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

import java.util.ArrayList;
import java.util.List;

/**
 * A widget that displays a list of items, supporting an active selection and visual pagination.
 */
public class ListView implements ConsoleTUI.Widget {
    private final List<String> items = new ArrayList<>();
    private int selectedIndex = 0;
    private int maxVisibleItems = 5;
    private String selectedColor = "\u001B[36;1m"; // Bold Cyan
    private String unselectedColor = "\u001B[37m"; // White
    private String selectedPrefix = "▶ ";
    private String unselectedPrefix = "  ";

    /**
     * Adds an item to the list.
     *
     * @param item The text of the item to add.
     */
    public void addItem(String item) {
        items.add(item);
    }

    /**
     * Sets the items for the list, replacing any existing items.
     *
     * @param newItems The list of new items.
     */
    public void setItems(List<String> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        setSelectedIndex(0);
    }

    /**
     * Sets the currently selected index.
     *
     * @param index The index to select. Will be clamped to valid bounds.
     */
    public void setSelectedIndex(int index) {
        if (items.isEmpty()) {
            this.selectedIndex = 0;
            return;
        }
        this.selectedIndex = Math.max(0, Math.min(index, items.size() - 1));
    }

    /**
     * Moves the selection up by one.
     */
    public void selectPrevious() {
        setSelectedIndex(selectedIndex - 1);
    }

    /**
     * Moves the selection down by one.
     */
    public void selectNext() {
        setSelectedIndex(selectedIndex + 1);
    }

    /**
     * Sets the maximum number of items visible at once.
     *
     * @param maxVisibleItems The number of items to show.
     */
    public void setMaxVisibleItems(int maxVisibleItems) {
        this.maxVisibleItems = Math.max(1, maxVisibleItems);
    }

    @Override
    public String render() {
        if (items.isEmpty()) return "No items.";

        StringBuilder sb = new StringBuilder();

        int startIdx = Math.max(0, selectedIndex - (maxVisibleItems / 2));
        int endIdx = startIdx + maxVisibleItems;

        // Adjust if we are near the end
        if (endIdx > items.size()) {
            endIdx = items.size();
            startIdx = Math.max(0, endIdx - maxVisibleItems);
        }

        if (startIdx > 0) {
            sb.append("\u001B[90m  ↑ ").append(startIdx).append(" more items...\u001B[0m\n");
        }

        for (int i = startIdx; i < endIdx; i++) {
            boolean isSelected = (i == selectedIndex);
            String prefix = isSelected ? selectedPrefix : unselectedPrefix;
            String color = isSelected ? selectedColor : unselectedColor;

            sb.append(color).append(prefix).append(items.get(i)).append("\u001B[0m\n");
        }

        if (endIdx < items.size()) {
            sb.append("\u001B[90m  ↓ ").append(items.size() - endIdx).append(" more items...\u001B[0m\n");
        }

        // Remove trailing newline
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }
}
