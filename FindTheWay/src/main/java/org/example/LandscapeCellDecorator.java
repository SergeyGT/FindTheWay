package org.example;

import Interfaces.ILandscapeElement;
import java.util.List;

public class LandscapeCellDecorator {
    public final Cell cell;
    public ILandscapeElement landscapeElement;

    public LandscapeCellDecorator(Cell cell, ILandscapeElement element) {
        this.cell = cell;
        this.landscapeElement = element;
    }

    public void update(List<LandscapeCellDecorator> allDecorators) {
        if (landscapeElement == null) return;
        landscapeElement.update(cell, allDecorators);

        if (landscapeElement.shouldTransform()) {
            landscapeElement.transform(cell);
            if (landscapeElement.shouldRemoveAfterTransform()) {
                landscapeElement = null;
            }
        }
    }

//
//    public boolean canMove() {
//        if (landscapeElement == null) return true;
//        return landscapeElement.canMove(cell);
//    }
//
//    public boolean canRotate() {
//        if (landscapeElement == null) return true;
//        return landscapeElement.canRotate(cell);
//    }
//
//    public boolean canRemove() {
//        if (landscapeElement == null) return true;
//        return landscapeElement.canRemove(cell);
//    }

}
