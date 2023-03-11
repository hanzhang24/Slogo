package Model;

import static IntegrationTests.SimpleProgramTests.loadInputLinesFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import slogo.Model.ModelTracker;
import slogo.Payload.ViewPayloadManager.ViewPayload;
import java.util.ResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Alec Liu Test class for ModelTracker. Verifies functionality.
 */
public class ModelTest {

  private static final String AVATAR_EXCEPTIONS_PATH = "Model.AvatarExceptions";
  private static final ResourceBundle AVATAR_EXCEPTIONS = ResourceBundle.getBundle(
      AVATAR_EXCEPTIONS_PATH);
  private static final String MODEL_EXCEPTIONS_PATH = "Model.ModelExceptions";
  private static final ResourceBundle MODEL_EXCEPTIONS = ResourceBundle.getBundle(
      MODEL_EXCEPTIONS_PATH);
  private final Random randomGenerator = new Random();
  private ModelTracker modelTracker;

  /**
   * Checks if two arrays are equal
   */
  void assertArrayEquals(int[] expected, int[] actual) {
    for (int i = 0; i < expected.length; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

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
      assertArrayEquals(new int[]{0, 0, 0}, modelTracker.getAvatarPenColor());
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
      String expected = String.format(AVATAR_EXCEPTIONS.getString("ConfigurationParsingError"),
          "AvatarX");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testForgotToStartOp() {
      Exception exception = assertThrows(RuntimeException.class,
          () -> modelTracker.setAvatarPosition(randomGenerator.nextDouble(),
              randomGenerator.nextDouble()));
      String expected = MODEL_EXCEPTIONS.getString("StartOpNotCalledError");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testForgotToEndLastOp() {
      modelTracker.startOp();
      modelTracker.getAvatarY();

      Exception exception = assertThrows(RuntimeException.class, () -> modelTracker.startOp());
      String expected = MODEL_EXCEPTIONS.getString("EndOpNotCalledError");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testBailedUpdatesNotPushed() {
      modelTracker.startOp();
      int[] RGB = modelTracker.getAvatarPenColor();
      double randRed = randomGenerator.nextInt(0, 255);
      double randGreen = randomGenerator.nextInt(0, 255);
      double randBlue = randomGenerator.nextInt(0, 255);
      modelTracker.setAvatarPenColor(randRed, randGreen, randBlue);
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
      r += randomGenerator.nextDouble();
      modelTracker.setAvatarRotation(r);

      double randRed = randomGenerator.nextInt(0, 255);
      double randGreen = randomGenerator.nextInt(0, 255);
      double randBlue = randomGenerator.nextInt(0, 255);
      modelTracker.setAvatarPenColor(randRed, randGreen, randBlue);

      double x = modelTracker.getAvatarX();
      double y = modelTracker.getAvatarY();
      x += randomGenerator.nextDouble();
      y += randomGenerator.nextDouble();
      modelTracker.setAvatarPosition(x, y);

      double x2 = modelTracker.getAvatarX();
      double y2 = modelTracker.getAvatarY();
      x2 += randomGenerator.nextDouble();
      y2 += randomGenerator.nextDouble();
      modelTracker.setAvatarPosition(x2, y2);

      double a = randomGenerator.nextDouble();
      modelTracker.setUserVariable("a", a);
      boolean b = randomGenerator.nextBoolean();
      modelTracker.setAvatarPenDown(b);

      ViewPayload viewPayload = modelTracker.endOp("", new ArrayList<>());
      System.out.println(viewPayload);

      assertEquals(r, modelTracker.getAvatarRotation());
      assertEquals(a, modelTracker.getUserVariable("a"));
      assertArrayEquals(new int[]{(int) randRed, (int) randGreen, (int) randBlue},
          modelTracker.getAvatarPenColor());
      assertEquals(x2, modelTracker.getAvatarX());
      assertEquals(y2, modelTracker.getAvatarY());
    }

    @Test
    void testSetUserVariables() {
      modelTracker.startOp();

      double a = randomGenerator.nextDouble();
      modelTracker.setUserVariable("a", a);
      a = modelTracker.getUserVariable("a");
      a += randomGenerator.nextDouble();
      modelTracker.setUserVariable("a", a);
      modelTracker.endOp("", new ArrayList<>());

      assertEquals(a, modelTracker.getUserVariable("a"));
    }

    @Test
    void testGetAllUserVariables() {
      Map<String, Double> userVars = new HashMap<>();
      double a = randomGenerator.nextDouble();
      userVars.put("a", a);

      double b = randomGenerator.nextDouble();
      userVars.put("b", b);

      double c = randomGenerator.nextDouble();
      userVars.put("c", c);

      modelTracker.startOp();
      for (String key : userVars.keySet()) {
        modelTracker.setUserVariable(key, userVars.get(key));
      }
      modelTracker.endOp("", new ArrayList<>());

      assertEquals(userVars, modelTracker.getAllUserVariables());
    }

    @Test
    void testGetNonexistentVariable() {
      modelTracker.startOp();
      double a = modelTracker.getUserVariable("a");
      modelTracker.endOp("", new ArrayList<>());

      assertEquals(0.0, a);

    }

    @Test
    void testSetNonexistentAvatarID() {
      modelTracker.startOp();
      Exception exception = assertThrows(RuntimeException.class,
          () -> modelTracker.setCurrentAvatarID(2));
      String expected = AVATAR_EXCEPTIONS.getString("NonexistentAvatarError");
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

    @Test
    void testSetVisibility() {
      modelTracker.startOp();
      modelTracker.setAvatarVisible(false);
      ViewPayload viewPayload = modelTracker.endOp("", new ArrayList<>());

      assertEquals(false, modelTracker.getAvatarVisible());
    }

    @Test
    void testClearScreen() {
      modelTracker.startOp();
      modelTracker.setAvatarPosition(randomGenerator.nextDouble(), randomGenerator.nextDouble());
      modelTracker.setAvatarRotation(randomGenerator.nextDouble());
      modelTracker.endOp("", new ArrayList<>());

      modelTracker.startOp();
      modelTracker.resetOrientation();
      modelTracker.endOp("", new ArrayList<>());

      assertEquals(0.0, modelTracker.getAvatarX());
      assertEquals(0.0, modelTracker.getAvatarY());
      assertEquals(0.0, modelTracker.getAvatarRotation());
    }

    @Test
    void testInitialActiveAvatars() {
      modelTracker.startOp();
      List<Integer> initialActive = modelTracker.getActiveAvatars();
      modelTracker.endOp("", new ArrayList<>());

      assertEquals(initialActive.size(), 1);
      assertEquals(initialActive.get(0), 1);
    }

    @Test
    void testCreateAvatars() {
      modelTracker.startOp();
      List<Integer> activeIDs = new ArrayList<>(List.of(1, 5, 7));
      modelTracker.setActiveAvatars(activeIDs);
      ViewPayload viewPayload = modelTracker.endOp("", new ArrayList<>());

      assertEquals(3, modelTracker.getActiveAvatars().size());
    }

    @Test
    void testManageAvatars() {
      modelTracker.startOp();
      List<Integer> activeIDs = new ArrayList<>(List.of(1, 2, 3));
      modelTracker.setActiveAvatars(activeIDs);
      modelTracker.setCurrentAvatarID(1);
      double x = randomGenerator.nextDouble();
      double y = randomGenerator.nextDouble();
      modelTracker.setAvatarPosition(x, y);
      modelTracker.setCurrentAvatarID(2);
      double rot = randomGenerator.nextDouble();
      modelTracker.setAvatarRotation(rot);
      modelTracker.setCurrentAvatarID(3);
      boolean penDown = randomGenerator.nextBoolean();
      modelTracker.setAvatarPenDown(penDown);
      ViewPayload viewPayload = modelTracker.endOp("", new ArrayList<>());

      modelTracker.startOp();
      modelTracker.setCurrentAvatarID(1);
      assertEquals(x, modelTracker.getAvatarX());
      assertEquals(y, modelTracker.getAvatarY());
      modelTracker.setCurrentAvatarID(2);
      assertEquals(rot, modelTracker.getAvatarRotation());
      modelTracker.setCurrentAvatarID(3);
      assertEquals(penDown, modelTracker.getAvatarIsPenDown());
      modelTracker.endOp("", new ArrayList<>());
    }

    @Test
    void testRecoverInitialAvatars() {
      modelTracker.startOp();
      modelTracker.setActiveAvatars(List.of(1, 2, 3, 10, 16));
      modelTracker.bail();

      assertEquals(1, modelTracker.getTotalNumberOfAvatars());
    }
  }
}
