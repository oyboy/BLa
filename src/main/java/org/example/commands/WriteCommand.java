package org.example.commands;

import org.example.Matrix;
import org.example.Type;
import picocli.CommandLine;

import java.util.*;

@CommandLine.Command(name="write", description = "write(subject, object_name, permission)")
public class WriteCommand extends DefaultFields implements Runnable {
    private final Matrix subjectMatrix;
    private final Matrix objectMatrix;
    private final Matrix accessMatrix;

    public WriteCommand(Matrix subjectMatrix, Matrix objectMatrix, Matrix accessMatrix) {
        this.subjectMatrix = subjectMatrix;
        this.objectMatrix = objectMatrix;
        this.accessMatrix = accessMatrix;
    }

    public WriteCommand() {
        this(new Matrix(Type.SUBJECTS), new Matrix(Type.OBJECTS), new Matrix(Type.ACCESS));
    }

    @Override
    public void run() {
        Map<String, String> subjectLevels = subjectMatrix.readSecrecyLevels();
        Map<String, String> objectLevels = objectMatrix.readSecrecyLevels();
        Map<String, Map<String, List<String>>> rightsMatrix = accessMatrix.readMatrix();

        String subjectLevel = subjectLevels.get(subject);
        String objectLevel = objectLevels.get(object);

        if (subjectLevel == null || objectLevel == null) {
            System.out.println("Уровни секретности не найдены для субъекта или объекта.");
            return;
        }

        List<String> rights = rightsMatrix.getOrDefault(subject, new HashMap<>()).getOrDefault(object, new ArrayList<>());
        if (rights.contains("write")) {
            System.out.println("Запись разрешена. У субъекта уже есть право на запись.");
            return;
        }

        if (compareLevels(subjectLevel, objectLevel) <= 0) {
            System.out.println("Файл успешно записан.");
        } else {
            System.out.println("Недостаточно прав для записи файла.");
            System.out.println("Разрешить принудительную запись с удалением остальных прав? (yes/no)");
            rewritePermissions();
        }
    }
    private void rewritePermissions() {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("Операция отменена.");
            return;
        }

        Map<String, String> subjectLevels = subjectMatrix.readSecrecyLevels();
        Map<String, String> objectLevels = objectMatrix.readSecrecyLevels();
        Map<String, Map<String, List<String>>> access = accessMatrix.readMatrix();

        String objectLevel = objectLevels.get(object);
        if (objectLevel == null) {
            System.err.println("Объект не найден.");
            return;
        }
        subjectLevels.put(subject, objectLevel);
        subjectMatrix.writeSecrecyLevels(subjectLevels);

        // 🔁 Пересчитываем права на все объекты
        Map<String, List<String>> newRights = new HashMap<>();
        for (Map.Entry<String, String> entry : objectLevels.entrySet()) {
            String objName = entry.getKey();
            String objLevel = entry.getValue();
            List<String> rights = determineRights(objectLevel, objLevel);
            if (!rights.isEmpty()) {
                newRights.put(objName, rights);
            }
        }

        access.put(subject, newRights);
        accessMatrix.writeMatrix(access);

        System.out.println("Уровень субъекта понижен до " + objectLevel + ", права пересчитаны.");
    }
    private List<String> determineRights(String subjectLevel, String objectLevel) {
        Map<String, Integer> levelMap = Map.of("LOW", 1, "MEDIUM", 2, "HIGH", 3);
        int sLevel = levelMap.getOrDefault(subjectLevel, 0);
        int oLevel = levelMap.getOrDefault(objectLevel, 0);

        if (sLevel == oLevel) return Arrays.asList("read", "write");
        else if (sLevel > oLevel) return Collections.singletonList("read");
        else return Collections.singletonList("write"); // ← как раз тот случай!
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