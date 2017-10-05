import logic.Direction;
import logic.GameState;
import logic.Location;
import logic.Snake;
import org.junit.Assert;
import org.junit.Test;

public class SnakeTests {

  @Test
  public void testCreate() {
    Snake snake = new Snake(1, 2);
    Assert.assertEquals(new Location(1, 2), snake.getHead());
    Assert.assertEquals(1, snake.getLength());
    Assert.assertEquals(Direction.Stop, snake.direction);
    Assert.assertEquals(1, snake.body.size());
  }

  @Test
  public void testMove() {
    GameState game = new GameState(10, 10, new Snake(0, 0));
    Assert.assertEquals(false, game.isOver);
    Assert.assertEquals(0, game.scores);
    Assert.assertEquals(new Location(0, 0), game.snake.getHead());

    game.snake.move(Direction.Right, game);
    Assert.assertEquals(new Location(1, 0), game.snake.getHead());
    Assert.assertEquals(null, game.map.getObject(0, 0).snake);
    Assert.assertEquals(1, game.snake.getLength());
  }
}