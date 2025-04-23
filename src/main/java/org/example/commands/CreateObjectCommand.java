package org.example.commands;

import org.example.Matrix;
import org.example.Type;
import picocli.CommandLine;

import java.util.Map;
import java.util.Set;

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
        Map<String, String> levels = objectMatrix.readSecrecyLevels();

        if (levels.containsKey(name)) {
            System.err.println("Ошибка: Объект с именем '" + name + "' уже существует");
            return;
        }

        levels.put(name, level.toUpperCase());

        objectMatrix.writeSecrecyLevels(levels);

        System.out.println("Объект " + name + " создан с уровнем " + level.toUpperCase());
    }
}