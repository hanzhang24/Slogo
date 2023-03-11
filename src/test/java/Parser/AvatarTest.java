package Parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import slogo.Parser.*;
import slogo.Model.*;
import slogo.Node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AvatarTest {

    private Parser parser = null;
    private Model model = null;
    private CommandManager commandManager = null;
    @BeforeEach
    void init() throws Exception {
        commandManager = new CommandManager();
        parser = new Parser(commandManager);
        model = new ModelTracker();
        model.startOp();
    }

    @Test
    void testBack() throws Exception {
        Node root = parser.parseInput("bk 50");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), -50);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);
    }

    @Test
    void testForward() throws Exception {
        Node root = parser.parseInput("fd 50");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 50);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);
    }

    @Test
    void testGoto() throws Exception {
        Node root = parser.parseInput("goto 69 420");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 69);
        assertEquals(model.getAvatarY(), 420);
        assertEquals(model.getAvatarRotation(), 0);
    }

    @Test
    void testHome() throws Exception {
        Node root = parser.parseInput("goto 69 420 home");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 0);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);
    }

    @Test
    void testPen() throws Exception {
        Node root = parser.parseInput("goto 69 420");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 69);
        assertEquals(model.getAvatarY(), 420);
        assertEquals(model.getAvatarRotation(), 0);
    }
}
