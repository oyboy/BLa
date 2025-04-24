package org.example.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@CommandLine.Command(name="init", description = "Инициализация директорий")
public class InitCommand implements Runnable {
    @Override
    public void run() {
        try {
            File dir = new File("./system");
            if (!dir.exists()) dir.mkdir();

            File objectsFile = new File("./system/objects.json");
            File subjectsFile = new File("./system/subjects.json");
            File accessFile = new File("./system/access.json");

            boolean objectsCreated = objectsFile.createNewFile();
            boolean subjectsCreated = subjectsFile.createNewFile();

            ObjectMapper mapper = new ObjectMapper();
            if (objectsCreated) {
                mapper.writeValue(objectsFile, new HashMap<>());
            }
            if (subjectsCreated) {
                mapper.writeValue(subjectsFile, new HashMap<>());
            }
            if (accessFile.createNewFile()) {
                mapper.writeValue(accessFile, new HashMap<>());
            }

            System.out.println("Инициализация завершена.");

        } catch (IOException io) {
            System.err.println("Не удалось создать файлы: " + io.getMessage());
        }
    }
}
