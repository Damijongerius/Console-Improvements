# ConsoleImprovements

`ConsoleImprovements` is a Java library/plugin for Bukkit (PaperMC) that provides advanced Terminal User Interface (TUI) features. It bypasses the standard logger prefixes to render beautiful, dynamic components directly in the server console, bringing a modern CLI experience to your Minecraft server administration.

## Features

- **Rich Widgets**: Render interactive or updating components like Bar Charts, Sparklines, Task Managers, Spinners, Progress Bars, Step Progress, and Tables.
- **Dynamic Styling**: Built-in support for ANSI colors and gradients.
- **LipGloss Styling**: Advanced box rendering, margins, paddings, and borders inspired by Charm's LipGloss.
- **Interactive Prompts**: Easily ask for user input or confirmation asynchronously without blocking the main server thread.
- **Live Updating**: Update components dynamically on a timer and redraw them to the terminal.

## Initialization

Before using the advanced TUI features, you need to initialize `ConsoleTUI` and set it as the static TUI instance so other utilities can access it.

```java
import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;
import app.jongerius.dami.consoleImprovements.api.StaticUI;

// Initialize the TUI
ConsoleTUI tui = new ConsoleTUI();
StaticUI.setTui(tui);

// Activate it to start rendering widgets
tui.setActive(true);
```

## Using Widgets

You can add widgets to the TUI instance. Once added, you can update them dynamically, and call `tui.update()` to refresh the console view.

```java
import app.jongerius.dami.consoleImprovements.api.widgets.ProgressBar;

ProgressBar progressBar = new ProgressBar("Downloading Map", 100);
tui.addWidget(progressBar);

// Simulate progress
progressBar.increment(10);
tui.update();
```

When you are finished displaying live updates, make sure to finish the session:

```java
tui.finish();
```

## LipGloss Styling

You can create beautifully formatted static boxes and text blocks using the `LipGloss` API.

```java
import app.jongerius.dami.consoleImprovements.api.charm.LipGloss;
import app.jongerius.dami.consoleImprovements.api.charm.Borders;

String message = LipGloss.style()
    .setContent("System Initialization Complete")
    .border(Borders.ROUNDED)
    .borderColor(0, 255, 0) // Green
    .padding(1, 2)
    .render();

// Print safely bypassing normal logger prefixes
StaticUI.printRaw(message);
```

## Prompts & User Input

Ask the server administrator for input asynchronously:

```java
import app.jongerius.dami.consoleImprovements.api.Prompt;

Prompt.ask("What is your name?").thenAccept(name -> {
    StaticUI.printRaw("Hello, " + name + "!");
});

Prompt.confirm("Do you want to proceed?").thenAccept(proceed -> {
    if (proceed) {
        // Continue...
    }
});
```

## Available Widgets

- **`BarChart`**: Display bar charts with double values.
- **`Sparkline`**: Show a mini-chart over time.
- **`TaskManager`**: Track progress of multiple sub-tasks.
- **`Spinner`**: Show an animated loading spinner.
- **`StepProgress`**: Visualize a multi-step process.
- **`ProgressBar`**: A standard loading bar.
- **`Table`**: Display data in a structured, bordered table layout.
