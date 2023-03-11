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

public class LogicalTest {

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
    void testAnd() throws Exception {
        Node root = parser.parseInput("and 0 1");
        root.initContext(model);
        NodeValue nv = root.execute();
        assertEquals(false, Precision.asBoolean(nv.getNumeric()));

        root = parser.parseInput("and 1 1");
        root.initContext(model);
        nv = root.execute();
        assertEquals(true, Precision.asBoolean(nv.getNumeric()));
    }

    @Test
    void testOr() throws Exception{
        Node root = parser.parseInput("or 0 1");
        root.initContext(model);
        NodeValue nv = root.execute();
        assertEquals(true, Precision.asBoolean(nv.getNumeric()));

        root = parser.parseInput("or 0 0");
        root.initContext(model);
        nv = root.execute();
        assertEquals(false, Precision.asBoolean(nv.getNumeric()));
    }

    @Test
    void testComparison() throws Exception {
        Node root = parser.parseInput(">= 0 1");
        root.initContext(model);
        NodeValue nv = root.execute();
        assertEquals(false, Precision.asBoolean(nv.getNumeric()));

        root = parser.parseInput("> 0 1");
        root.initContext(model);
        nv = root.execute();
        assertEquals(false, Precision.asBoolean(nv.getNumeric()));

        root = parser.parseInput("< 0 1");
        root.initContext(model);
        nv = root.execute();
        assertEquals(true, Precision.asBoolean(nv.getNumeric()));

        root = parser.parseInput("<= 0 1");
        root.initContext(model);
        nv = root.execute();
        assertEquals(true, Precision.asBoolean(nv.getNumeric()));

        root = parser.parseInput("== 0 1");
        root.initContext(model);
        nv = root.execute();
        assertEquals(false, Precision.asBoolean(nv.getNumeric()));

        root = parser.parseInput("!= 0 1");
        root.initContext(model);
        nv = root.execute();
        assertEquals(true, Precision.asBoolean(nv.getNumeric()));
    }
}
