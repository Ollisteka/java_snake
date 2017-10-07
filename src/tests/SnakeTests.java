package tests;

import logic.*;
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

  @Test
  public void testEatFood(){
    GameState game = new GameState(10, 10, new Snake(0, 0));
    MapObject f = new MapObject(1,0);
    f.food = new Food(new Location(1,0), 10, false);
    game.map.setObject(1,0,f);
    game.snake.move(Direction.Right, game);
    Assert.assertNotNull(game.map.getObject(0, 0).snake);
    Assert.assertEquals(2, game.snake.getLength());
  }

  @Test
  public void testGoAnotherSide(){
    GameState game = new GameState(10, 10, new Snake(0, 0));
    game.snake.move(Direction.Up, game);
    Assert.assertNotNull(game.map.getObject(0, 9).snake);
    Assert.assertNull(game.map.getObject(0, 0).snake);
  }
}