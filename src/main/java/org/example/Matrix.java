package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Matrix {
    private static String MATRIX_FILE_PATH = "./system/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Matrix(Type type) {
        switch (type){
            case OBJECTS -> MATRIX_FILE_PATH += "objects.json";
            case SUBJECTS -> MATRIX_FILE_PATH += "subjects.json";
        }
    }

    public void write(Map<String, Map<String, List<String>>> map){
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(MATRIX_FILE_PATH), map);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл matrix.json: " + e.getMessage());
        }
    }
    public Map<String, Map<String, List<String>>> readMatrix() {
        try {
            return objectMapper.readValue(new File(MATRIX_FILE_PATH), new TypeReference<Map<String, Map<String, List<String>>>>() {});
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла matrix.json: " + e.getMessage());
            return null;
        }
    }
}