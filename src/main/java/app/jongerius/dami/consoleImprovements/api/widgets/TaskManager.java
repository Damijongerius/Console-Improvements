package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

import java.util.ArrayList;
import java.util.List;

public class TaskManager implements ConsoleTUI.Widget {
    private final List<Task> tasks = new ArrayList<>();

    public static class Task {
        public String name;
        public int progress;
        public int total;
        public String color;

        public Task(String name, int total, String color) {
            this.name = name;
            this.total = total;
            this.color = color;
            this.progress = 0;
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public String render() {
        if (tasks.isEmpty()) return "";
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            int percent = (int) (((double) task.progress / task.total) * 100);
            
            sb.append(task.color).append(task.name).append("\u001B[0m: ")
              .append(percent).append("%");
            
            if (i < tasks.size() - 1) {
                sb.append(" \u001B[37m|\u001B[0m ");
            }
        }
        return sb.toString();
    }
}
