package ru.croccode.hypernull.map.editor;

import ru.croccode.hypernull.domain.MatchMap;
import ru.croccode.hypernull.geometry.Point;
import ru.croccode.hypernull.geometry.Size;
import ru.croccode.hypernull.util.Check;

import java.util.ArrayList;
import java.util.List;

public class EditableMap implements MatchMap {
    private final MapPoint[][] points;

    public EditableMap(int width, int height) {
        Check.condition(width > 0, "Ширина карты матча должна быть больше нуля.");
        Check.condition(height > 0, "Высота карты матча должна быть больше нуля.");

        this.points = new MapPoint[width][height];
    }

    @Override
    public Size getSize() {
        return new Size(points.length, points[0].length);
    }
    @Override
    public boolean isBlocked(Point point) { return points[point.x()][point.y()].isBlocked(); }
    @Override
    public List<Point> getSpawnPositions() {
        ArrayList<Point> result = new ArrayList<>();
        for (int x = 0; x < this.getWidth(); x++)
            for (int y = 0; y < this.getHeight(); y++)
                if (this.points[x][y].isSpawn())
                    result.add(points[x][y]);

        return result;
    }
}
