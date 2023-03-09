package View.Screens;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.assertj.core.api.Assert;
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

  private SplashScreen thisScreen;
  private ComboBox languages;
  private Node button;
  private ColorPicker color;


  @Override
  public void start (Stage stage) {
    thisScreen = new SplashScreen(DEFAULT_LANGUAGE, LANGUAGE_OPTIONS, new ScreenController(stage));
    stage.setScene(thisScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    stage.setTitle(TITLE);
    stage.show();

    languages = lookup("#Language-Box").query();
    button = lookup("#Go-Button").query();
    color = lookup("#Color-Selector").query();
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

//  @Test
//  void testValuesPassedToGameScreen() {
//    String expectedLanguage = "English";
//    select(languages, expectedLanguage);
//    Color expectedColor = Color.RED;
//    setValue(color, expectedColor);
//    clickOn(button);
////    assertEquals(expectedLanguage, GameScreen.);
//  }
//  @Test
//  void testThrowsRuntimeError(){
//    Exception noLanguage = assertThrows(NullPointerException.class, () -> clickOn(button));
//    String ErrorMessage = "You have not selected an Language";
//    assertEquals(noLanguage.getMessage(), ErrorMessage);
//  }
}