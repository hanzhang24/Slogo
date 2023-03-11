package View.Screens;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import slogo.ScreenController;
import slogo.View.Screens.SplashScreen;
import util.DukeApplicationTest;

class SplashScreenTest extends DukeApplicationTest {
  private static final String TITLE = "SLogo Team 6";
  private static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
  private static final String DEFAULT_LANGUAGE = "English";
  private static final ObservableList<String> LANGUAGE_OPTIONS  =
      FXCollections.observableArrayList(
          "English",
          "Spanish"
      );

  private ComboBox languages;
  private ColorPicker color;
  private Button button;

  @Override
  public void start (Stage stage) {
    SplashScreen splashScreen = new SplashScreen(stage, DEFAULT_LANGUAGE, LANGUAGE_OPTIONS);
    stage.setScene(splashScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    stage.setTitle(TITLE);
    stage.show();

    languages = lookup("#languagePicker").query();
    button = lookup("#GoCommand").query();
    color = lookup("#colorPicker").query();
  }

  @Test
  void testLanguageBox() {
    String expected = "English";
    select(languages, expected);
    // THEN, check label text has been updated to match input
    assertEquals(expected, languages.getValue());
  }

  @Test
  void testColorPicker() {
    Color expected = Color.RED;
    // GIVEN, app first starts up
    // WHEN, color picker is used
    setValue(color, expected);
    // THEN, check label text has been updated to match input
    assertEquals(expected, color.getValue());
  }
  @Test
  void testButtonError(){
    clickOn(button);
    String message = getDialogMessage();
    assertEquals("You have not selected a Language", message);
  }
}