package tests;
import main.*;
import org.junit.Assert;
import org.junit.Test;

public class SnakeTests {
    @Test
    public void testCreate(){
        Snake snake = new Snake(1,2);
        Assert.assertEquals(new Location(1,2), snake.getHead());
        Assert.assertEquals(1, snake.Length());
        Assert.assertEquals(Direction.Stop, snake.direction);
        Assert.assertEquals(1, snake.Body.size());
    }
}