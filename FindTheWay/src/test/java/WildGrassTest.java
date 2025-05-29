import org.example.WildGrass;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WildGrassTest {
    @Test
    public void testCanMove_AlwaysReturnsTrue() {
        WildGrass wildGrass = new WildGrass();
        assertTrue(wildGrass.canMove());
    }

    @Test
    public void testCanRotate_AlwaysReturnsTrue() {
        WildGrass wildGrass = new WildGrass();
        assertTrue(wildGrass.canRotate());
    }

    @Test
    public void testCanRemove_AlwaysReturnsTrue() {
        WildGrass wildGrass = new WildGrass();
        assertTrue(wildGrass.canRemove());
    }
}