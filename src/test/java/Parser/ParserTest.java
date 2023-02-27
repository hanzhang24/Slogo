package Parser;

import org.junit.jupiter.api.Test;
import slogo.Model.ModelTracker;
import slogo.Node.NodeValue;
import slogo.Parser.CommandManager;
import slogo.Parser.Parser;
import slogo.Node.NodeCategories.*;
import slogo.Node.Node;
import slogo.Model.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    void testForwardParse() throws ClassNotFoundException {

        CommandManager commandManager = new CommandManager();
        Model model = new ModelTracker();
        Parser parser = new Parser(){};
        Node root = parser.parseInput("fd 50");
        root.initContext(model);
        model.startOp();
        NodeValue result = root.execute();
        model.endOp();

        assertEquals(result.getNumeric(), 50);
        System.out.println(model.getAvatarX());
        System.out.println(model.getAvatarY());
    }
}
