package org.example.commands;

import picocli.CommandLine;

@CommandLine.Command(name="read", description = "read(subject, object_name, permission)")
public class ReadCommand extends DefaultFields implements Runnable {
    @Override
    public void run() {

    }
}
