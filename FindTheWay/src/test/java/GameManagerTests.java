import org.example.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class GameManagerTests {
    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
    }

    @Test
    void testGameStartsCorrectly() {
        gameManager.StartGame();

        assertNotNull(gameManager.get_field(), "Поле должно быть создано");
    }

    @Test
    void testMazeCompletionEndsGame() {
        gameManager.StartGame();
        GameManager.MazeObserver observer = gameManager.new MazeObserver();
        observer.OnMaseComplete();

        //assertTrue(gameManager.is_gameStatus(), "Игра должна завершиться после завершения лабиринта");
    }
}
