package tests;

import logic.*;
import org.junit.Assert;
import org.junit.Test;

public class OtherTests {

    @Test
    public void testGameStateCreate() {
        Snake mySnake= new Snake(1,4);
        GameState myState = new GameState(5,6,mySnake);
        Assert.assertNotNull(myState.map.getObject(1,4).snake);
        Assert.assertEquals(6,myState.map.height());
        Assert.assertEquals(5,myState.map.width());
    }

    @Test
    public void testGameStateWithLevelOne() {
        GameState myState = new GameState(Level.one);
        Assert.assertNotNull(myState.map.getObject(3,3).snake);
        Assert.assertEquals(15,myState.map.height());
        Assert.assertEquals(15,myState.map.width());
        Assert.assertEquals(Level.one,myState.level);
    }

    @Test
    public void testGameStateWithLevelZero() {
        GameState myState = new GameState(Level.zero);
        Assert.assertNotNull(myState.map.getObject(3,3).snake);
        Assert.assertEquals(10,myState.map.height());
        Assert.assertEquals(10,myState.map.width());
        Assert.assertEquals(Level.zero,myState.level);
    }

    @Test
    public void testLoationsEqual(){
        Location first = new Location(1,1);
        Location second = new Location(1,1);
        Assert.assertEquals(first,second);
    }


}