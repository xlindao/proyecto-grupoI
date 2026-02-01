package com.mycompany.grupoi.proyectogrupoi.repository;

import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class JsonStore {

    private final Gson gson;

    public JsonStore() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.toString());
                    }
                })
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                        return LocalDate.parse(json.getAsString());
                    }
                })
                .create();
    }

    public Gson gson() { return gson; }

    public void ensureFile(String filePath) {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path.getParent())) Files.createDirectories(path.getParent());
            if (!Files.exists(path)) Files.writeString(path, "[]");
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear archivo: " + filePath, e);
        }
    }

    public String readAll(String filePath) {
        try {
            ensureFile(filePath);
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer: " + filePath, e);
        }
    }

    public void writeAll(String filePath, String content) {
        try {
            ensureFile(filePath);
            Files.writeString(Path.of(filePath), content);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo escribir: " + filePath, e);
        }
    }
}
