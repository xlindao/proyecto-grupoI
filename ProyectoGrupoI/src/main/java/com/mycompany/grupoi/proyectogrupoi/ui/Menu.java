package com.mycompany.grupoi.proyectogrupoi.ui;

import com.mycompany.grupoi.proyectogrupoi.model.Priority;
import com.mycompany.grupoi.proyectogrupoi.model.Task;
import com.mycompany.grupoi.proyectogrupoi.model.User;
import com.mycompany.grupoi.proyectogrupoi.service.AuthService;
import com.mycompany.grupoi.proyectogrupoi.service.TaskService;
import com.mycompany.grupoi.proyectogrupoi.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final AuthService authService = new AuthService();
    private final TaskService taskService = new TaskService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n=== TaskManager Grupo I ===");
            System.out.println("1) Registrarse");
            System.out.println("2) Iniciar sesión");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String opt = scanner.nextLine();

            try {
                switch (opt) {
                    case "1" -> registerFlow();
                    case "2" -> loginFlow();
                    case "0" -> { System.out.println("Adiós."); return; }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void registerFlow() {
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();
        User u = authService.register(user, pass);
        System.out.println("Registrado con éxito: " + u.getUsername());
    }

    private void loginFlow() {
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        User u = authService.login(user, pass);
        System.out.println("Bienvenido, " + u.getUsername());
        userMenu(u);
    }

    private void userMenu(User user) {
        while (true) {
            System.out.println("\n--- Menú de Usuario (" + user.getUsername() + ") ---");
            System.out.println("1) Crear tarea");
            System.out.println("2) Listar tareas");
            System.out.println("3) Completar tarea");
            System.out.println("4) Eliminar tarea");
            System.out.println("0) Cerrar sesión");
            System.out.print("Opción: ");
            String opt = scanner.nextLine();

            try {
                switch (opt) {
                    case "1" -> createTaskFlow(user);
                    case "2" -> listTasksFlow(user);
                    case "3" -> completeTaskFlow(user);
                    case "4" -> deleteTaskFlow(user);
                    case "0" -> { return; }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void createTaskFlow(User user) {
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Descripción: ");
        String desc = scanner.nextLine();

        System.out.print("Prioridad (BAJA/MEDIA/ALTA): ");
        Priority pr = Priority.valueOf(scanner.nextLine().trim().toUpperCase());

        System.out.print("Fecha límite (YYYY-MM-DD) o vacío: ");
        String dateInput = scanner.nextLine();
        LocalDate due = dateInput.isBlank() ? null : ValidationUtil.parseDate(dateInput);

        Task t = taskService.createTask(user.getId(), title, desc, pr, due);
        System.out.println("Tarea creada con ID: " + t.getId());
    }

    private void listTasksFlow(User user) {
        List<Task> tasks = taskService.listUserTasks(user.getId());
        if (tasks.isEmpty()) {
            System.out.println("No hay tareas.");
            return;
        }

        System.out.println("\nID | Estado | Prioridad | Fecha | Título");
        for (Task t : tasks) {
            String estado = t.isCompleted() ? "OK" : "PEND";
            System.out.printf("%s | %s | %s | %s | %s%n",
                    t.getId(),
                    estado,
                    t.getPriority(),
                    (t.getDueDate() == null ? "-" : t.getDueDate()),
                    t.getTitle());
        }
    }

    private void completeTaskFlow(User user) {
        System.out.print("ID de la tarea: ");
        String id = scanner.nextLine();
        taskService.completeTask(user.getId(), id.trim());
        System.out.println("Tarea completada.");
    }

    private void deleteTaskFlow(User user) {
        System.out.print("ID de la tarea: ");
        String id = scanner.nextLine();
        taskService.deleteTask(user.getId(), id.trim());
        System.out.println("Tarea eliminada.");
    }
}
