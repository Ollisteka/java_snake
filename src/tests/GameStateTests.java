package tests;

import logic.*;
import org.junit.Assert;
import org.junit.Test;

public class GameStateTests {
    public GameState Game = new GameState(
            new GameMap(5, 5, false),
            new Snake(2, 2),
            new Food(new Location(2, 3), 10, false));

    @Test
    public void GameStateConstructorTest() {
        Assert.assertEquals(false, Game.isOver);
        Assert.assertEquals(0, Game.scores);
    }

    @Test
    public void SnakeFromConstructorTest() {
        Assert.assertEquals(1, Game.snake.body.size());
        Assert.assertEquals(new Location(2, 2), Game.snake.getHead());
        Assert.assertEquals(1, Game.snake.getLength());
        Assert.assertEquals(false, Game.map.isCycled);
    }

    @Test
    public void MovementTests() {
        Game.snake.move(Direction.Left, Game);
        Assert.assertEquals(new Location(1, 2), Game.snake.getHead());
        Assert.assertEquals(Direction.Left, Game.snake.direction);

        Game.snake.move(Direction.Up, Game);
        Assert.assertEquals(new Location(1, 1), Game.snake.getHead());
        Assert.assertEquals(Direction.Up, Game.snake.direction);

        Game.snake.move(Direction.Right, Game);
        Assert.assertEquals(new Location(2, 1), Game.snake.getHead());
        Assert.assertEquals(Direction.Right, Game.snake.direction);

        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 2), Game.snake.getHead());
        Assert.assertEquals(Direction.Down, Game.snake.direction);
    }

    @Test
    public void EatTests() {
        Game.snake.move(Direction.Down, Game);
        Assert.assertEquals(new Location(2, 3), Game.snake.getHead());
        Assert.assertEquals(2, Game.snake.getLength());
        Assert.assertEquals(10, Game.scores);
        Assert.assertEquals(1, Game.map.foodCount);
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
                new Food(new Location(2, 3), 10, true));

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
