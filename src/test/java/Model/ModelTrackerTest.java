package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.ResourceBundle;
import org.assertj.core.internal.bytebuddy.matcher.StringMatcher.Mode;
import org.junit.jupiter.api.AfterEach;
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
      Map<String, String> backendCopy = modelTracker.getBackendState();
      assertEquals("0.0", backendCopy.get("AvatarX"));
      assertEquals("0.0", backendCopy.get("AvatarY"));
      assertEquals("0.0", backendCopy.get("AvatarRotation"));
      assertEquals("black", backendCopy.get("AvatarPenColor"));
    }
    @BeforeEach
    void setup(){
      modelTracker = new ModelTracker("DefaultParameters");
    }

    @Test
    void openAndClose(){
      modelTracker.startOp();
      modelTracker.endOp();
      checkDefaultParameters();
    }

    @Test
    void initializeBadDefaultParameters(){
      Exception exception = assertThrows(NumberFormatException.class, () -> {
        modelTracker = new ModelTracker("DefaultParametersBadType");
      });
      String expected = String.format(EXCEPTIONS.getString("ConfigurationParseDoubleError"), "AvatarX");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void forgotToStartOp(){
      Exception exception = assertThrows(RuntimeException.class, () -> {
        modelTracker.getNumber("AvatarX");
      });
      String expected = EXCEPTIONS.getString("StartOpNotCalled");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void forgotToEndLastOp(){
      modelTracker.startOp();
      modelTracker.getNumber("AvatarX");

      Exception exception = assertThrows(RuntimeException.class, () -> {
        modelTracker.startOp();
      });
      String expected = EXCEPTIONS.getString("EndOpNotCalled");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void bailedUpdatesNotPushed(){
      modelTracker.startOp();
      modelTracker.setValue("AvatarPenColor", "blue");
      modelTracker.bail();
      checkDefaultParameters();
    }

    @Test
    void bailThenRun(){
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
    void runSuccessfulPayload(){
      modelTracker.startOp();
      double d = modelTracker.getNumber("AvatarX");
      d += 50;
      modelTracker.setValue("AvatarX", d);
      String penColor = modelTracker.getString("AvatarPenColor");
      penColor = "blue";
      modelTracker.setValue("AvatarPenColor", penColor);
      modelTracker.setPosition(3, 6);
      modelTracker.setValue("a", 112);
      modelTracker.endOp();

      Map<String, String> backendCopy = modelTracker.getBackendState();
      assertEquals("112.0", backendCopy.get("a"));
      assertEquals("blue", backendCopy.get("AvatarPenColor"));
      assertEquals("3.0", backendCopy.get("AvatarX"));
      assertEquals("6.0", backendCopy.get("AvatarY"));
    }

    @Test
    void assignDefaultStringToDouble(){
      modelTracker.startOp();
      Exception exception = assertThrows(NumberFormatException.class, () -> {
        modelTracker.setValue("AvatarPenColor", 4.0);
      });
      String expected = String.format(EXCEPTIONS.getString("DefaultTypeReassignment"), "AvatarPenColor");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }

    @Test
    void assignDefaultDoubleToString(){
      modelTracker.startOp();
      Exception exception = assertThrows(NumberFormatException.class, () -> {
        modelTracker.setValue("AvatarX", "blue");
      });
      String expected = String.format(EXCEPTIONS.getString("DefaultTypeReassignment"), "AvatarX");
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }
  }


}
