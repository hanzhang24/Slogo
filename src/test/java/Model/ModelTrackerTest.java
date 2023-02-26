package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Payload.ViewPayloadManager.ViewPayload;
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
  class ConfigurationTests{

    /**
     * Checks if the modelTracker has all default parameters
     */
    void checkDefaultParameters(){
      assertEquals(0.0, modelTracker.getAvatarX());
      assertEquals(0.0, modelTracker.getAvatarY());
      assertEquals(0.0, modelTracker.getAvatarRotation());
      assertEquals("black", modelTracker.getAvatarPenColor());
      assertEquals(true, modelTracker.getAvatarIsPenDown());
    }
    @BeforeEach
    void setup(){
      modelTracker = new ModelTracker("DefaultParameters");
    }

    @Test
    void testOpenAndClose(){
      modelTracker.startOp();
      modelTracker.endOp();
      checkDefaultParameters();
    }

    @Test
    void testInitializeBadDefaultParameters(){
      Exception exception = assertThrows(NumberFormatException.class, () -> {
        modelTracker = new ModelTracker("DefaultParametersBadType");
      });
      String expected = String.format(EXCEPTIONS.getString("ConfigurationParsingError"), "AvatarX");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testForgotToStartOp(){
      Exception exception = assertThrows(RuntimeException.class, () -> {
        modelTracker.setAvatarX(13.2);
      });
      String expected = EXCEPTIONS.getString("StartOpNotCalledError");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testForgotToEndLastOp(){
      modelTracker.startOp();
      modelTracker.getAvatarY();

      Exception exception = assertThrows(RuntimeException.class, () -> {
        modelTracker.startOp();
      });
      String expected = EXCEPTIONS.getString("EndOpNotCalledError");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void testBailedUpdatesNotPushed(){
      modelTracker.startOp();
      modelTracker.setAvatarPenColor("blue");
      modelTracker.bail();
      checkDefaultParameters();
    }

    @Test
    void testBailThenRun(){
      modelTracker.startOp();
      modelTracker.bail();

      modelTracker.startOp();
      modelTracker.endOp();

      checkDefaultParameters();
    }
  }

  @Nested
  @DisplayName("Run Payload Tests")
  class RunPayloadTests {
    @BeforeEach
    void setup(){
      modelTracker = new ModelTracker("DefaultParameters");
    }

    @Test
    void testRunSuccessfulPayload(){
      modelTracker.startOp();

      double d = modelTracker.getAvatarX();
      d += 50;
      modelTracker.setAvatarX(d);

      double e = modelTracker.getAvatarY();
      e += 30;
      modelTracker.setAvatarY(e);

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

      modelTracker.setUserVariable("a", 112);
      modelTracker.setAvatarPenDown(false);

      ViewPayload viewPayload = modelTracker.endOp();
      System.out.println(viewPayload);

      assertEquals(112.0, modelTracker.getUserVariable("a"));
      assertEquals("blue", modelTracker.getAvatarPenColor());
      assertEquals(53.0, modelTracker.getAvatarX());
      assertEquals(36.0, modelTracker.getAvatarY());
    }

    @Test
    void testSetUserVariables(){
      modelTracker.startOp();
      modelTracker.setUserVariable("a", 5);
      double a = modelTracker.getUserVariable("a");
      a += 20;
      modelTracker.setUserVariable("a", a);
      modelTracker.endOp();

      assertEquals(25.0, modelTracker.getUserVariable("a"));
    }

    @Test
    void setNonexistentAvatarID(){
      modelTracker.startOp();
      Exception exception = assertThrows(RuntimeException.class, () -> {
        modelTracker.setCurrentAvatar(12);
      });
      String expected = EXCEPTIONS.getString("NonexistentAvatarError");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }
  }
}
