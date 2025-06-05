import org.example.Fire;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FireTest {
    @Test
    public void testIncrementMoveCount_UpdatesState() {
        Fire fire = new Fire();
        fire.incrementMoveCount();
        fire.incrementMoveCount();

        assertEquals(2, fire.getMoveCount());
        assertTrue(fire.canMove());
    }

    @Test
    public void testCanMove_AfterMaxMoves_ReturnsFalse() {
        Fire fire = new Fire();
        for (int i = 0; i < 3; i++) {
            fire.incrementMoveCount();
        }

        assertFalse(fire.canMove());
        assertTrue(fire.canRemove());
    }

    @Test
    public void testCanRotate_AlwaysReturnsTrue() {
        Fire fire = new Fire();
        assertTrue(fire.canRotate());
    }
}