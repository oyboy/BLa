package org.example.commands;

import picocli.CommandLine;

@CommandLine.Command(name="write", description = "write(subject, object_name, permission)")
public class WriteCommand extends DefaultFields implements Runnable {
    @Override
    public void run() {

    }
}