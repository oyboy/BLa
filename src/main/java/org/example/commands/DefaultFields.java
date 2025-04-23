package org.example.commands;

import picocli.CommandLine;

abstract class DefaultFields {
    @CommandLine.Option(names = {"-s", "--subject"}, required = true, description = "Subject")
    public String subject;

    @CommandLine.Option(names = {"-o", "--object"}, required = true, description = "Object")
    public String object;
}