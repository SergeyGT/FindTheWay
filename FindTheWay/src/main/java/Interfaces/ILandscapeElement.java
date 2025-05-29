package Interfaces;

public interface ILandscapeElement {
    boolean canMove();
    boolean canRotate();
    boolean canRemove();
    void update();
}
