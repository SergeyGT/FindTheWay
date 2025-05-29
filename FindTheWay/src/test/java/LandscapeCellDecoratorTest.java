import Interfaces.ILandscapeElement;
import org.example.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LandscapeCellDecoratorTest {
    @Test
    public void testCanMove_WithMovableElement_ReturnsTrue() {
        Cell cell = new Cell(0, 0, false, false, false, null, null, "tree");
        ILandscapeElement element = new Tree();
        LandscapeCellDecorator decorator = new LandscapeCellDecorator(cell, element);

        assertTrue(decorator.canMove());
    }

    @Test
    public void testCanMove_WithNonMovableElement_ReturnsFalse() {
        Cell cell = new Cell(0, 0, false, false, false, null, null, "flowerbed");
        ILandscapeElement element = new FlowerBed();
        LandscapeCellDecorator decorator = new LandscapeCellDecorator(cell, element);

        assertFalse(decorator.canMove());
    }

    @Test
    public void testUpdate_BurntTree_TransformsToRoad() {
        Cell cell = new Cell(0, 0, false, false, false, null, null, "tree");
        Tree tree = new Tree();
        LandscapeCellDecorator decorator = new LandscapeCellDecorator(cell, tree);

        tree.surroundByFire(); // Делаем дерево сгоревшим
        decorator.update();

        assertNull(decorator.landscapeElement);
        assertFalse(cell.getIsEmpty());
        assertEquals(DirectionEnum.LEFT, cell.getDirectionEnter().getDirectionEnum());
    }
}