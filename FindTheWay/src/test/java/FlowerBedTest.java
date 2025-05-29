import org.example.FlowerBed;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlowerBedTest {
    @Test
    public void testWater_ResetsTurnsWithoutWater() {
        FlowerBed flowerBed = new FlowerBed();
        flowerBed.water();

        assertTrue(flowerBed.isWatered());
        assertEquals(0, flowerBed.getTurnsWithoutWater());
    }

    @Test
    public void testUpdate_AfterThreeTurnsWithoutWater_Dies() {
        FlowerBed flowerBed = new FlowerBed();
        for (int i = 0; i < 3; i++) {
            flowerBed.update();
        }

        assertTrue(flowerBed.isAlive());
        assertFalse(flowerBed.canRemove());
    }

    @Test
    public void testCanMove_AlwaysReturnsFalse() {
        FlowerBed flowerBed = new FlowerBed();
        assertFalse(flowerBed.canMove());
    }
}