package app.jongerius.dami.consoleImprovements;

import app.jongerius.dami.consoleImprovements.api.*;
import app.jongerius.dami.consoleImprovements.api.widgets.*;
import app.jongerius.dami.consoleImprovements.api.charm.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Random;

public final class ConsoleImprovements extends JavaPlugin {

    @Override
    public void onEnable() {
        ConsoleTUI tui = new ConsoleTUI();
        StaticUI.setTui(tui); // For prefix-free printing
        tui.setActive(true);
        
        // Phase 1: Fancy Sequence with Charm/LipGloss Style
        String welcomeMsg = LipGloss.style()
            .setContent("Initializing ConsoleImprovements v1.0.0\nSystem Check In Progress...")
            .border(Borders.ROUNDED)
            .borderColor(255, 105, 180) // Hot Pink
            .padding(1, 2)
            .margin(2)
            .foreground(0, 255, 255) // Cyan Text
            .render();
            
        // Use StaticUI to print raw to terminal (bypasses logger prefix)
        StaticUI.printRaw(welcomeMsg);

        StepProgress steps = new StepProgress(Arrays.asList("Kernel", "Modules", "Network", "UI"));
        BarChart resourceChart = new BarChart("Resources");
        resourceChart.setValue("CPU", 12.5);
        resourceChart.setValue("RAM", 45.0);
        resourceChart.setValue("NET", 2.1);

        tui.addWidget(steps);
        tui.addWidget(resourceChart);

        Random random = new Random();

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks++;
                
                // Update StepProgress
                if (ticks == 20) steps.setStep(1);
                if (ticks == 40) steps.setStep(2);
                if (ticks == 60) steps.setStep(3);

                // Update BarChart with random fluctuations
                resourceChart.setValue("CPU", Math.max(5, Math.min(100, (double)resourceChart.render().length() % 20 + 10 + random.nextDouble() * 5)));
                resourceChart.setValue("RAM", 45.0 + random.nextDouble() * 2);
                resourceChart.setValue("NET", 2.0 + random.nextDouble() * 10);

                tui.update();

                if (ticks >= 80) {
                    tui.finish();
                    this.cancel();
                    
                    String readyMsg = LipGloss.style()
                        .setContent("READY\nAll systems operational.")
                        .border(Borders.DOUBLE)
                        .borderColor(0, 255, 0) // Green
                        .padding(1, 4)
                        .margin(2)
                        .foreground(200, 255, 200) // Light Green
                        .render();
                    
                    StaticUI.printRaw(readyMsg);
                    showFinalReport();
                }
            }
        }.runTaskTimer(this, 10L, 2L);
    }

    private void showFinalReport() {
        Prompt.confirm("Would you like to see the final status report?").thenAccept(show -> {
            if (show) {
                Table table = new Table(Arrays.asList("Component", "Status", "Load Time"));
                table.addRow(Arrays.asList("World Engine", "§aREADY", "1.2s"));
                table.addRow(Arrays.asList("Asset Cache", "§aREADY", "0.8s"));
                table.addRow(Arrays.asList("Network", "§6WAITING", "N/A"));
                table.print();
            } else {
                BoxUtils.printBox("COMPLETE", "System initialization finished.", ColorUtils.GREEN);
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
