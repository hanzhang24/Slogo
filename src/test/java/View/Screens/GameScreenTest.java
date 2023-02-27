package View.Screens;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GameScreenTest extends DukeApplicationTest {

  private static final String TITLE = "SLogo Team 6";
  private static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
  private static final String DEFAULT_LANGUAGE = "English";
  private static final ObservableList<String> LANGUAGE_OPTIONS  =
      FXCollections.observableArrayList(
          "English",
          "Spanish"
      );

  private SplashScreen thisScreen;
  private Node button;
  private TextArea area;
  private Button clear;

  @Override
  public void start (Stage stage) {
    thisScreen = new SplashScreen(stage, DEFAULT_LANGUAGE, LANGUAGE_OPTIONS);
    stage.setScene(thisScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    stage.setTitle(TITLE);
    stage.show();
    button = lookup("#Go-Button").query();
    clickOn(button);
    area =  lookup("#Text-Box").query();
    clear = lookup("#Clear").query();
  }
@Test
  void testClear(){
    writeInputTo(area, "Test");
    clickOn(clear);
    assertEquals(area.getText(), "");
  }
}