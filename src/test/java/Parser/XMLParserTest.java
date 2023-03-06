package Parser;

import org.junit.jupiter.api.Test;
import slogo.Parser.XMLParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMLParserTest {

    @Test
    void testXMLParser() {
        XMLParser parsedXML = new XMLParser("src/main/resources/Parser/example.xml");

        assertEquals("single_step", parsedXML.getType());
        assertEquals("subtract", parsedXML.getName());
        assertEquals("Sum", parsedXML.getFunctionClass());
        assertEquals("2", parsedXML.getNumParameters());
        assertEquals("a,b", parsedXML.getParams());
        assertEquals("Number", parsedXML.getReturns());
        assertEquals("Subtracts two numbers", parsedXML.getDescription());
        assertEquals(null, parsedXML.getDocumentation());
    }
}
