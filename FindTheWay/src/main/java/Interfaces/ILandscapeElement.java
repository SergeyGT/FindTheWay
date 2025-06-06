package Interfaces;

import org.example.Cell;
import org.example.LandscapeCellDecorator;

import java.util.List;

public interface ILandscapeElement {
    boolean canMove(Cell cell);
    boolean canRotate(Cell cell);
    boolean canRemove(Cell cell);
    void update(Cell cell);
    boolean shouldTransform();
    void transform(Cell cell);
    boolean shouldRemoveAfterTransform();
    void checkWatering(List<LandscapeCellDecorator> allDecorators, Cell cell);
}
