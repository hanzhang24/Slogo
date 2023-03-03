package IntegrationTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SlogoInitializer;

public class SimpleProgramTests {
  public static final String TEST_SCRIPT_PATH = "/TestScripts/";
  private SlogoInitializer slogoInitializer;

  /**
   * Load commands into List of Strings
   * @param filename test script file name
   * @return list of command strings
   */
  public static List<String> loadInputLinesFromFile(String filename){
    Scanner input = new Scanner(
        SimpleProgramTests.class.getResourceAsStream(TEST_SCRIPT_PATH + filename));
    List<String> elementList = new ArrayList<>();
    while (input.hasNextLine()) {
      elementList.add(input.nextLine());
    }
    return elementList;
  }

  /**
   * Loads commands into a SINGLE String
   * @param filename test script file name
   * @return command string
   */
  private String loadAsOneInputFromFile(String filename){
    Scanner input = new Scanner(
        SimpleProgramTests.class.getResourceAsStream(TEST_SCRIPT_PATH + filename));
    String userInput = "";
    while (input.hasNextLine()) {
      userInput += input.nextLine() + "\n";
    }
    return userInput;
  }
  @BeforeEach
  void setup(){
    SlogoInitializer slogoInitializer = new SlogoInitializer(new Stage()); // needs completion from someone in view
  }

  @Test
  void testForward(){
    // simulate text box input for fd 50
    // String commands = loadAsOneInputFromFile("grid.txt");
    //System.out.println(commands);
  }
}
