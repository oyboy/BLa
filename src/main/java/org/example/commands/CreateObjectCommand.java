package org.example.commands;

import org.example.Matrix;
import org.example.Type;
import picocli.CommandLine;

import java.util.Map;

@CommandLine.Command(name="create-object", description = "Создание нового объекта")
public class CreateObjectCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, required = true, description = "Имя объекта")
    String name;

    @CommandLine.Option(names = {"-l", "--level"}, required = true, description = "Уровень секретности (LOW, MEDIUM, HIGH)")
    String level;

    @Override
    public void run() {
        Matrix objectMatrix = new Matrix(Type.OBJECTS);
        Map<String, String> levels = objectMatrix.readSecrecyLevels();

        levels.put(name, level.toUpperCase());

        objectMatrix.writeSecrecyLevels(levels);

        System.out.println("Объект " + name + " создан с уровнем " + level.toUpperCase());
    }
}