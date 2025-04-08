package org.example;

enum DirectionEnum {
    UP, DOWN, LEFT, RIGHT
}

class Direction {
    private DirectionEnum direction;

    public boolean canConnect(Direction other) {
        return (direction == DirectionEnum.UP && other.direction == DirectionEnum.DOWN) ||
                (direction == DirectionEnum.DOWN && other.direction == DirectionEnum.UP) ||
                (direction == DirectionEnum.LEFT && other.direction == DirectionEnum.RIGHT) ||
                (direction == DirectionEnum.RIGHT && other.direction == DirectionEnum.LEFT);
    }
}
