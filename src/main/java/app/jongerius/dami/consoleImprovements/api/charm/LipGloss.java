package app.jongerius.dami.consoleImprovements.api.charm;

import java.util.ArrayList;
import java.util.List;

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

    public static LipGloss style() {
        return new LipGloss();
    }

    public LipGloss setContent(String content) {
        this.content = content;
        return this;
    }

    public LipGloss width(int width) {
        this.width = width;
        return this;
    }

    public LipGloss padding(int v, int h) {
        this.paddingTop = v;
        this.paddingBottom = v;
        this.paddingLeft = h;
        this.paddingRight = h;
        return this;
    }

    public LipGloss padding(int top, int right, int bottom, int left) {
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
        this.paddingLeft = left;
        return this;
    }

    public LipGloss margin(int left) {
        this.marginLeft = left;
        return this;
    }

    public LipGloss border(Borders border) {
        this.border = border;
        return this;
    }

    public LipGloss borderColor(String ansi) {
        this.borderColor = ansi;
        return this;
    }

    public LipGloss borderColor(int r, int g, int b) {
        this.borderColor = String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
        return this;
    }

    public LipGloss foreground(int r, int g, int b) {
        this.foregroundColor = String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
        return this;
    }

    public LipGloss background(int r, int g, int b) {
        this.backgroundColor = String.format("\u001B[48;2;%d;%d;%dm", r, g, b);
        return this;
    }

    public String render() {
        if (content == null) return "";

        String[] lines = content.split("\n");
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
        sb.append(foregroundColor).append(contentLine).append(reset).append(backgroundColor); // Reset needed inside? Maybe
        
        int currentLen = stripAnsi(contentLine).length();
        int remaining = totalWidth - paddingLeft - paddingRight - currentLen;
        if (remaining > 0) sb.append(" ".repeat(remaining));
        
        sb.append(" ".repeat(paddingRight));
        sb.append(reset);

        if (border != Borders.NONE) sb.append(borderColor).append(border.right).append(reset);
        sb.append("\n");
    }

    private String stripAnsi(String s) {
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
