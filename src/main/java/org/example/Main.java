package org.example;

import org.example.commands.*;
import picocli.CommandLine;

import java.util.Scanner;

@CommandLine.Command(name = "bla", description = "Model B-La", subcommands = {
        WriteCommand.class,
        ReadCommand.class,
        CreateObjectCommand.class,
        CreateSubjectCommand.class,
        InitCommand.class
})
public class Main implements Runnable {
    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Если это первый запуск программы, то необходимо выполнить команду init с соотвествующими аргументами");
        System.out.println(" Для выхода введите exit.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из программы.");
                break;
            }

            String[] commandArgs = input.split(" ");
            new CommandLine(new Main()).execute(commandArgs);
        }

        scanner.close();
    }
}