package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;
import java.util.List;

public class StepProgress implements ConsoleTUI.Widget {
    private final List<String> steps;
    private int currentStep;

    public StepProgress(List<String> steps) {
        this.steps = steps;
        this.currentStep = 0;
    }

    public void setStep(int step) {
        this.currentStep = Math.max(0, Math.min(step, steps.size() - 1));
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < steps.size(); i++) {
            if (i < currentStep) {
                // Done
                sb.append("\u001B[32m✔ ").append(steps.get(i)).append("\u001B[0m");
            } else if (i == currentStep) {
                // Current
                sb.append("\u001B[36;1m▶ ").append(steps.get(i)).append("\u001B[0m");
            } else {
                // Future
                sb.append("\u001B[37m○ ").append(steps.get(i)).append("\u001B[0m");
            }

            if (i < steps.size() - 1) {
                sb.append(" \u001B[37m→\u001B[0m ");
            }
        }
        return sb.toString();
    }
}
