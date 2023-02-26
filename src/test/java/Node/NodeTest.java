package Node;
import slogo.Node.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NodeTest {

    @Test
    void testConstantNode() {
        Node constantNode = new Constant("1.23");
        Node constantNode2 = new Constant(1.23);
        assertEquals(constantNode.execute().getNumeric(), 1.23);
        assertEquals(constantNode2.execute().getNumeric(),1.23);
    }
}
