package View.Screens;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SlogoInitializer;
import slogo.View.Screens.GameScreen;
import slogo.View.Screens.SplashScreen;
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

  private GameScreen thisScreen;
  private Node button;
  private TextArea area;
  private Button clear;

  @Override
  public void start (Stage stage) {
    thisScreen = new GameScreen(stage, DEFAULT_LANGUAGE, Color.BLACK);
    stage.setScene(thisScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    stage.setTitle(TITLE);
    stage.show();

    button = lookup("#Run").query();
    area =  lookup("#Text-Box").query();
    clear = lookup("#Clear").query();
  }
@Test
  void testClear(){
    writeInputTo(area, "Test");
    clickOn(clear);
    assertEquals(area.getText(), "");
  }
@Test
  void testUpdatePosition(){
    thisScreen.updateAvatarPosXY(10,10);
    assertEquals(285, thisScreen.getAvatar().getXCor());
    assertEquals(285, thisScreen.getAvatar().getYCor());
  }
@Test
  void testUpdatePenStatus(){
    Boolean test = false;
    thisScreen.updateAvatarIsPenDown(test);
    assertEquals(test, thisScreen.getAvatar().getPenActive());
    Color PenTest = Color.RED;
    thisScreen.updatePenColor(PenTest);
    assertEquals(PenTest, thisScreen.getAvatar().getColor());
}

}