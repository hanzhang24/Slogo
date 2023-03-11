package Node;
import slogo.Node.*;

import org.junit.jupiter.api.Test;
import slogo.Node.NodeCategories.Constant;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NodeTest {

    @Test
    void testConstantNode() throws Exception {
        Node constantNode = new Constant("1.23");
        Node constantNode2 = new Constant(1.23);
        assertEquals(constantNode.execute().getNumeric(), 0);
        assertEquals(constantNode2.execute().getNumeric(), 1.23);
    }

    @Test
    void testVariableNode() {
        
    }

    @Test
    void testCommandNode() {

    }

}
