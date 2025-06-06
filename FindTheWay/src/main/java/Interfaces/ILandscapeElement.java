package Interfaces;

import java.util.List;

public interface ILandscapeElement {
    boolean canMove();
    boolean canRotate();
    boolean canRemove();
    void update(List<ILandscapeElement> neighbors);
    void onMove();
    ILandscapeElement transform();
    String getLandscapeType();
}
