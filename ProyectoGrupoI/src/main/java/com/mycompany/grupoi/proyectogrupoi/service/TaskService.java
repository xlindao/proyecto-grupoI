package com.mycompany.grupoi.proyectogrupoi.service;

import com.mycompany.grupoi.proyectogrupoi.model.Priority;
import com.mycompany.grupoi.proyectogrupoi.model.Task;
import com.mycompany.grupoi.proyectogrupoi.repository.TaskRepository;
import com.mycompany.grupoi.proyectogrupoi.util.ValidationUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class TaskService {
    private final TaskRepository repo = new TaskRepository();

    public Task createTask(String userId, String title, String description, Priority priority, LocalDate dueDate) {
        ValidationUtil.requireNotBlank(userId, "userId");
        ValidationUtil.requireNotBlank(title, "Título");
        ValidationUtil.requireNotBlank(description, "Descripción");

        Task task = new Task(userId, title.trim(), description.trim(), priority, dueDate);
        List<Task> all = repo.findAll();
        all.add(task);
        repo.saveAll(all);
        return task;
    }

    public List<Task> listUserTasks(String userId) {
        List<Task> tasks = repo.findByUser(userId);
        tasks.sort(Comparator
                .comparing(Task::isCompleted)
                .thenComparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder())));
        return tasks;
    }

    public void completeTask(String userId, String taskId) {
        List<Task> all = repo.findAll();
        Task task = all.stream()
                .filter(t -> t.getUserId().equals(userId) && t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada."));

        task.setCompleted(true);
        repo.saveAll(all);
    }

    public void deleteTask(String userId, String taskId) {
        List<Task> all = repo.findAll();
        boolean removed = all.removeIf(t -> t.getUserId().equals(userId) && t.getId().equals(taskId));
        if (!removed) throw new IllegalArgumentException("Tarea no encontrada.");
        repo.saveAll(all);
    }
}
