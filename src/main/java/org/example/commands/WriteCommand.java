package org.example.commands;

import org.example.Matrix;
import org.example.Type;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

@CommandLine.Command(name="write", description = "write(subject, object_name, permission)")
public class WriteCommand extends DefaultFields implements Runnable {
    @Override
    public void run() {
        Matrix accessMatrix = new Matrix(Type.SUBJECTS);
        Matrix objectMatrix = new Matrix(Type.OBJECTS);

        Map<String, String> subjectLevels = accessMatrix.readSecrecyLevels();
        Map<String, String> objectLevels = objectMatrix.readSecrecyLevels();

        String subjectLevel = subjectLevels.get(subject);
        String objectLevel = objectLevels.get(object);

        if (subjectLevel == null || objectLevel == null) {
            System.out.println("Уровни секретности не найдены для субъекта или объекта.");
            return;
        }

        if (compareLevels(subjectLevel, objectLevel) <= 0) {
            System.out.println("Файл успешно записан.");
        } else {
            System.out.println("Недостаточно прав для записи файла.");
        }
    }
    private int compareLevels(String subjectLevel, String objectLevel) {
        Map<String, Integer> levelMap = new HashMap<>();
        levelMap.put("LOW", 1);
        levelMap.put("MEDIUM", 2);
        levelMap.put("HIGH", 3);

        int subjectRank = levelMap.getOrDefault(subjectLevel, 0);
        int objectRank = levelMap.getOrDefault(objectLevel, 0);

        return Integer.compare(subjectRank, objectRank);
    }

}