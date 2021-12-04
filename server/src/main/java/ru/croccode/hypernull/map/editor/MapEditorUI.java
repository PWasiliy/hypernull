package ru.croccode.hypernull.map.editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapEditorUI {
    private static final String COMMAND_INFO_FORMAT = "\"%s\" - %s\n";
    public static void main(String[] args) {
        Map<String, StringCommand> commands = new HashMap<>();
        commands.put("open", new StringCommand("открыть карту из файла .map", null));
        commands.put("new clear", new StringCommand("создать новую пустую карту", null));
        commands.put("new random", new StringCommand("создать новую случайную карту", null));
        commands.put("save file", new StringCommand("сохранить карту в файл", null));

        System.out.printf(COMMAND_INFO_FORMAT, "help", "список команд");
        System.out.printf(COMMAND_INFO_FORMAT, "end", "завершить работу");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] strings = scanner.nextLine().split(" ");
            if (strings.length == 0 || strings[0].trim().length() == 0)
                continue;

            if (commands.containsKey(strings[0]))
                    commands.get(strings[0]).action(Arrays.copyOfRange(strings, 1, strings.length));
            else if (strings.length == 1)
                if (strings[0].equalsIgnoreCase("help")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    commands.forEach((String key, StringCommand command) -> stringBuilder.append(String.format(COMMAND_INFO_FORMAT, key, command.description())));
                    System.out.println(stringBuilder);
                } else if (strings[0].equals("end"))
                    break;
            else
                System.out.println("Неизвестная команда. Пожалуйста, попробуйте еще раз.");
        }
    }
}
