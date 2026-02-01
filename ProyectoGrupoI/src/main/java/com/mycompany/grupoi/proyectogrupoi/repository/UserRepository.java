package com.mycompany.grupoi.proyectogrupoi.repository;

import com.google.gson.reflect.TypeToken;
import com.mycompany.grupoi.proyectogrupoi.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final String filePath = "data/users.json";
    private final JsonStore store = new JsonStore();
    private final Type listType = new TypeToken<List<User>>(){}.getType();

    public List<User> findAll() {
        String json = store.readAll(filePath);
        List<User> users = store.gson().fromJson(json, listType);
        return users != null ? users : new ArrayList<>();
    }

    public Optional<User> findByUsername(String username) {
        return findAll().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public User save(User user) {
        List<User> users = findAll();
        users.add(user);
        store.writeAll(filePath, store.gson().toJson(users, listType));
        return user;
    }
}
