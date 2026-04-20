package app.jongerius.dami.consoleImprovements.api;

import net.minecrell.terminalconsole.TerminalConsoleAppender;
import org.jline.reader.LineReader;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class Prompt {
    private static final Logger logger = Bukkit.getLogger();

    public static CompletableFuture<String> ask(String question) {
        CompletableFuture<String> future = new CompletableFuture<>();
        LineReader reader = TerminalConsoleAppender.getReader();

        if (reader == null) {
            future.completeExceptionally(new RuntimeException("Console reader is not available"));
            return future;
        }

        // We run the prompt in a separate thread so it doesn't block the server main thread.
        new Thread(() -> {
            try {
                String input = reader.readLine(ColorUtils.color("\r[?] " + question + " ", ColorUtils.CYAN));
                future.complete(input);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }).start();

        return future;
    }

    public static CompletableFuture<Boolean> confirm(String question) {
        return ask(question + " (y/n)").thenApply(input -> 
            input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")
        );
    }
}
