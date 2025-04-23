package org.example.commands;

import org.example.Matrix;
import org.example.Type;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

@CommandLine.Command(name="read", description = "read(subject, object_name, permission)")
public class ReadCommand extends DefaultFields implements Runnable {
    private final Matrix accessMatrix;
    private final Matrix objectMatrix;

    public ReadCommand(Matrix accessMatrix, Matrix objectMatrix) {
        this.accessMatrix = accessMatrix;
        this.objectMatrix = objectMatrix;
    }
    public ReadCommand() {
        this(new Matrix(Type.SUBJECTS), new Matrix(Type.OBJECTS));
    }

    @Override
    public void run() {
        Map<String, String> subjectLevels = accessMatrix.readSecrecyLevels();
        Map<String, String> objectLevels = objectMatrix.readSecrecyLevels();

        String subjectLevel = subjectLevels.get(subject);
        String objectLevel = objectLevels.get(object);

        if (subjectLevel == null || objectLevel == null) {
            System.out.println("Уровни секретности не найдены для субъекта или объекта.");
            return;
        }

        if (compareLevels(subjectLevel, objectLevel) >= 0) {
            System.out.println("Разрешено чтение файла.");
        } else {
            System.out.println("Недостаточно прав для чтения файла.");
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
