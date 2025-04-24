package org.example.commands;

import org.example.Matrix;
import org.example.Type;
import picocli.CommandLine;

import java.util.*;

@CommandLine.Command(name="create-object", description = "Создание нового объекта")
public class CreateObjectCommand implements Runnable {
    private static final Set<String> VALID_LEVELS = Set.of("LOW", "MEDIUM", "HIGH");

    @CommandLine.Option(names = {"-n", "--name"}, required = true, description = "Имя объекта")
    String name;

    @CommandLine.Option(names = {"-l", "--level"}, required = true, description = "Уровень секретности (LOW, MEDIUM, HIGH)")
    String level;

    @Override
    public void run() {
        String upperLevel = level.toUpperCase();
        if (!VALID_LEVELS.contains(upperLevel)) {
            System.err.println("Ошибка: Недопустимый уровень секретности. Допустимые значения: LOW, MEDIUM, HIGH");
            return;
        }

        Matrix objectMatrix = new Matrix(Type.OBJECTS);
        Map<String, String> objectLevels = objectMatrix.readSecrecyLevels();

        if (objectLevels.containsKey(name)) {
            System.err.println("Ошибка: Объект с именем '" + name + "' уже существует");
            return;
        }

        objectLevels.put(name, upperLevel);
        objectMatrix.writeSecrecyLevels(objectLevels);

        Matrix subjectMatrix = new Matrix(Type.SUBJECTS);
        Map<String, String> subjectLevels = subjectMatrix.readSecrecyLevels();

        Matrix accessMatrix = new Matrix(Type.ACCESS);
        Map<String, Map<String, List<String>>> access = accessMatrix.readMatrix();

        for (Map.Entry<String, String> subjectEntry : subjectLevels.entrySet()) {
            String subject = subjectEntry.getKey();
            String subjectLevel = subjectEntry.getValue();
            /*В зависимости от уровня секретности в матрицу доступа записываются нужные права*/
            List<String> rights = determineRights(subjectLevel, upperLevel);
            access.computeIfAbsent(subject, k -> new HashMap<>()).put(name, rights);
        }

        accessMatrix.writeMatrix(access);
        System.out.println("Объект " + name + " создан с уровнем " + upperLevel);
    }

    private List<String> determineRights(String subjectLevel, String objectLevel) {
        Map<String, Integer> levelMap = Map.of("LOW", 1, "MEDIUM", 2, "HIGH", 3);
        int sLevel = levelMap.getOrDefault(subjectLevel, 0);
        int oLevel = levelMap.getOrDefault(objectLevel, 0);

        if (sLevel == oLevel) return Arrays.asList("read", "write");
        else if (sLevel > oLevel) return Collections.singletonList("read");
        else return Collections.singletonList("write");
    }
}