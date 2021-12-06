package ru.croccode.hypernull.map.editor;

import ru.croccode.hypernull.geometry.Point;
import ru.croccode.hypernull.server.AsciiMatchPrinter;
import ru.croccode.hypernull.util.Check;
import ru.croccode.hypernull.util.Question;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class MapEditor {
    private static final String RELOAD_MAP = "%s приведет к потере несохраненных изменений. Продолжить?";
    private static final String METHOD_ARGS_COUNT = "Количество аргументов метода равно %d, передано - %d.";
    private static final String MAP_FILE_INVALID_FORMAT = "Некорректный формат .map у файла \"%s\" в строке номер %d.";
    private static final String NO_MAP_DATA = "В редакторе отсутствует информция о карте матча.";

    private EditableMap map;

    public void open(String filename) {
        Scanner scanner = null;
        try {
            scanner  = new Scanner(Paths.get(String.format("maps\\%s", filename.endsWith(".map") ? filename : filename + ".map")));
        } catch (IOException e) {
            System.out.printf("Не удалось открыть файл \"%s\", ошибка: %s.", e.getMessage(), e.getCause());
            return;
        }

        int width = 0, height = 0, line = 1;
        EditableMap.Radius radius = new EditableMap.Radius();
        ArrayList<MapPoint> points = new ArrayList<>();

        while (scanner.hasNext()) {
            String[] strings = MapEditor.checkMapFileLine(scanner.nextLine(), line, filename);
            switch (line) {
                case 1 : width = Integer.parseInt(strings[1]); height = Integer.parseInt(strings[2]); break;
                case 2 : radius.view = Integer.parseInt(strings[1]); break;
                case 3 : radius.mining = Integer.parseInt(strings[1]); break;
                case 4 : radius.attack = Integer.parseInt(strings[1]); break;
                default : points.add(new MapPoint(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]),
                    strings[0].equalsIgnoreCase("block") ? MapPoint.State.BLOCKED :  MapPoint.State.SPAWN));
            }

            line++;
        }

        if (map != null && !Question.ask(String.format(RELOAD_MAP, "Чтение данных карты матча из файла")))
            return;

        this.map = new EditableMap(width, height);
        this.map.radius().assign(radius);
        points.forEach((point) -> map.setPointState(point.x(), point.y(), point.state));

        System.out.println("Чтение завершено успешно.");
    }
    public void open(String[] args) {
        Check.condition(args.length == 1, String.format(METHOD_ARGS_COUNT, 1, args.length));
        this.open(args[0]);
    }

    public void printMap() {
        if (this.map == null) {
            System.out.println("NO_MAP_DATA");
        } else
            AsciiMatchPrinter.printMap(this.map, null, null);
    }
    public void printMap(String[] args) {
        Check.condition(args.length == 0, String.format(METHOD_ARGS_COUNT, 0, args.length));
        this.printMap();
    }

    public static String[] checkMapFileLine(String line, int lineNumber, String filename) {
        String error = String.format(MAP_FILE_INVALID_FORMAT, filename, lineNumber);
        String[] strings = line.split(" ");
        if (lineNumber == 1)
            Check.condition(strings.length == 3 && strings[0].equalsIgnoreCase("map_size"), error);
        else if (lineNumber >= 2 && lineNumber <= 4) {
            Check.condition(strings.length == 2, error);
            switch (lineNumber) {
                case 2 : Check.condition(strings[0].equalsIgnoreCase("view_radius"), error); break;
                case 3 : Check.condition(strings[0].equalsIgnoreCase("mining_radius"), error); break;
                case 4 : Check.condition(strings[0].equalsIgnoreCase("attack_radius"), error); break;
            }
        } else
            Check.condition(strings.length == 3 && (strings[0].equalsIgnoreCase("block") || strings[0].equalsIgnoreCase("spawn_position")), error);

        return strings;
    }
}
