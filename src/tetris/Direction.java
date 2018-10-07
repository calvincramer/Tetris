package tetris;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;
    
    public Direction rotateClockwise() {
        return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
    }
}
