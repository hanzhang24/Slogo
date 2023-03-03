package Model;

import static IntegrationTests.SimpleProgramTests.loadInputLinesFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.Model.ModelTracker;
import slogo.Payload.ViewPayloadManager.ViewPayload;
import java.util.ResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ModelTrackerTest {

  private static final String EXCEPTIONS_PATH = "Model.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private ModelTracker modelTracker;

  @Nested
  @DisplayName("Configuration Tests")
  class ConfigurationTests {

    /**
     * Checks if the modelTracker has all default parameters
     */
    void checkDefaultParameters() {
      assertEquals(0.0, modelTracker.getAvatarX());
      assertEquals(0.0, modelTracker.getAvatarY());
      assertEquals(0.0, modelTracker.getAvatarRotation());
      assertEquals("black", modelTracker.getAvatarPenColor());
      assertEquals(true, modelTracker.getAvatarIsPenDown());
    }

    @BeforeEach
    void setup() {
      modelTracker = new ModelTracker("DefaultParameters");
    }

    @Test
    void testDefaultConstructor() {
      modelTracker = new ModelTracker();
      checkDefaultParameters();
    }

    @Test
    void testOpenAndClose() {
      modelTracker.startOp();
      modelTracker.endOp("", new ArrayList<>());
      checkDefaultParameters();
    }

    @Test
    void testInitializeBadDefaultParameters() {
      Exception exception = assertThrows(NumberFormatException.class,
          () -> modelTracker = new ModelTracker("DefaultParametersBadType"));
      String expected = String.format(EXCEPTIONS.getString("ConfigurationParsingError"), "AvatarX");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testForgotToStartOp() {
      Exception exception = assertThrows(RuntimeException.class,
          () -> modelTracker.setAvatarPosition(13.2, 3.2));
      String expected = EXCEPTIONS.getString("StartOpNotCalledError");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testForgotToEndLastOp() {
      modelTracker.startOp();
      modelTracker.getAvatarY();

      Exception exception = assertThrows(RuntimeException.class, () -> modelTracker.startOp());
      String expected = EXCEPTIONS.getString("EndOpNotCalledError");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testBailedUpdatesNotPushed() {
      modelTracker.startOp();
      modelTracker.setAvatarPenColor("blue");
      modelTracker.bail();
      checkDefaultParameters();
    }

    @Test
    void testBailThenRun() {
      modelTracker.startOp();
      modelTracker.bail();

      modelTracker.startOp();
      modelTracker.endOp("", new ArrayList<>());

      checkDefaultParameters();
    }
  }

  @Nested
  @DisplayName("Run Payload Tests")
  class RunPayloadTests {

    void assertPayloadContains(ViewPayload viewPayload, String key) {
      assertTrue(viewPayload.toString().contains(key));
    }

    @BeforeEach
    void setup() {
      modelTracker = new ModelTracker("DefaultParameters");
    }

    @Test
    void testRunSuccessfulPayload() {
      modelTracker.startOp();

      double r = modelTracker.getAvatarRotation();
      r += 90;
      modelTracker.setAvatarRotation(r);

      String penColor = modelTracker.getAvatarPenColor();
      penColor = "blue";
      modelTracker.setAvatarPenColor(penColor);

      double x = modelTracker.getAvatarX();
      double y = modelTracker.getAvatarY();
      x += 3;
      y += 6;
      modelTracker.setAvatarPosition(x, y);

      double x2 = modelTracker.getAvatarX();
      double y2 = modelTracker.getAvatarY();
      x2 += 3;
      y2 += 6;
      modelTracker.setAvatarPosition(x2, y2);

      modelTracker.setUserVariable("a", 112);
      modelTracker.setAvatarPenDown(false);

      ViewPayload viewPayload = modelTracker.endOp("", new ArrayList<>());
      System.out.println(viewPayload);

      assertEquals(112.0, modelTracker.getUserVariable("a"));
      assertEquals("blue", modelTracker.getAvatarPenColor());
      assertEquals(6.0, modelTracker.getAvatarX());
      assertEquals(12.0, modelTracker.getAvatarY());
    }

    @Test
    void testSetUserVariables() {
      modelTracker.startOp();
      modelTracker.setUserVariable("a", 5);
      double a = modelTracker.getUserVariable("a");
      a += 20;
      modelTracker.setUserVariable("a", a);
      modelTracker.endOp("", new ArrayList<>());

      assertEquals(25.0, modelTracker.getUserVariable("a"));
    }

    @Test
    void testGetAllUserVariables() {
      Map<String, Double> userVars = new HashMap<>();
      userVars.put("a", 5.0);
      userVars.put("b", 6.0);
      userVars.put("c", 7.0);

      modelTracker.startOp();
      for (String key : userVars.keySet()) {
        modelTracker.setUserVariable(key, userVars.get(key));
      }
      modelTracker.endOp("", new ArrayList<>());

      assertEquals(userVars, modelTracker.getAllUserVariables());
    }

    @Test
    void testSetNonexistentAvatarID() {
      modelTracker.startOp();
      Exception exception = assertThrows(RuntimeException.class,
          () -> modelTracker.setCurrentAvatar(12));
      String expected = EXCEPTIONS.getString("NonexistentAvatarError");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testHistoryAndReturnValues() {
      String userInput = "fd 50\nfd 100";
      List<Double> returnValues = new ArrayList<>();
      returnValues.add(50.0);
      returnValues.add(100.0);

      modelTracker.startOp();
      modelTracker.setAvatarPosition(50, 0);
      modelTracker.setAvatarPosition(150, 0);
      ViewPayload viewPayload = modelTracker.endOp(userInput, returnValues);

      assertPayloadContains(viewPayload, userInput);
      assertPayloadContains(viewPayload, "50.0");
      assertPayloadContains(viewPayload, "100.0");
    }

    @Test
    void testGetAllHistory() {
      List<String> inputs = loadInputLinesFromFile("grid.txt");
      for (String input : inputs) {
        modelTracker.startOp();
        // fake doing something successfully
        modelTracker.endOp(input, new ArrayList<>());
      }
      assertEquals(inputs, modelTracker.getAllHistory());
    }
  }
}
