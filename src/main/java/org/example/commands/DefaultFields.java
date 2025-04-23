package org.example.commands;

import picocli.CommandLine;

abstract class DefaultFields {
    @CommandLine.Option(names = {"-s", "--subject"}, required = true, description = "Subject")
    String subject;

    @CommandLine.Option(names = {"-o", "--object"}, required = true, description = "Object")
    String object;

    @CommandLine.Option(names = {"-p", "--permission"}, required = true, description = "Permission")
    String permission;
}