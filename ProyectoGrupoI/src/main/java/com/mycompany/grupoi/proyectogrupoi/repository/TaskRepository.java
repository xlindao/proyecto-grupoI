package com.mycompany.grupoi.proyectogrupoi.repository;

import com.google.gson.reflect.TypeToken;
import com.mycompany.grupoi.proyectogrupoi.model.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepository {
    private final String filePath = "data/tasks.json";
    private final JsonStore store = new JsonStore();
    private final Type listType = new TypeToken<List<Task>>(){}.getType();

    public List<Task> findAll() {
        String json = store.readAll(filePath);
        List<Task> tasks = store.gson().fromJson(json, listType);
        return tasks != null ? tasks : new ArrayList<>();
    }

    public List<Task> findByUser(String userId) {
        return findAll().stream()
                .filter(t -> t.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void saveAll(List<Task> tasks) {
        store.writeAll(filePath, store.gson().toJson(tasks, listType));
    }
}
