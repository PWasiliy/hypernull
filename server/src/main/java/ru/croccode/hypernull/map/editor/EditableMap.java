package ru.croccode.hypernull.map.editor;

import ru.croccode.hypernull.domain.MatchMap;
import ru.croccode.hypernull.geometry.Point;
import ru.croccode.hypernull.geometry.Size;
import ru.croccode.hypernull.util.Check;

import java.util.ArrayList;
import java.util.List;

public class EditableMap implements MatchMap {
    public static class Radius {
        public int view, mining, attack;
        public void setAll(int value) {
            this.attack = value;
            this.mining = value;
            this.view = value;
        }
        public void assign(Radius radius) {
            this.attack = radius.attack;
            this.mining = radius.mining;
            this.view = radius.view;
        }
    }

    private final MapPoint[][] points;
    private final Radius radius;

    public EditableMap(int width, int height) {
        Check.condition(width > 0, "Ширина карты матча должна быть больше нуля.");
        Check.condition(height > 0, "Высота карты матча должна быть больше нуля.");

        this.points = new MapPoint[width][height];
        this.radius = new Radius();
    }
    public Radius radius() { return radius; }
    public boolean isInside(int x, int y) { return x >= 0 && x <= this.getSize().width() && y >= 0 && y <= this.getSize().width(); }
    public boolean isInside(Point point) { return this.isInside(point.x(), point.y()); }
    public void setPointState(int x, int y, MapPoint.State state) {
        this.checkIsInside(x, y);
        if (this.points[x][y] == null)
            this.points[x][y] = new MapPoint(x, y, state);
        else
            this.points[x][y].state = state;
    }
    public void setBlocked(int x, int y) { this.setPointState(x, y, MapPoint.State.BLOCKED); }
    public void setSpawn(int x, int y) { this.setPointState(x, y, MapPoint.State.SPAWN); }
    public void setFree(int x, int y) {
        this.checkIsInside(x, y);
        this.points[x][y] = null;
    }

    @Override
    public Size getSize() {
        return new Size(points.length, points[0].length);
    }
    @Override
    public boolean isBlocked(Point point) {
        this.checkIsInside(point);
        return points[point.x()][point.y()] != null && points[point.x()][point.y()].isBlocked();
    }
    @Override
    public List<Point> getSpawnPositions() {
        ArrayList<Point> result = new ArrayList<>();
        for (int x = 0; x < this.getWidth(); x++)
            for (int y = 0; y < this.getHeight(); y++)
                if (this.points[x][y].isSpawn())
                    result.add(points[x][y]);

        return result;
    }
    @Override
    public int getViewRadius() { return this.radius().view; }
    @Override
    public int getAttackRadius() { return this.radius().attack; }
    @Override
    public int getMiningRadius() { return this.radius().mining; }

    private void checkIsInside(int x, int y) { Check.condition(this.isInside(x, y), String.format("Точка (%d, %d) не попадает в размеры карты %s.", x, y, this.getSize())); }
    private void checkIsInside(Point point) { this.checkIsInside(point.x(), point.y()); }
}
