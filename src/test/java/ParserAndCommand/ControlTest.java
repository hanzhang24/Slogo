package ParserAndCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Float.Precision;
import slogo.Model.Model;
import slogo.Model.ModelTracker;
import slogo.Parser.CommandManager;
import slogo.Parser.Parser;

import slogo.Node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControlTest {
    private Parser parser = null;
    private Model model = null;
    private CommandManager commandManager = null;

    private void print(String str) {
        System.out.println(str);
    }

    private void print(double val) {
        System.out.println(val);
    }

    private void print(boolean bool) {System.out.println(bool);}
    @BeforeEach
    void init() throws Exception {
        commandManager = new CommandManager();
        parser = new Parser(commandManager);
        model = new ModelTracker();
        model.startOp();
    }

    @Test
    void testDoTimes() throws Exception {
        Node root = parser.parseInput("dotimes [:x 5] [fd :x]");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 15);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);
        assertEquals(model.getUserVariable(":x"), 5);
    }

    @Test
    void testFor() throws Exception {
        Node root = parser.parseInput("for [:x 1 5 2] [fd :x]");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 9);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);
        assertEquals(model.getUserVariable(":x"), 5);
    }

    @Test
    void testIf() throws Exception {
        Node root = parser.parseInput("if [> 3 2] [fd 10] if [> 2 3] [fd 100]");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 10);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);
    }
    @Test
    void testIfElse() throws Exception {
        Node root = parser.parseInput("ifelse [> 3 2] [fd 100] [fd 10]");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 100);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);

        model.setAvatarPosition(0,0);
        root = parser.parseInput("ifelse [> 2 3] [fd 100] [fd 10]");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 10);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);
    }

    @Test
    void testRepeat() throws Exception {
        Node root = parser.parseInput("repeat 5 [fd :repcount]");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 15);
        assertEquals(model.getAvatarY(), 0);
        assertEquals(model.getAvatarRotation(), 0);
    }

    @Test
    void testSetVariable() throws Exception{
        Node root = parser.parseInput("set :abc 420");
        root.initContext(model);
        root.execute();
        assertEquals(420, model.getUserVariable(":abc"));
    }

    @Test
    void testCustom() throws Exception{
        Node root = parser.parseInput("to halfsq [:len] [fd :len rt 90 fd :len rt 90] halfsq 50");
        root.initContext(model);
        root.execute();
        assertEquals(model.getAvatarX(), 50);
        assertEquals(model.getAvatarY(), -50);
        assertEquals(model.getAvatarRotation(), -180);
    }

}
