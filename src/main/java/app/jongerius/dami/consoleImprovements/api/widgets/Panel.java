package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;
import app.jongerius.dami.consoleImprovements.api.charm.LipGloss;
import app.jongerius.dami.consoleImprovements.api.charm.Borders;

/**
 * A widget that wraps another widget or plain text inside a styled LipGloss box.
 */
public class Panel implements ConsoleTUI.Widget {
    private ConsoleTUI.Widget innerWidget;
    private String textContent;
    private String title;
    private Borders border = Borders.NORMAL;
    private String borderColor = "\u001B[37m"; // Default white
    private int padding = 1;

    /**
     * Constructs a Panel wrapping another widget.
     *
     * @param innerWidget The widget to wrap inside the panel.
     */
    public Panel(ConsoleTUI.Widget innerWidget) {
        this.innerWidget = innerWidget;
    }

    /**
     * Constructs a Panel wrapping plain text.
     *
     * @param textContent The text content to display inside the panel.
     */
    public Panel(String textContent) {
        this.textContent = textContent;
    }

    /**
     * Sets an optional title for the panel.
     * Note: This will be rendered inside the panel content area, above the main content,
     * as LipGloss currently doesn't natively support titles embedded in borders.
     *
     * @param title The title text.
     * @return The Panel instance.
     */
    public Panel title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the border style for the panel.
     *
     * @param border The border style.
     * @return The Panel instance.
     */
    public Panel border(Borders border) {
        this.border = border;
        return this;
    }

    /**
     * Sets the ANSI border color.
     *
     * @param borderColor The ANSI color code.
     * @return The Panel instance.
     */
    public Panel borderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /**
     * Sets the padding inside the panel.
     *
     * @param padding The padding amount.
     * @return The Panel instance.
     */
    public Panel padding(int padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public String render() {
        StringBuilder contentBuilder = new StringBuilder();

        if (title != null && !title.isEmpty()) {
            contentBuilder.append("\u001B[1m").append(title).append("\u001B[0m\n\n");
        }

        if (innerWidget != null) {
            String renderedWidget = innerWidget.render();
            if (renderedWidget != null) {
                contentBuilder.append(renderedWidget);
            }
        } else if (textContent != null) {
            contentBuilder.append(textContent);
        }

        return LipGloss.style()
            .setContent(contentBuilder.toString())
            .border(border)
            .borderColor(borderColor)
            .padding(padding, padding)
            .render();
    }
}
