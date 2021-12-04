package ru.croccode.hypernull.map.editor;

@FunctionalInterface
public interface StringCommandAction {
    void execute(String[] args);
}
