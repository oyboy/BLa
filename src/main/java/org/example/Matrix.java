package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matrix {
    private String MATRIX_FILE_PATH = "./system/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Matrix(Type type) {
        switch (type){
            case OBJECTS -> MATRIX_FILE_PATH += "objects.json";
            case SUBJECTS -> MATRIX_FILE_PATH += "subjects.json";
            case ACCESS -> MATRIX_FILE_PATH += "access.json";
        }
    }
    public Map<String, String> readSecrecyLevels() {
        try {
            return objectMapper.readValue(new File(MATRIX_FILE_PATH), new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            System.err.println("Ошибка при чтении уровней секретности: " + e.getMessage());
            return new HashMap<>();
        }
    }
    public void writeSecrecyLevels(Map<String, String> levels) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(MATRIX_FILE_PATH), levels);
        } catch (IOException e) {
            System.err.println("Ошибка при записи уровней секретности: " + e.getMessage());
        }
    }
    public Map<String, Map<String, List<String>>> readMatrix() {
        try {
            return objectMapper.readValue(new File(MATRIX_FILE_PATH), new TypeReference<>() {});
        } catch (IOException e) {
            System.err.println("Ошибка при чтении матрицы прав: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public void writeMatrix(Map<String, Map<String, List<String>>> matrix) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(MATRIX_FILE_PATH), matrix);
        } catch (IOException e) {
            System.err.println("Ошибка при записи матрицы прав: " + e.getMessage());
        }
    }
}