package app.jongerius.dami.consoleImprovements.api.charm;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for styling text blocks, inspired by the Charm LipGloss library in Go.
 * Allows adding borders, padding, margins, and colors to multiline strings.
 */
public class LipGloss {
    private String content;
    private int width = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;
    private int marginLeft = 0;
    private Borders border = Borders.NONE;
    private String borderColor = "\u001B[37m"; // White default
    private String foregroundColor = "";
    private String backgroundColor = "";

    /**
     * Starts a new styling builder.
     *
     * @return A new LipGloss builder instance.
     */
    public static LipGloss style() {
        return new LipGloss();
    }

    /**
     * Sets the multiline content to be styled.
     *
     * @param content The text to style.
     * @return The builder instance.
     */
    public LipGloss setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Overrides the width of the content area.
     * If not set, the width adapts to the longest line in the content.
     *
     * @param width The target width.
     * @return The builder instance.
     */
    public LipGloss width(int width) {
        this.width = width;
        return this;
    }

    /**
     * Sets vertical and horizontal padding around the content (inside the border).
     *
     * @param v Vertical padding (top and bottom).
     * @param h Horizontal padding (left and right).
     * @return The builder instance.
     */
    public LipGloss padding(int v, int h) {
        this.paddingTop = v;
        this.paddingBottom = v;
        this.paddingLeft = h;
        this.paddingRight = h;
        return this;
    }

    /**
     * Sets specific padding for each side around the content.
     *
     * @param top Top padding.
     * @param right Right padding.
     * @param bottom Bottom padding.
     * @param left Left padding.
     * @return The builder instance.
     */
    public LipGloss padding(int top, int right, int bottom, int left) {
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
        this.paddingLeft = left;
        return this;
    }

    /**
     * Sets the left margin (spacing outside the border).
     *
     * @param left The left margin size.
     * @return The builder instance.
     */
    public LipGloss margin(int left) {
        this.marginLeft = left;
        return this;
    }

    /**
     * Sets the border style.
     *
     * @param border The border style to use.
     * @return The builder instance.
     */
    public LipGloss border(Borders border) {
        this.border = border;
        return this;
    }

    /**
     * Sets the border color using a raw ANSI string.
     *
     * @param ansi The ANSI color string.
     * @return The builder instance.
     */
    public LipGloss borderColor(String ansi) {
        this.borderColor = ansi;
        return this;
    }

    /**
     * Sets the border color using RGB values.
     *
     * @param r Red (0-255).
     * @param g Green (0-255).
     * @param b Blue (0-255).
     * @return The builder instance.
     */
    public LipGloss borderColor(int r, int g, int b) {
        this.borderColor = String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
        return this;
    }

    /**
     * Sets the text foreground color using RGB values.
     *
     * @param r Red (0-255).
     * @param g Green (0-255).
     * @param b Blue (0-255).
     * @return The builder instance.
     */
    public LipGloss foreground(int r, int g, int b) {
        this.foregroundColor = String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
        return this;
    }

    /**
     * Sets the background color inside the padded area using RGB values.
     *
     * @param r Red (0-255).
     * @param g Green (0-255).
     * @param b Blue (0-255).
     * @return The builder instance.
     */
    public LipGloss background(int r, int g, int b) {
        this.backgroundColor = String.format("\u001B[48;2;%d;%d;%dm", r, g, b);
        return this;
    }

    /**
     * Renders the styled block into an ANSI-formatted string.
     *
     * @return The styled ANSI string.
     */
    public String render() {
        if (content == null || content.isEmpty()) {
            if (width > 0 || paddingTop > 0 || paddingBottom > 0 || paddingLeft > 0 || paddingRight > 0 || border != Borders.NONE) {
                // If there's styling but no content, render an empty string with styling
                content = "";
            } else {
                return "";
            }
        }

        String[] lines = content.split("\n", -1);
        int contentWidth = 0;
        for (String line : lines) {
            contentWidth = Math.max(contentWidth, stripAnsi(line).length());
        }

        if (width > 0) contentWidth = width;

        int totalWidth = contentWidth + paddingLeft + paddingRight;
        StringBuilder sb = new StringBuilder();

        String marginStr = " ".repeat(marginLeft);
        String reset = "\u001B[0m";

        // Top Border
        if (border != Borders.NONE) {
            sb.append(marginStr)
              .append(borderColor).append(border.topLeft)
              .append(border.top.repeat(totalWidth))
              .append(border.topRight).append(reset)
              .append("\n");
        }

        // Top Padding
        for (int i = 0; i < paddingTop; i++) {
            renderLine(sb, marginStr, "", totalWidth);
        }

        // Content
        for (String line : lines) {
            renderLine(sb, marginStr, line, totalWidth);
        }

        // Bottom Padding
        for (int i = 0; i < paddingBottom; i++) {
            renderLine(sb, marginStr, "", totalWidth);
        }

        // Bottom Border
        if (border != Borders.NONE) {
            sb.append(marginStr)
              .append(borderColor).append(border.bottomLeft)
              .append(border.bottom.repeat(totalWidth))
              .append(border.bottomRight).append(reset); // No newline at end
        }

        return sb.toString();
    }

    private void renderLine(StringBuilder sb, String marginStr, String contentLine, int totalWidth) {
        String reset = "\u001B[0m";
        sb.append(marginStr);
        if (border != Borders.NONE) sb.append(borderColor).append(border.left).append(reset);
        
        sb.append(backgroundColor);
        sb.append(" ".repeat(paddingLeft));
        sb.append(foregroundColor).append(contentLine != null ? contentLine : "").append(reset).append(backgroundColor); // Reset needed inside? Maybe
        
        int currentLen = contentLine != null ? stripAnsi(contentLine).length() : 0;
        int remaining = totalWidth - paddingLeft - paddingRight - currentLen;
        if (remaining > 0) sb.append(" ".repeat(remaining));
        
        sb.append(" ".repeat(paddingRight));
        sb.append(reset);

        if (border != Borders.NONE) sb.append(borderColor).append(border.right).append(reset);
        sb.append("\n");
    }

    private String stripAnsi(String s) {
        if (s == null) return "";
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
