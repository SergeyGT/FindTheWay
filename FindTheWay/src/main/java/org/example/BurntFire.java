package org.example;

import Interfaces.ILandscapeElement;

import java.util.List;

public class BurntFire implements ILandscapeElement {
    @Override public boolean canMove(Cell cell) { return false; }
    @Override public boolean canRotate(Cell cell) { return false; }
    @Override public boolean canRemove(Cell cell) { return false; }
    @Override public void update(Cell cell) {}
    @Override public boolean shouldTransform() { return false; }
    @Override public void transform(Cell cell) {}
    @Override public boolean shouldRemoveAfterTransform() { return false; }
    @Override public void checkWatering(List<LandscapeCellDecorator> allDecorators, Cell cell) {}
}