package app.jongerius.dami.consoleImprovements.api;

import net.minecrell.terminalconsole.TerminalConsoleAppender;
import org.jline.utils.AttributedString;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * The core controller for the Terminal User Interface (TUI).
 * <p>
 * This class hooks into the JLine terminal provided by PaperMC/TerminalConsoleAppender
 * to allow dynamic rendering of widgets to the console status line (bottom of the console).
 * It bypasses standard Bukkit logger prefixes so components look clean.
 */
public class ConsoleTUI {
    private Object terminal;
    private Object reader;
    private Object status;
    private Method statusUpdateMethod;
    private final List<Widget> widgets = new CopyOnWriteArrayList<>();
    private boolean active = false;
    private final Logger logger;
    private String lastRenderedAnsi = "";

    /**
     * Constructs a new ConsoleTUI instance.
     * Uses reflection to hook into the TerminalConsoleAppender.
     */
    public ConsoleTUI() {
        this.logger = Bukkit.getLogger();
        try {
            this.terminal = TerminalConsoleAppender.getTerminal();
            
            // Try to get the LineReader for synchronization to avoid IndexOutOfBoundsException in Status.redraw()
            try {
                java.lang.reflect.Field readerField = TerminalConsoleAppender.class.getDeclaredField("reader");
                readerField.setAccessible(true);
                this.reader = readerField.get(null);
            } catch (Exception ignored) {}

            if (terminal != null) {
                try {
                    Method getStatusMethod = terminal.getClass().getMethod("getStatus");
                    this.status = getStatusMethod.invoke(terminal);
                    
                    if (this.status != null) {
                        this.statusUpdateMethod = status.getClass().getMethod("update", List.class);
                        logger.info("[ConsoleTUI] Status line initialized via reflection.");
                    }
                } catch (NoSuchMethodException e) {
                    logger.warning("[ConsoleTUI] getStatus() method not found on terminal class.");
                }
            } else {
                logger.severe("[ConsoleTUI] Terminal is null!");
            }
        } catch (Exception e) {
            logger.warning("[ConsoleTUI] Error during reflection check: " + e.getMessage());
            this.status = null;
        }
    }

    /**
     * Adds a widget to the TUI to be rendered.
     *
     * @param widget The widget to add.
     */
    public void addWidget(Widget widget) {
        widgets.add(widget);
    }

    /**
     * Removes a widget from the TUI so it is no longer rendered.
     *
     * @param widget The widget to remove.
     */
    public void removeWidget(Widget widget) {
        widgets.remove(widget);
    }

    /**
     * Updates and redraws all currently active widgets to the console status line.
     * Should be called frequently (e.g., in a Bukkit runnable) if widgets animate or change.
     */
    public void update() {
        if (!active) return;

        List<AttributedString> lines = new ArrayList<>();
        for (Widget widget : widgets) {
            String rendered = widget.render();
            if (!rendered.isEmpty()) {
                lines.add(AttributedString.fromAnsi(rendered));
            }
        }

        if (status != null && statusUpdateMethod != null) {
            try {
                // Synchronize on the terminal to prevent concurrent access issues with JLine
                Object term = (terminal != null) ? terminal : status;
                synchronized (term) {
                    statusUpdateMethod.invoke(status, lines);
                }
            } catch (Exception e) {
                if (!lines.isEmpty()) {
                    printRaw(lines.get(lines.size() - 1).toAnsi());
                }
            }
        } else if (!lines.isEmpty()) {
            printRaw(lines.get(lines.size() - 1).toAnsi());
        }
    }

    /**
     * Finalizes the TUI session.
     * Prints the current state of all widgets to the main console output area (above the prompt)
     * and deactivates the status line rendering.
     */
    public void finish() {
        if (active) {
            // Print all current lines to the normal terminal
            for (Widget widget : widgets) {
                String rendered = widget.render();
                if (!rendered.isEmpty()) {
                    printRaw(rendered + "\n");
                }
            }
        }
        setActive(false);
    }

    /**
     * Prints raw ANSI to the terminal bypassing the Bukkit logger prefixes.
     * Uses LineReader.printAbove to handle the prompt correctly so it doesn't interrupt typing.
     *
     * @param ansi The ANSI string to print.
     */
    public void printRaw(String ansi) {
        // Try to use LineReader.printAbove to handle the prompt correctly and avoid prefixes
        if (reader != null) {
            try {
                Method printAboveMethod = reader.getClass().getMethod("printAbove", String.class);
                synchronized (reader) {
                    printAboveMethod.invoke(reader, ansi);
                }
                return;
            } catch (Exception ignored) {}
        }

        // Fallback to terminal writer if reader is not available
        if (terminal != null) {
            try {
                java.io.PrintWriter writer = (java.io.PrintWriter) terminal.getClass().getMethod("writer").invoke(terminal);
                synchronized (terminal) {
                    writer.print("\n" + ansi + "\n");
                    writer.flush();
                }
            } catch (Exception e) {
                // Final fallback to System.out.write if reflection fails
                byte[] bytes = ("\n" + ansi + "\n").getBytes(java.nio.charset.StandardCharsets.UTF_8);
                try {
                    System.out.write(bytes);
                    System.out.flush();
                } catch (java.io.IOException ignored) {}
            }
        }
    }

    /**
     * Sets whether the TUI is actively intercepting and drawing to the status line.
     *
     * @param active True to enable TUI rendering, false to disable and clear it.
     */
    public void setActive(boolean active) {
        this.active = active;
        if (!active) {
            if (status != null && statusUpdateMethod != null) {
                try {
                    Object term = (terminal != null) ? terminal : status;
                    synchronized (term) {
                        statusUpdateMethod.invoke(status, Collections.emptyList());
                    }
                } catch (Exception ignored) {}
            }
        }
    }

    /**
     * Represents a graphical component that can be rendered to the console.
     */
    public interface Widget {
        /**
         * Renders the current state of the widget to an ANSI-formatted string.
         *
         * @return The ANSI string representing the widget.
         */
        String render();
    }
}
