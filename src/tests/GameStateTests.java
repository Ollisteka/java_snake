import logic.*;
import org.junit.Assert;
import org.junit.Test;

public class GameStateTests {
    public GameState Game = new GameState(
            new GameMap(5, 5, false),
            new Snake(2, 2),
        new Food(10, false), new Location(2, 3));

    @Test
    public void GameStateConstructorTest() {
        Assert.assertEquals(false, Game.isOver);
        Assert.assertEquals(0, Game.scores);
    }

    @Test
    public void testGameStateCreate() {
        Snake mySnake= new Snake(1,4);
        GameState myState = new GameState(5,6,mySnake);
        // Assert.assertNotNull(myState.map.getObject(1,4).snake);
        Assert.assertEquals(6, myState.map.getHeight());
        Assert.assertEquals(5, myState.map.getWidth());
    }

    @Test
    public void testGameStateWithLevelOne() {
        GameState myState = new GameState(Level.one);
        // Assert.assertNotNull(myState.map.getObject(3,3).snake);
        Assert.assertEquals(15, myState.map.getHeight());
        Assert.assertEquals(15, myState.map.getWidth());
    }

    @Test
    public void testGameStateWithLevelZero() {
        GameState myState = new GameState(Level.zero);
        // Assert.assertNotNull(myState.map.getObject(3,3).snake);
        Assert.assertEquals(10, myState.map.getHeight());
        Assert.assertEquals(10, myState.map.getWidth());
    }

    @Test
    public void testGameStateWithLevelTwo() {
        GameState myState = new GameState(Level.two);
        // Assert.assertNotNull(myState.map.getObject(3,3).snake);
        Assert.assertEquals(12, myState.map.getHeight());
        Assert.assertEquals(12, myState.map.getWidth());
    }


    @Test
    public void SnakeFromConstructorTest() {
      //Assert.assertEquals(1, Game.snake.body.size());
        Assert.assertEquals(new Location(2, 2), Game.snake.getHead());
        Assert.assertEquals(1, Game.snake.getLength());
        Assert.assertEquals(false, Game.map.isCycled());
        GameState Game2 = new GameState(5,5,
                new Snake(2, 2),
                true);
        Assert.assertEquals(new Location(2, 2), Game2.snake.getHead());
        Assert.assertEquals(1, Game2.snake.getLength());
        Assert.assertEquals(5, Game2.map.getHeight());
        Assert.assertEquals(5, Game2.map.getWidth());
        Assert.assertTrue(Game2.noPoison);
    }

    @Test
    public void MovementTests() {
        Game.snake.move(Direction.Left, Game);
        Assert.assertEquals(new Location(1, 2), Game.snake.getHead());
      Assert.assertEquals(Direction.Left, Game.snake.getDirection());

        Game.snake.move(Direction.Up, Game);
        Assert.assertEquals(new Location(1, 1), Game.snake.getHead());
      Assert.assertEquals(Direction.Up, Game.snake.getDirection());

        Game.snake.move(Direction.Right, Game);
        Assert.assertEquals(new Location(2, 1), Game.snake.getHead());
      Assert.assertEquals(Direction.Right, Game.snake.getDirection());

        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 2), Game.snake.getHead());
      Assert.assertEquals(Direction.Down, Game.snake.getDirection());
    }

    @Test
    public void EatTests() {
        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 3), Game.snake.getHead());
        Assert.assertEquals(2, Game.snake.getLength());
        Assert.assertEquals(10, Game.scores);
        Assert.assertEquals(1, Game.map.getFoodCount());
        Assert.assertEquals(false, Game.isOver);

    }

    @Test
    public void NonCycledMapTests() {
        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 3), Game.snake.getHead());
        Assert.assertEquals(false, Game.isOver);

        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 4), Game.snake.getHead());
        Assert.assertEquals(false, Game.isOver);

        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(true, Game.isOver);
    }

    @Test
    public void CycledMapTests() {
        GameState Game = new GameState(
                new GameMap(5, 5, true),
                new Snake(2, 2),
            new Food(10, true),
            new Location(2, 3));

        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 3), Game.snake.getHead());
        Assert.assertEquals(false, Game.isOver);

        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 4), Game.snake.getHead());
        Assert.assertEquals(false, Game.isOver);

        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(false, Game.isOver);
    }
}
