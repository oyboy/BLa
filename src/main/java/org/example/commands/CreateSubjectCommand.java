package org.example.commands;

import org.example.Matrix;
import org.example.Type;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CommandLine.Command(name="create-subject", description = "Создание нового субъекта")
public class CreateSubjectCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, required = true, description = "Имя субъекта")
    String name;

    @CommandLine.Option(names = {"-l", "--level"}, required = true, description = "Уровень секретности (LOW, MEDIUM, HIGH)")
    String level;

    @Override
    public void run() {
        Matrix subjectMatrix = new Matrix(Type.SUBJECTS);
        Map<String, Map<String, List<String>>> matrix = subjectMatrix.readMatrix();
        Map<String, String> levels = subjectMatrix.readSecrecyLevels();

        if (!matrix.containsKey(name)) {
            matrix.put(name, new HashMap<>());
        }

        levels.put(name, level.toUpperCase());

        subjectMatrix.write(matrix);
        subjectMatrix.writeSecrecyLevels(levels);

        System.out.println("Субъект " + name + " создан с уровнем " + level.toUpperCase());
    }
}