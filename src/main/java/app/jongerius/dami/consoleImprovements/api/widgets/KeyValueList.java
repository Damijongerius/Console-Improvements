package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A widget that displays a list of key-value properties, vertically aligning the values.
 */
public class KeyValueList implements ConsoleTUI.Widget {
    private final Map<String, String> properties = new LinkedHashMap<>();
    private final String keyColor;
    private final String valueColor;

    /**
     * Constructs a new KeyValueList.
     *
     * @param keyColor The ANSI color code for keys.
     * @param valueColor The ANSI color code for values.
     */
    public KeyValueList(String keyColor, String valueColor) {
        this.keyColor = keyColor != null ? keyColor : "";
        this.valueColor = valueColor != null ? valueColor : "";
    }

    /**
     * Adds or updates a property in the list.
     *
     * @param key The key string.
     * @param value The value string.
     */
    public void put(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String render() {
        if (properties.isEmpty()) return "";

        int maxKeyLength = 0;
        for (String key : properties.keySet()) {
            maxKeyLength = Math.max(maxKeyLength, stripAnsi(key).length());
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            int padding = maxKeyLength - stripAnsi(key).length() + 1; // +1 for space after colon

            sb.append(keyColor).append(key).append("\u001B[0m:")
              .append(" ".repeat(padding))
              .append(valueColor).append(value).append("\u001B[0m\n");
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private String stripAnsi(String s) {
        if (s == null) return "";
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
