import org.example.Tree;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {
    @Test
    public void testSurroundByFire_SetsBurntFlag() {
        Tree tree = new Tree();
        tree.surroundByFire();

        assertTrue(tree.isBurnt());
        assertTrue(tree.canRemove());
    }

    @Test
    public void testCanMove_AlwaysReturnsTrue() {
        Tree tree = new Tree();
        assertTrue(tree.canMove());
    }

    @Test
    public void testCanRotate_AlwaysReturnsTrue() {
        Tree tree = new Tree();
        assertTrue(tree.canRotate());
    }
}