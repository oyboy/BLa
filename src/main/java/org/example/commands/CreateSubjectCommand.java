package org.example.commands;

import org.example.Matrix;
import org.example.Type;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CommandLine.Command(name="create-subject", description = "Создание нового субъекта")
public class CreateSubjectCommand implements Runnable {
    private static final Set<String> VALID_LEVELS = Set.of("LOW", "MEDIUM", "HIGH");

    @CommandLine.Option(names = {"-n", "--name"}, required = true, description = "Имя субъекта")
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

        Matrix subjectMatrix = new Matrix(Type.SUBJECTS);
        Map<String, String> levels = subjectMatrix.readSecrecyLevels();

        if (levels.containsKey(name)) {
            System.err.println("Ошибка: Субъект с именем '" + name + "' уже существует");
            return;
        }

        levels.put(name, level.toUpperCase());

        subjectMatrix.writeSecrecyLevels(levels);

        System.out.println("Субъект " + name + " создан с уровнем " + level.toUpperCase());
    }
}