import logic.*;
import logic.Food;
import org.junit.Assert;
import org.junit.Test;

public class SnakeTests {

  @Test
  public void testCreate() {
    Snake snake = new Snake(1, 2);
    Assert.assertEquals(new Location(1, 2), snake.getHead());
    Assert.assertEquals(1, snake.getLength());
    Assert.assertEquals(Direction.Stop, snake.getDirection());
    Assert.assertEquals(1, snake.getLength());
  }

  @Test
  public void testMove() {
    GameState game = new GameState(10, 10, new Snake(0, 0));
    Assert.assertEquals(false, game.isOver());
    Assert.assertEquals(0, game.getScores());
    Assert.assertEquals(new Location(0, 0), game.getSnake().getHead());

    game.getSnake().move(Direction.Right, game);
    Assert.assertEquals(new Location(1, 0), game.getSnake().getHead());
    //Assert.assertEquals(null, game.getMap().getObject(0, 0).getSnake());
    Assert.assertEquals(1, game.getSnake().getLength());
  }

  @Test
  public void testEatFood(){
    GameState game = new GameState(10, 10, new Snake(0, 0));
    game.getMap().setObject(new MapObject(new Food(10, false), 1, 0));
    game.getSnake().move(Direction.Right, game);
    // Assert.assertNotNull(game.getMap().getObject(0, 0).getSnake());
    Assert.assertEquals(2, game.getSnake().getLength());
  }

  @Test
  public void testKill(){
    GameState game = new GameState(10, 10, new Snake(0, 0));
    game.getMap().setObject(new MapObject(new Wall(), 1, 0));
    game.getSnake().move(Direction.Right, game);
    Assert.assertTrue(game.isOver());
  }

//  @Test
//  public void testGoAnotherSide(){
//    GameState game = new GameState(10, 10, new Snake(0, 0));
//    game.getSnake().move(Direction.Up, game);
//    Assert.assertNotNull(game.getMap().getObject(0, 9).getSnake());
//    Assert.assertNull(game.getMap().getObject(0, 0).getSnake());
//  }
}