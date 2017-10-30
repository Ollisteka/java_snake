package tests;

import gui.Controller;
import logic.*;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ControllerTests {
    @Test
    public void testSetDirections() {
        GameState g = new GameState(10, 10, new Snake(3,3));
        Controller c = new Controller(g);
        Button b = new Button();
        KeyEvent k = new KeyEvent(b, 0,0,0,KeyEvent.VK_DOWN,'a');
        c.keyPressed(k);
        Assert.assertEquals(g.getSnake().getDirection(), Direction.Down);
        k.setKeyCode(KeyEvent.VK_S);
        c.keyPressed(k);
        Assert.assertEquals(g.getSnake().getDirection(), Direction.Down);
        k.setKeyCode(KeyEvent.VK_LEFT);
        c.keyPressed(k);
        Assert.assertEquals(g.getSnake().getDirection(), Direction.Left);
        k.setKeyCode(KeyEvent.VK_A);
        c.keyPressed(k);
        Assert.assertEquals(g.getSnake().getDirection(), Direction.Left);
        k.setKeyCode(KeyEvent.VK_UP);
        c.keyPressed(k);
        Assert.assertEquals(g.getSnake().getDirection(), Direction.Up);
        k.setKeyCode(KeyEvent.VK_W);
        c.keyPressed(k);
        Assert.assertEquals(g.getSnake().getDirection(), Direction.Up);
        k.setKeyCode(KeyEvent.VK_RIGHT);
        c.keyPressed(k);
        Assert.assertEquals(g.getSnake().getDirection(), Direction.Right);
        k.setKeyCode(KeyEvent.VK_D);
        c.keyPressed(k);
        Assert.assertEquals(g.getSnake().getDirection(), Direction.Right);
    }

    @Test
    public void testSetGameStates() {
        GameState g = new GameState(10, 10, new Snake(3,3));
        Controller c = new Controller(g);
        Button b = new Button();
        KeyEvent k = new KeyEvent(b, 0,0,0,KeyEvent.VK_P,'a');
        c.keyPressed(k);
        Assert.assertTrue(g.isPaused());
        k.setKeyCode(KeyEvent.VK_P);
        c.keyPressed(k);
        Assert.assertTrue(!g.isPaused());
        k.setKeyCode(KeyEvent.VK_R);
        c.keyPressed(k);
        Assert.assertTrue(g.isReadyToRestart());
    }
}