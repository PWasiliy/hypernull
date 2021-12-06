package ru.croccode.hypernull.map.editor;

import ru.croccode.hypernull.geometry.Point;

public class MapPoint extends Point {
    public PointState state = PointState.FREE;
    public MapPoint(int x, int y) { super(x, y); }
    public MapPoint(int x, int y, PointState state) {
        super(x, y);
        this.state = state;
    }
    public boolean isBlocked() { return this.state == PointState.BLOCKED; }
    public boolean isSpawn() { return  this.state == PointState.SPAWN; }
}
