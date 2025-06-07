package org.example;

import Factories.LandscapeElementFactory;
import Interfaces.ILandscapeElement;
import java.util.List;

public class LandscapeCellDecorator {
    public Cell cell;
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

    public void handleCellMove(Cell newCell, LandscapeElementFactory factory) {
        if (newCell.getLandscapeType() == null) {
            this.landscapeElement = null;
            return;
        }

        this.landscapeElement = (this.landscapeElement != null)
                ? this.landscapeElement.copyState()
                : factory.create(newCell.getLandscapeType());

        this.cell = newCell;
    }

}
