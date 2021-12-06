package ru.croccode.hypernull.map.editor;

public class StringCommand {
    private final String description;
    private final StringCommandAction action;

    public StringCommand(String description, StringCommandAction action) {
        this.description = description;
        this.action = action;
    }
    public String description() { return this.description; }
    public void execute(String[] args) {this.action.execute(args); }
}
