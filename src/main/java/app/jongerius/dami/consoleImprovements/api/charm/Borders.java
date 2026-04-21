package app.jongerius.dami.consoleImprovements.api.charm;

/**
 * Defines various border styles for use with LipGloss and other rendering tools.
 */
public enum Borders {
    NONE("", "", "", "", "", "", "", ""),
    NORMAL("┌", "─", "┐", "│", "│", "└", "─", "┘"),
    ROUNDED("╭", "─", "╮", "│", "│", "╰", "─", "╯"),
    THICK("┏", "━", "┓", "┃", "┃", "┗", "━", "┛"),
    DOUBLE("╔", "═", "╗", "║", "║", "╚", "═", "╝");

    public final String topLeft;
    public final String top;
    public final String topRight;
    public final String left;
    public final String right;
    public final String bottomLeft;
    public final String bottom;
    public final String bottomRight;

    Borders(String topLeft, String top, String topRight, String left, String right, String bottomLeft, String bottom, String bottomRight) {
        this.topLeft = topLeft;
        this.top = top;
        this.topRight = topRight;
        this.left = left;
        this.right = right;
        this.bottomLeft = bottomLeft;
        this.bottom = bottom;
        this.bottomRight = bottomRight;
    }
}
