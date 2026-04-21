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
    private int marginTop = 0;
    private int marginRight = 0;
    private int marginBottom = 0;
    private int marginLeft = 0;
    private Borders border = Borders.NONE;
    private String borderColor = "\u001B[37m"; // White default
    private String foregroundColor = "";
    private String backgroundColor = "";
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;
    private boolean strikethrough = false;
    private Align alignment = Align.LEFT;

    /**
     * Alignment options for text content.
     */
    public enum Align {
        LEFT, CENTER, RIGHT
    }

    /**
     * Starts a new styling builder.
     *
     * @return A new LipGloss builder instance.
     */
    public static LipGloss style() {
        return new LipGloss();
    }

    /**
     * Sets the text alignment.
     *
     * @param alignment The alignment (LEFT, CENTER, RIGHT).
     * @return The builder instance.
     */
    public LipGloss align(Align alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * Makes the text bold.
     *
     * @param bold True to enable, false to disable.
     * @return The builder instance.
     */
    public LipGloss bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    /**
     * Makes the text italic.
     *
     * @param italic True to enable, false to disable.
     * @return The builder instance.
     */
    public LipGloss italic(boolean italic) {
        this.italic = italic;
        return this;
    }

    /**
     * Underlines the text.
     *
     * @param underline True to enable, false to disable.
     * @return The builder instance.
     */
    public LipGloss underline(boolean underline) {
        this.underline = underline;
        return this;
    }

    /**
     * Adds a strikethrough to the text.
     *
     * @param strikethrough True to enable, false to disable.
     * @return The builder instance.
     */
    public LipGloss strikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
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
     * Sets uniform vertical and horizontal margin (spacing outside the border).
     *
     * @param v Vertical margin (top and bottom).
     * @param h Horizontal margin (left and right).
     * @return The builder instance.
     */
    public LipGloss margin(int v, int h) {
        this.marginTop = v;
        this.marginBottom = v;
        this.marginLeft = h;
        this.marginRight = h;
        return this;
    }

    /**
     * Sets specific margin for each side.
     *
     * @param top Top margin.
     * @param right Right margin.
     * @param bottom Bottom margin.
     * @param left Left margin.
     * @return The builder instance.
     */
    public LipGloss margin(int top, int right, int bottom, int left) {
        this.marginTop = top;
        this.marginRight = right;
        this.marginBottom = bottom;
        this.marginLeft = left;
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

        String leftMarginStr = " ".repeat(marginLeft);
        String rightMarginStr = " ".repeat(marginRight);
        String reset = "\u001B[0m";

        // Top Margin
        int fullWidth = marginLeft + totalWidth + (border != Borders.NONE ? 2 : 0) + marginRight;
        for (int i = 0; i < marginTop; i++) {
            sb.append(" ".repeat(fullWidth)).append("\n");
        }

        // Top Border
        if (border != Borders.NONE) {
            sb.append(leftMarginStr)
              .append(borderColor).append(border.topLeft)
              .append(border.top.repeat(totalWidth))
              .append(border.topRight).append(reset)
              .append(rightMarginStr)
              .append("\n");
        }

        // Top Padding
        for (int i = 0; i < paddingTop; i++) {
            renderLine(sb, leftMarginStr, rightMarginStr, "", totalWidth);
        }

        // Content
        for (String line : lines) {
            renderLine(sb, leftMarginStr, rightMarginStr, line, totalWidth);
        }

        // Bottom Padding
        for (int i = 0; i < paddingBottom; i++) {
            renderLine(sb, leftMarginStr, rightMarginStr, "", totalWidth);
        }

        // Bottom Border
        if (border != Borders.NONE) {
            sb.append(leftMarginStr)
              .append(borderColor).append(border.bottomLeft)
              .append(border.bottom.repeat(totalWidth))
              .append(border.bottomRight).append(reset)
              .append(rightMarginStr);
              // We omit the final newline from the border itself to make inline joining easier
        }

        // If no bottom border, but we have bottom margin, we need to handle that.
        // We handle bottom margin by appending newlines.
        for (int i = 0; i < marginBottom; i++) {
            if (border != Borders.NONE || i > 0) sb.append("\n");
            sb.append(" ".repeat(fullWidth));
            if (i < marginBottom - 1 && border == Borders.NONE) sb.append("\n");
        }

        return sb.toString();
    }

    private void renderLine(StringBuilder sb, String leftMarginStr, String rightMarginStr, String contentLine, int totalWidth) {
        String reset = "\u001B[0m";
        sb.append(leftMarginStr);
        if (border != Borders.NONE) sb.append(borderColor).append(border.left).append(reset);
        
        sb.append(backgroundColor);
        sb.append(" ".repeat(paddingLeft));
        
        // Add typography formatting
        StringBuilder styling = new StringBuilder();
        styling.append(foregroundColor);
        if (bold) styling.append("\u001B[1m");
        if (italic) styling.append("\u001B[3m");
        if (underline) styling.append("\u001B[4m");
        if (strikethrough) styling.append("\u001B[9m");

        String safeContent = contentLine != null ? contentLine : "";
        int currentLen = stripAnsi(safeContent).length();
        int availableSpace = totalWidth - paddingLeft - paddingRight;
        int remaining = availableSpace - currentLen;

        if (remaining < 0) remaining = 0; // Prevent negative repeats if content exceeds width somehow

        // Handle Alignment
        String leftPad = "";
        String rightPad = "";

        if (alignment == Align.LEFT) {
            rightPad = " ".repeat(remaining);
        } else if (alignment == Align.RIGHT) {
            leftPad = " ".repeat(remaining);
        } else if (alignment == Align.CENTER) {
            int leftLen = remaining / 2;
            int rightLen = remaining - leftLen;
            leftPad = " ".repeat(leftLen);
            rightPad = " ".repeat(rightLen);
        }

        sb.append(leftPad);
        sb.append(styling).append(safeContent).append(reset).append(backgroundColor);
        sb.append(rightPad);
        
        sb.append(" ".repeat(paddingRight));
        sb.append(reset);

        if (border != Borders.NONE) sb.append(borderColor).append(border.right).append(reset);
        sb.append(rightMarginStr);
        sb.append("\n");
    }

    private static String stripAnsi(String s) {
        if (s == null) return "";
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    /**
     * Joins multiple rendered text blocks horizontally.
     *
     * @param blocks The rendered ANSI strings to join.
     * @return A single horizontally combined ANSI string.
     */
    public static String joinHorizontal(String... blocks) {
        return joinHorizontal(0, blocks);
    }

    /**
     * Joins multiple rendered text blocks horizontally with a specific spacing.
     *
     * @param spacing The number of spaces to insert between blocks.
     * @param blocks The rendered ANSI strings to join.
     * @return A single horizontally combined ANSI string.
     */
    public static String joinHorizontal(int spacing, String... blocks) {
        if (blocks == null || blocks.length == 0) return "";

        List<String[]> blockLines = new ArrayList<>();
        int maxLines = 0;

        for (String block : blocks) {
            String[] lines = block != null ? block.split("\n", -1) : new String[]{""};
            blockLines.add(lines);
            maxLines = Math.max(maxLines, lines.length);
        }

        StringBuilder result = new StringBuilder();
        String spacingStr = " ".repeat(Math.max(0, spacing));

        for (int lineIdx = 0; lineIdx < maxLines; lineIdx++) {
            for (int blockIdx = 0; blockIdx < blockLines.size(); blockIdx++) {
                String[] lines = blockLines.get(blockIdx);
                String lineContent = lineIdx < lines.length ? lines[lineIdx] : "";

                result.append(lineContent);

                if (blockIdx < blockLines.size() - 1) {
                    // Calculate padding needed to match the max width of the current block
                    int maxBlockWidth = 0;
                    for (String l : lines) {
                        maxBlockWidth = Math.max(maxBlockWidth, stripAnsi(l).length());
                    }

                    int currentLen = stripAnsi(lineContent).length();
                    int paddingNeeded = maxBlockWidth - currentLen;
                    if (paddingNeeded > 0) result.append(" ".repeat(paddingNeeded));

                    result.append(spacingStr);
                }
            }
            if (lineIdx < maxLines - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }

    /**
     * Joins multiple rendered text blocks vertically.
     *
     * @param blocks The rendered ANSI strings to join.
     * @return A single vertically combined ANSI string.
     */
    public static String joinVertical(String... blocks) {
        if (blocks == null || blocks.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.length; i++) {
            sb.append(blocks[i]);
            if (i < blocks.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
