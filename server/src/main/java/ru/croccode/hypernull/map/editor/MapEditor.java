package ru.croccode.hypernull.map.editor;

import ru.croccode.hypernull.util.Check;
import ru.croccode.hypernull.util.Question;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class MapEditor {
    private static final String RELOAD_MAP = "%s приведет к потере несохраненных изменений. Продолжить?";
    private static final String METHOD_ARGS_COUNT = "Количество аргументов метода равно %d, передано - %d.";
    private static final String MAP_FILE_INVALID_FORMAT = "Некорректный формат .map у файла \"%s\" в строке номер %d.";

    private EditableMap map;

    public void open(String filename) {
        Scanner scanner = null;
        try {
            scanner  = new Scanner(Paths.get(filename.endsWith(".map") ? filename : filename + ".map"));
        } catch (IOException e) {
            System.out.printf("Не удалось открыть файл \"%s\", ошибка: %s.", e.getMessage(), e.getCause());
            return;
        }

        if (map != null || !Question.ask(String.format(RELOAD_MAP, "Чтение данных карты матча из файла")))
            return;

        int line = 1;
        while (scanner.hasNext()) {
            String[] strings = MapEditor.checkMapFileLine(scanner.nextLine(), line++, filename);
        }
    }
    public void open(String[] args) {
        Check.condition(args.length == 1, String.format(METHOD_ARGS_COUNT, 1, args.length));
        this.open(args[0]);
    }

    public static String[] checkMapFileLine(String line, int lineNumber, String filename) {
        String error = String.format(MAP_FILE_INVALID_FORMAT, filename, lineNumber);
        String[] strings = line.split(" ");
        if (lineNumber == 1)
            Check.condition(strings.length == 3 && strings[0].equalsIgnoreCase("map_size"), error);
        else if (lineNumber >= 2 && lineNumber <= 4) {
            Check.condition(strings.length == 2, error);
            switch (lineNumber) {
                case 2 :
                    Check.condition(strings[0].equalsIgnoreCase("view_radius"), error);
                    break;
                case 3 :
                    Check.condition(strings[0].equalsIgnoreCase("mining_radius"), error);
                    break;
                case 4 :
                    Check.condition(strings[0].equalsIgnoreCase("attack_radius"), error);
                    break;
            }
        } else
            Check.condition(strings.length == 3 && (strings[0].equalsIgnoreCase("block") || strings[0].equalsIgnoreCase("spawn_position")), error);

        return strings;
    }
}
