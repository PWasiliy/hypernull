package ru.croccode.hypernull.map.editor;

import ru.croccode.hypernull.domain.MatchMap;
import ru.croccode.hypernull.geometry.Point;
import ru.croccode.hypernull.geometry.Size;
import ru.croccode.hypernull.util.Check;

import java.util.ArrayList;
import java.util.List;

public class EditableMap implements MatchMap {
    private static final String MUST_BE_MORE_THEN_ZERO = "%s карты матча должна быть больше нуля.";

    public static class Radius {
        public int view, mining, attack;
        public Radius(int view, int mining, int attack) {
            this.attack = attack;
            this.mining = mining;
            this.view = view;
        }
        public Radius() {
            this(0, 0, 0);
        }
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
    public enum PointState {
        FREE, BLOCKED, SPAWN;
    }

    private PointState[][] points;
    private final Radius radius;

    public EditableMap(int width, int height) {
        this.points = new PointState[0][0];
        this.radius = new Radius();

        this.setSize(width, height);
    }
    public Radius radius() { return radius; }
    public boolean isInside(int x, int y) { return x >= 0 && x < this.getSize().width() && y >= 0 && y < this.getSize().height(); }
    public boolean isInside(Point point) { return this.isInside(point.x(), point.y()); }
    public void setPointState(int x, int y, PointState state) {
        this.checkIsInside(x, y);
        this.points[x][y] = state;
    }
    public void setBlocked(int x, int y) { this.setPointState(x, y, PointState.BLOCKED); }
    public void setSpawn(int x, int y) { this.setPointState(x, y, PointState.SPAWN); }
    public void setFree(int x, int y) {
        this.checkIsInside(x, y);
        this.points[x][y] = null;
    }
    public void setSize(int width, int height) {
        Check.condition(width > 0, String.format(MUST_BE_MORE_THEN_ZERO, "Ширина"));
        Check.condition(height > 0, String.format(MUST_BE_MORE_THEN_ZERO, "Высота"));

        PointState[][] copy = new PointState[width][height];
        for (int i = 0; i < Math.min(this.points.length, copy.length); i++)
            System.arraycopy(this.points[i], 0, copy[i], 0, Math.min(this.points[i].length, copy[i].length));

        this.points = copy;
    }
    public String getInfo() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("map_size %d %d\n", this.getWidth(), this.getHeight()));
        builder.append(String.format("view_radius %d\n", this.radius().view));
        builder.append(String.format("mining_radius %d\n", this.radius().mining));
        builder.append(String.format("attack_radius %d\n", this.radius().attack));

        return builder.toString();
    }
    public PointState getPointState(int x, int y) {
        this.checkIsInside(x, y);
        return this.points[x][y];
    }

    @Override
    public Size getSize() {
        return new Size(points.length, points[0].length);
    }
    @Override
    public boolean isBlocked(Point point) { return this.getPointState(point.x(), point.y()) == PointState.BLOCKED; }
    @Override
    public List<Point> getSpawnPositions() {
        ArrayList<Point> result = new ArrayList<>();
        for (int x = 0; x < this.getWidth(); x++)
            for (int y = 0; y < this.getHeight(); y++)
                if (this.points[x][y] == PointState.SPAWN)
                    result.add(new Point(x, y));

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
