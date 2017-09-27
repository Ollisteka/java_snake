package tests;
import main.*;
import org.junit.Assert;
import org.junit.Test;

public class SnakeTests {
    @Test
    public void testCreate(){
        Snake snake = new Snake(1,2);
        Assert.assertEquals(snake.getHead(), new Location(1,2));
    }
}