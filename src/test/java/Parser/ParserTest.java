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
    void testForwardParse() throws Exception {

        CommandManager commandManager = new CommandManager();
        Model model = new ModelTracker();
        Parser parser = new Parser(commandManager){};
        Node root = parser.parseInput("fd 50");
        root.initContext(model);
        model.startOp();
        NodeValue result = root.execute();
        // model.endOp();

        assertEquals(result.getNumeric(), 50);
        assertEquals(model.getAvatarX(), 50);
        assertEquals(model.getAvatarY(), 0);
    }
}
