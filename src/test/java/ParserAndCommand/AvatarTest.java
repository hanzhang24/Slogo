package ParserAndCommand;

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

    private void print(String str) {
        System.out.println(str);
    }

    private void print(double val) {
        System.out.println(val);
    }
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
        Node root = parser.parseInput("pu");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarIsPenDown(), false);

        root = parser.parseInput("pd");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarIsPenDown(), true);
    }

    @Test
    void testRotate() throws Exception {
        Node root = parser.parseInput("rt 50");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarRotation(), -50);

        root = parser.parseInput("lt 60");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarRotation(), 10);
    }

    @Test
    void testHeading() throws Exception {
        Node root = parser.parseInput("setheading 42069");
        root.initContext(model);
        root.execute();
        assertEquals(42069%360, model.getAvatarRotation());
    }

    @Test
    void testTowards() throws Exception {
        Node root = parser.parseInput("towards 0 69");
        root.initContext(model);
        root.execute();
        assertEquals(90, model.getAvatarRotation());

        root = parser.parseInput("towards 69 0");
        root.initContext(model);
        root.execute();
        assertEquals(0, model.getAvatarRotation());

        root = parser.parseInput("towards 420 420");
        root.initContext(model);
        root.execute();
        assertEquals(45, model.getAvatarRotation());

        root = parser.parseInput("towards 3 4");
        root.initContext(model);
        root.execute();

        print(Math.toDegrees(Math.atan(4.0/3)));
        print(model.getAvatarRotation());

        assertEquals(Math.toDegrees(Math.atan(4.0/3)), model.getAvatarRotation());
    }
}
