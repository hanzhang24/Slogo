package View.Screens;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
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
    thisScreen = new SplashScreen(stage, DEFAULT_LANGUAGE, LANGUAGE_OPTIONS);
    stage.setScene(thisScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    stage.setTitle(TITLE);
    stage.show();

    languages = lookup("#Language-Box").query();
    button = lookup("#Go-Button").query();
    color = lookup("#Color-Selector").query();


  }

  @Test
  void testValidLanguage() {
    ComboBox<String> options = lookup("#Language-Box").query();
    String expected = "English";
    select(options, expected);
    // THEN, check label text has been updated to match input
    assertEquals(expected, options.getValue());
  }

  @Test
  void testValidColor() {
    ColorPicker picker = lookup("#Color-Selector").query();
    Color expected = Color.RED;
    // GIVEN, app first starts up
    // WHEN, color picker is used
    setValue(picker, expected);
    // THEN, check label text has been updated to match input
    assertEquals(expected, picker.getValue());
  }



}