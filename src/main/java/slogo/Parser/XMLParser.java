package slogo.Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLParser {
    private HashMap<String, String> map;

    public XMLParser(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            map = new HashMap<String, String>();
            NodeList nodeList = doc.getElementsByTagName("*");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                if (element.getChildNodes().getLength() == 1) {
                    String name = element.getNodeName();
                    String value = element.getTextContent();
                    map.put(name, value);
                    // System.out.println(name);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
            //TODO add pop-up error message
        }
    }

    public String getType() {
        return map.get("type");
    }

    public String getName() {
        return map.get("name");
    }

    public String getSyntax() {return map.get("syntax");}

    public String getFunctionClass() {
        return map.get("function-class");
    }

    public String getNumParameters() {
        return map.get("numparameters");
    }

    public String getParams() {
        return map.get("params");
    }

    public String getReturns() {
        return map.get("returns");
    }

    public String getDescription() {
        return map.get("description");
    }

    public String getDocumentation() {
        return map.get("documentation");
    }

    public String getTag(String tagName) {
        return map.get(tagName);
    }
}
