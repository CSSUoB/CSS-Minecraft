package com.cssbham.cssminecraft.common.command;

public final class CommandContext {

    private final String label;
    private final String[] args;

    public CommandContext(String label, String[] args) {
        this.label = label;
        this.args = args;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }
}
