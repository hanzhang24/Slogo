package Parser;

import org.junit.jupiter.api.Test;
import slogo.Parser.Parser;
import slogo.Node.NodeCategories.*;
import slogo.Node.Node;
public class ParserTest {

    @Test
    void testForwardParse() {
        Parser parser = new Parser(){};
        Node root = parser.parseInput("fd 50");

        


    }
}
