import logic.*;
import logic.Food;
import org.junit.Assert;
import org.junit.Test;

public class GameStateTests {
    public GameState game = new GameState(
            new GameMap(5, 5, false),
            new Snake(2, 2),
        new Food(10, false), new Location(2, 3));

    @Test
    public void gameStateConstructorTest() {
        Assert.assertEquals(false, game.isOver());
        Assert.assertEquals(0, game.getScores());
    }

    @Test
    public void testGameStateCreate() {
        Snake mySnake= new Snake(1,4);
        GameState myState = new GameState(5,6,mySnake);
        // Assert.assertNotNull(myState.getMap().getObject(1,4).getSnake());
        Assert.assertEquals(6, myState.getMap().getHeight());
        Assert.assertEquals(5, myState.getMap().getWidth());
    }

    @Test
    public void testGameStateWithLevelOne() {
        GameState myState = new GameState(Level.one);
        // Assert.assertNotNull(myState.getMap().getObject(3,3).getSnake());
        Assert.assertEquals(15, myState.getMap().getHeight());
        Assert.assertEquals(15, myState.getMap().getWidth());
    }

    @Test
    public void testGameStateWithLevelZero() {
        GameState myState = new GameState(Level.zero);
        // Assert.assertNotNull(myState.getMap().getObject(3,3).getSnake());
        Assert.assertEquals(10, myState.getMap().getHeight());
        Assert.assertEquals(10, myState.getMap().getWidth());
    }

    @Test
    public void testGameStateWithLevelTwo() {
        GameState myState = new GameState(Level.two);
        // Assert.assertNotNull(myState.getMap().getObject(3,3).getSnake());
        Assert.assertEquals(12, myState.getMap().getHeight());
        Assert.assertEquals(12, myState.getMap().getWidth());
    }


    @Test
    public void snakeFromConstructorTest() {
        //Assert.assertEquals(1, game.getSnake().body.size());
        Assert.assertEquals(new Location(2, 2), game.getSnake().getHead());
        Assert.assertEquals(1, game.getSnake().getLength());
        Assert.assertEquals(false, game.getMap().isCycled());
        GameState Game2 = new GameState(5,5,
                new Snake(2, 2),
                true);
        Assert.assertEquals(new Location(2, 2), Game2.getSnake().getHead());
        Assert.assertEquals(1, Game2.getSnake().getLength());
        Assert.assertEquals(5, Game2.getMap().getHeight());
        Assert.assertEquals(5, Game2.getMap().getWidth());
        Assert.assertTrue(Game2.isNoPoison());
    }

    @Test
    public void movementTests() {
        game.getSnake().move(Direction.Left, game);
        Assert.assertEquals(new Location(1, 2), game.getSnake().getHead());
        Assert.assertEquals(Direction.Left, game.getSnake().getDirection());

        game.getSnake().move(Direction.Up, game);
        Assert.assertEquals(new Location(1, 1), game.getSnake().getHead());
        Assert.assertEquals(Direction.Up, game.getSnake().getDirection());

        game.getSnake().move(Direction.Right, game);
        Assert.assertEquals(new Location(2, 1), game.getSnake().getHead());
        Assert.assertEquals(Direction.Right, game.getSnake().getDirection());

        game.getSnake().move(Direction.Down, game);
        Assert.assertEquals(new Location(2, 2), game.getSnake().getHead());
        Assert.assertEquals(Direction.Down, game.getSnake().getDirection());
    }

    @Test
    public void eatTests() {
        game.getSnake().move(Direction.Down, game);
        Assert.assertEquals(new Location(2, 3), game.getSnake().getHead());
        Assert.assertEquals(2, game.getSnake().getLength());
        Assert.assertEquals(10, game.getScores());
        Assert.assertEquals(1, game.getMap().getFoodCount());
        Assert.assertEquals(false, game.isOver());

    }

    @Test
    public void nonCycledMapTests() {
        game.getSnake().move(Direction.Down, game);
        Assert.assertEquals(new Location(2, 3), game.getSnake().getHead());
        Assert.assertEquals(false, game.isOver());

        game.getSnake().move(Direction.Down, game);
        Assert.assertEquals(new Location(2, 4), game.getSnake().getHead());
        Assert.assertEquals(false, game.isOver());

        game.getSnake().move(Direction.Down, game);
        Assert.assertEquals(true, game.isOver());
    }

    @Test
    public void cycledMapTests() {
        GameState game = new GameState(
                new GameMap(5, 5, true),
                new Snake(2, 2),
            new Food(10, true),
            new Location(2, 3));

        game.getSnake().move(Direction.Down, game);
        Assert.assertEquals(new Location(2, 3), game.getSnake().getHead());
        Assert.assertEquals(false, game.isOver());

        game.getSnake().move(Direction.Down, game);
        Assert.assertEquals(new Location(2, 4), game.getSnake().getHead());
        Assert.assertEquals(false, game.isOver());

        game.getSnake().move(Direction.Down, game);
        Assert.assertEquals(false, game.isOver());
    }

    @Test
    public void moveTest(){
        GameState game = new GameState(
                new GameMap(5, 5, true),
                new Snake(2, 2),
                new Food(10, true),
                new Location(2, 3));
        game.getSnake().setDirection(Direction.Down);
        for (int i = 0;i<4;i++)
            game.makeMove(null);
        Assert.assertEquals(new Location(2,3), game.getSnake().getHead());
    }
}
