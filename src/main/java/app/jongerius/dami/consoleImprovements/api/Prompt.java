package app.jongerius.dami.consoleImprovements.api;

import net.minecrell.terminalconsole.TerminalConsoleAppender;
import org.jline.reader.LineReader;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

/**
 * Utility class for asking the user questions and receiving input
 * directly from the server console asynchronously.
 */
public class Prompt {
    private static final Logger logger = Bukkit.getLogger();

    /**
     * Asks the user a question in the console and waits for them to type a response.
     *
     * @param question The question to display to the user.
     * @return A CompletableFuture that completes with the user's typed string response.
     */
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

    /**
     * Asks the user a yes/no confirmation question in the console.
     *
     * @param question The question to ask.
     * @return A CompletableFuture that completes with true if the user types 'y' or 'yes'.
     */
    public static CompletableFuture<Boolean> confirm(String question) {
        return ask(question + " (y/n)").thenApply(input -> 
            input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")
        );
    }

    /**
     * Asks the user to choose one option from a list.
     *
     * @param question The question to display before the options.
     * @param options The list of valid options.
     * @return A CompletableFuture that completes with the exact selected option string.
     */
    public static CompletableFuture<String> choice(String question, java.util.List<String> options) {
        CompletableFuture<String> future = new CompletableFuture<>();
        if (options == null || options.isEmpty()) {
            future.completeExceptionally(new IllegalArgumentException("Options list cannot be empty"));
            return future;
        }

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("\r[?] ").append(question).append("\n");
        for (int i = 0; i < options.size(); i++) {
            promptBuilder.append(String.format("  %d) %s\n", i + 1, options.get(i)));
        }
        promptBuilder.append("Select an option [1-").append(options.size()).append("]: ");

        String fullPrompt = ColorUtils.color(promptBuilder.toString(), ColorUtils.CYAN);
        LineReader reader = TerminalConsoleAppender.getReader();

        if (reader == null) {
            future.completeExceptionally(new RuntimeException("Console reader is not available"));
            return future;
        }

        new Thread(() -> {
            boolean valid = false;
            while (!valid) {
                try {
                    String input = reader.readLine(fullPrompt).trim();
                    try {
                        int choiceIndex = Integer.parseInt(input) - 1;
                        if (choiceIndex >= 0 && choiceIndex < options.size()) {
                            future.complete(options.get(choiceIndex));
                            valid = true;
                        } else {
                            StaticUI.printRaw(ColorUtils.color("Invalid selection. Please try again.", ColorUtils.RED));
                        }
                    } catch (NumberFormatException e) {
                        StaticUI.printRaw(ColorUtils.color("Please enter a valid number.", ColorUtils.RED));
                    }
                } catch (Exception e) {
                    future.completeExceptionally(e);
                    valid = true;
                }
            }
        }).start();

        return future;
    }

    /**
     * Asks the user to choose multiple options from a list.
     *
     * @param question The question to display before the options.
     * @param options The list of valid options.
     * @return A CompletableFuture that completes with a list of the exact selected option strings.
     */
    public static CompletableFuture<java.util.List<String>> multiChoice(String question, java.util.List<String> options) {
        CompletableFuture<java.util.List<String>> future = new CompletableFuture<>();
        if (options == null || options.isEmpty()) {
            future.completeExceptionally(new IllegalArgumentException("Options list cannot be empty"));
            return future;
        }

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("\r[?] ").append(question).append("\n");
        for (int i = 0; i < options.size(); i++) {
            promptBuilder.append(String.format("  %d) %s\n", i + 1, options.get(i)));
        }
        promptBuilder.append("Select options (comma-separated numbers) [1-").append(options.size()).append("]: ");

        String fullPrompt = ColorUtils.color(promptBuilder.toString(), ColorUtils.CYAN);
        LineReader reader = TerminalConsoleAppender.getReader();

        if (reader == null) {
            future.completeExceptionally(new RuntimeException("Console reader is not available"));
            return future;
        }

        new Thread(() -> {
            boolean valid = false;
            while (!valid) {
                try {
                    String input = reader.readLine(fullPrompt).trim();
                    if (input.isEmpty()) {
                        StaticUI.printRaw(ColorUtils.color("Selection cannot be empty. Please try again.", ColorUtils.RED));
                        continue;
                    }

                    String[] parts = input.split(",");
                    java.util.List<String> selectedOptions = new java.util.ArrayList<>();
                    boolean allValid = true;

                    for (String part : parts) {
                        try {
                            int choiceIndex = Integer.parseInt(part.trim()) - 1;
                            if (choiceIndex >= 0 && choiceIndex < options.size()) {
                                selectedOptions.add(options.get(choiceIndex));
                            } else {
                                StaticUI.printRaw(ColorUtils.color("Invalid selection: " + (choiceIndex + 1) + ". Please try again.", ColorUtils.RED));
                                allValid = false;
                                break;
                            }
                        } catch (NumberFormatException e) {
                            StaticUI.printRaw(ColorUtils.color("Please enter valid comma-separated numbers.", ColorUtils.RED));
                            allValid = false;
                            break;
                        }
                    }

                    if (allValid) {
                        future.complete(selectedOptions);
                        valid = true;
                    }
                } catch (Exception e) {
                    future.completeExceptionally(e);
                    valid = true;
                }
            }
        }).start();

        return future;
    }
}
