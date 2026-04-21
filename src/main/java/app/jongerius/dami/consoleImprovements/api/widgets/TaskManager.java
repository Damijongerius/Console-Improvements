package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

import java.util.ArrayList;
import java.util.List;

/**
 * A widget that manages and displays the progress of multiple concurrent tasks.
 */
public class TaskManager implements ConsoleTUI.Widget {
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Represents a single task with its own progress and styling.
     */
    public static class Task {
        public String name;
        public int progress;
        public int total;
        public String color;

        /**
         * Constructs a new Task.
         *
         * @param name The name of the task.
         * @param total The total target value for 100% completion.
         * @param color The ANSI color code for the task name.
         */
        public Task(String name, int total, String color) {
            this.name = name;
            this.total = total;
            this.color = color;
            this.progress = 0;
        }
    }

    /**
     * Adds a task to be tracked and rendered.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from tracking.
     *
     * @param task The task to remove.
     */
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
