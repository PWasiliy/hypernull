package ru.croccode.hypernull.map.editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MapEditorUI {
    private static final String COMMAND_INFO_FORMAT = "\"%s\" - %s\n";

    public static void main(String[] args) {
        MapEditor mapEditor = new MapEditor();

        Map<String, StringCommand> commands = new HashMap<>();
        // общие команды
        commands.put("load-file", new StringCommand("открыть карту из файла .map", mapEditor::open));
        commands.put("new-empty", new StringCommand("создать новую пустую карту", mapEditor::createMap));
        commands.put("new-random", new StringCommand("создать новую случайную карту", mapEditor::createRandomMap));
        commands.put("save-file", new StringCommand("сохранить карту в файл", mapEditor::save));
        commands.put("print", new StringCommand("напечатать карту в консоле", mapEditor::printMap));
        commands.put("print-info", new StringCommand("напечатать информацию о карте", mapEditor::printMapInfo));
        // редактирование точек карты по их координатам
        commands.put("mark-point", new StringCommand("выделить точку на карте", mapEditor::printMarkedPoint));
        commands.put("point", new StringCommand("изменить тип точки на карте", mapEditor::setPointState));
        commands.put("rect", new StringCommand("изменить тип точек в прямоугольнике", mapEditor::setPointsState));
        // редактирование настроек карты
        commands.put("set-size", new StringCommand("изменить размеры карты", mapEditor::setMapSize));
        commands.put("set-radius-view", new StringCommand("изменить радиус видимости", mapEditor::setMapRadiusView));
        commands.put("set-radius-attack", new StringCommand("изменить радиус атаки", mapEditor::setMapRadiusAttack));
        commands.put("set-radius-mining", new StringCommand("изменить радиус майнинга", mapEditor::setMapRadiusMining));

        System.out.printf(COMMAND_INFO_FORMAT, "help", "список команд");
        System.out.printf(COMMAND_INFO_FORMAT, "end", "завершить работу");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] strings = scanner.nextLine().split(" ");
            if (strings.length == 0 || strings[0].trim().length() == 0)
                continue;

            if (commands.containsKey(strings[0]))
                    commands.get(strings[0]).execute(Arrays.copyOfRange(strings, 1, strings.length));
            else if (strings.length == 1) {
                if (strings[0].equalsIgnoreCase("help")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    commands.forEach((String key, StringCommand command) -> stringBuilder.append(String.format(COMMAND_INFO_FORMAT, key, command.description())));
                    System.out.println(stringBuilder);
                } else if (strings[0].equals("end"))
                    break;
            } else
                System.out.println("Неизвестная команда. Пожалуйста, попробуйте еще раз.");
        }
    }
}
