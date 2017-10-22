import logic.Direction;
import logic.Food;
import logic.GameMap;
import logic.GameState;
import logic.Level;
import logic.Location;
import logic.Snake;
import gui.GameFieldPanel;
import gui.GameForm;
import org.junit.Assert;
import org.junit.Test;

public class GameStateTests {
    public GameState Game = new GameState(
            new GameMap(5, 5, false),
            new Snake(2, 2),
        new Food(10, false), new Location(2, 3));

    @Test
    public void GameStateConstructorTest() {
        Assert.assertEquals(false, Game.isOver());
        Assert.assertEquals(0, Game.getScores());
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
    public void SnakeFromConstructorTest() {
        //Assert.assertEquals(1, Game.getSnake().body.size());
        Assert.assertEquals(new Location(2, 2), Game.getSnake().getHead());
        Assert.assertEquals(1, Game.getSnake().getLength());
        Assert.assertEquals(false, Game.getMap().isCycled());
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
    public void MovementTests() {
        Game.getSnake().move(Direction.Left, Game);
        Assert.assertEquals(new Location(1, 2), Game.getSnake().getHead());
        Assert.assertEquals(Direction.Left, Game.getSnake().getDirection());

        Game.getSnake().move(Direction.Up, Game);
        Assert.assertEquals(new Location(1, 1), Game.getSnake().getHead());
        Assert.assertEquals(Direction.Up, Game.getSnake().getDirection());

        Game.getSnake().move(Direction.Right, Game);
        Assert.assertEquals(new Location(2, 1), Game.getSnake().getHead());
        Assert.assertEquals(Direction.Right, Game.getSnake().getDirection());

        Game.getSnake().move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 2), Game.getSnake().getHead());
        Assert.assertEquals(Direction.Down, Game.getSnake().getDirection());
    }

    @Test
    public void EatTests() {
        Game.getSnake().move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 3), Game.getSnake().getHead());
        Assert.assertEquals(2, Game.getSnake().getLength());
        Assert.assertEquals(10, Game.getScores());
        Assert.assertEquals(1, Game.getMap().getFoodCount());
        Assert.assertEquals(false, Game.isOver());

    }

    @Test
    public void NonCycledMapTests() {
        Game.getSnake().move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 3), Game.getSnake().getHead());
        Assert.assertEquals(false, Game.isOver());

        Game.getSnake().move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 4), Game.getSnake().getHead());
        Assert.assertEquals(false, Game.isOver());

        Game.getSnake().move(Direction.Down, Game);
        Assert.assertEquals(true, Game.isOver());
    }

    @Test
    public void CycledMapTests() {
        GameState Game = new GameState(
                new GameMap(5, 5, true),
                new Snake(2, 2),
            new Food(10, true),
            new Location(2, 3));

        Game.getSnake().move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 3), Game.getSnake().getHead());
        Assert.assertEquals(false, Game.isOver());

        Game.getSnake().move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 4), Game.getSnake().getHead());
        Assert.assertEquals(false, Game.isOver());

        Game.getSnake().move(Direction.Down, Game);
        Assert.assertEquals(false, Game.isOver());
    }

    @Test
    public void MoveWithTimerTests(){
        GameState Game = new GameState(
                new GameMap(5, 5, true),
                new Snake(2, 2),
                new Food(10, true),
                new Location(2, 3));
        Game.startTimer();
        Assert.assertEquals(0, Game.getTimerTick());
        Game.getSnake().setDirection(Direction.Down);
        Game.setTimerTick(1);
        Assert.assertEquals(1, Game.getTimerTick());
        Assert.assertEquals(new Location(2,2), Game.getSnake().getHead());
    }
}
