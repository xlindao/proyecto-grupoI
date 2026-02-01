package com.mycompany.grupoi.proyectogrupoi.service;

import com.mycompany.grupoi.proyectogrupoi.model.User;
import com.mycompany.grupoi.proyectogrupoi.repository.UserRepository;
import com.mycompany.grupoi.proyectogrupoi.util.PasswordUtil;
import com.mycompany.grupoi.proyectogrupoi.util.ValidationUtil;

public class AuthService {
    private final UserRepository repo = new UserRepository();

    public User register(String username, String password) {
        ValidationUtil.requireNotBlank(username, "Usuario");
        ValidationUtil.requireNotBlank(password, "Contraseña");

        repo.findByUsername(username).ifPresent(u -> {
            throw new IllegalArgumentException("El usuario ya existe.");
        });

        String hash = PasswordUtil.sha256(password);
        return repo.save(new User(username.trim(), hash));
    }

    public User login(String username, String password) {
        ValidationUtil.requireNotBlank(username, "Usuario");
        ValidationUtil.requireNotBlank(password, "Contraseña");

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        String hash = PasswordUtil.sha256(password);
        if (!user.getPasswordHash().equals(hash)) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }
        return user;
    }
}
