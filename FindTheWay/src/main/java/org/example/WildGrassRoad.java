package org.example;

import Interfaces.ILandscapeElement;
import java.util.List;

public class WildGrassRoad implements ILandscapeElement {
    private final int maxTurnsNearRoad = 3;
    private int currentTurnsNearRoad = 0;
    private boolean isAlive = true;

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean canRotate() {
        return false;
    }

    @Override
    public boolean canRemove() {
        return !isAlive;
    }

    @Override
    public void update(List<ILandscapeElement> neighbors) {
        boolean nearRoad = neighbors.stream().anyMatch(e -> e == null);

        if (nearRoad) {
            currentTurnsNearRoad++;
            if (currentTurnsNearRoad >= maxTurnsNearRoad) {
                isAlive = false; // "умирает", чтобы в transform вернуть null (превращение в дорогу)
            }
        } else {
            currentTurnsNearRoad = 0; // сброс, если дороги рядом нет
        }
    }

    @Override
    public void onMove() {
        // Нет действия при движении
    }

    @Override
    public ILandscapeElement transform() {
        return isAlive ? this : null; // превращается в дорогу
    }

    @Override
    public String getLandscapeType() {
        return "grassroad";
    }
}
