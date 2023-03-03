package Payload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Payload.ViewPayloadManager.ChangeLog;
import slogo.Payload.ViewPayloadManager.ViewCommands.ViewCommand;
import slogo.Payload.ViewPayloadManager.ViewCommands.ViewCommandFactory;
import slogo.Payload.ViewPayloadManager.ViewPayload;
import slogo.View.Screens.GameScreen;

/**
 * @author Alec Liu Test class for the ViewPayload and ChangeLog translation mechanics. Sits around
 * 80% coverage because the commands are not run on the View - full integration testing will happen
 * in a different package.
 */
public class ViewPayloadTest {

  private static final String KEY_CODES_PATH = "Model.KeyCodes";
  private static final ResourceBundle KEY_CODES = ResourceBundle.getBundle(KEY_CODES_PATH);
  private static final String EXCEPTIONS_PATH = "Payload.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private final Random randomGenerator = new Random();
  private ViewPayload viewPayload;
  private ViewCommandFactory viewCommandFactory = new ViewCommandFactory();

  void assertPayloadContains(ViewPayload viewPayload, String key) {
    assertTrue(viewPayload.toString().contains(key));
  }

  ViewCommand getCommand(String key) {
    return viewCommandFactory.createViewCommand(new ChangeLog(KEY_CODES.getString(key), ""));
  }

  @BeforeEach
  void setup() {
    viewPayload = new ViewPayload();
  }

  @Test
  void testChangeLogTypes() {
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("X")));
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("X"), ""));
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("X"), randomGenerator.nextDouble()));
    List<Double> vals = new ArrayList<>();
    vals.add(randomGenerator.nextDouble());
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("X"), vals));
    assertPayloadContains(viewPayload, getCommand("X").getName());
  }

  @Test
  void testValidCommandLookup() {
    for (String key : KEY_CODES.keySet()) {
      viewPayload.addCommand(new ChangeLog(KEY_CODES.getString(key), ""));
    }

    for (String key : KEY_CODES.keySet()) {
      assertPayloadContains(viewPayload, getCommand(key).getName());
    }
  }

  @Test
  void testInvalidCommand() {
    Exception exception = assertThrows(RuntimeException.class,
        () -> viewPayload.addCommand(new ChangeLog("", "")));
    String expected = String.format(EXCEPTIONS.getString("UnknownCommandCodeError"));
    String actual = exception.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  void testInvalidParameters() {
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("X"), new ArrayList<>()));
    for (ViewCommand vc : viewPayload) {
      Exception exception = assertThrows(RuntimeException.class,
          vc::run);
      String expected = String.format(EXCEPTIONS.getString("InvalidParametersError"));
      String actual = exception.getMessage();
      assertEquals(expected, actual);
    }
  }
}
