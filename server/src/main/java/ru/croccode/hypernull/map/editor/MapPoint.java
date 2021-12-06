package ru.croccode.hypernull.map.editor;

import ru.croccode.hypernull.geometry.Point;

public class MapPoint extends Point {
    public enum State {
        FREE, BLOCKED, SPAWN;
    }

    public State state = State.FREE;
    public MapPoint(int x, int y) { super(x, y); }
    public MapPoint(int x, int y, State state) {
        super(x, y);
        this.state = state;
    }
    public boolean isBlocked() { return this.state == State.BLOCKED; }
    public boolean isSpawn() { return  this.state == State.SPAWN; }
}
