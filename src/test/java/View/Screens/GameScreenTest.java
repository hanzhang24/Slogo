package View.Screens;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javax.swing.text.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import slogo.Controller.Controller;
import slogo.Controller.ViewController;
import slogo.ScreenController;
import slogo.View.Screens.GameScreen;
import util.DukeApplicationTest;


class GameScreenTest extends DukeApplicationTest {

  private static final String TITLE = "SLogo Team 6";
  private static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
  private static final String DEFAULT_LANGUAGE = "English";
  private static final ObservableList<String> LANGUAGE_OPTIONS =
      FXCollections.observableArrayList(
          "English",
          "Spanish"
      );

  private GameScreen thisScreen;
  private Node run;
  private TextArea area;
  private Button clear;

  private Controller modelController;
  private ViewController viewController;

  @Override
  public void start(Stage stage) throws ClassNotFoundException {
    //TODO replace with the ScreenController
    thisScreen = new GameScreen(stage, "English", Color.BLACK);
    stage.setScene(thisScreen.makeScene(750, 750));
    modelController = new Controller();
    viewController = new ViewController(thisScreen);
    modelController.setViewController(viewController);
    thisScreen.getCommandBoxView().setController(modelController);
    stage.show();

    run = lookup("#Run").query();
    area = lookup("#Text-Box").query();
    clear = lookup("#Clear").query();
  }

  @Test
  void testClear() {
    writeInputTo(area, "Test");
    clickOn(clear);
    assertEquals(area.getText(), "");
  }

  @Test
  void testUpdatePosition() {
    simulateAction(0, 0, () -> {
      thisScreen.updateAvatarPosXY(10, 10);
      assertEquals(285, thisScreen.getAvatar().getXCor());
      assertEquals(285, thisScreen.getAvatar().getYCor());
    });
  }

  @Test
  void testUpdatePenStatus() {
    Boolean test = false;
    thisScreen.updateAvatarIsPenDown(test);
    assertEquals(test, thisScreen.getAvatar().getPenActive());
    Color PenTest = Color.RED;
    thisScreen.updatePenColor(PenTest);
    assertEquals(PenTest, thisScreen.getAvatar().getColor());
  }
  @Nested
  @DisplayName("Run Payload Tests")
  class RunInputCommands {
    @Test
    void testInputFd50() {
      writeInputTo(area, "fd 50");
      clickOn(run);
      assertEquals(325, thisScreen.getAvatar().getXCor());
      assertEquals(275, thisScreen.getAvatar().getYCor());
    }
    @Test
    void testInputHome() {
      writeInputTo(area, "home");
      clickOn(run);
      assertEquals(275, thisScreen.getAvatar().getXCor());
      assertEquals(275, thisScreen.getAvatar().getYCor());
    }
    @Test
    void testInputMultiple50(){
      writeInputTo(area, "fd 50 \nfd 50");
      clickOn(run);
      assertEquals(375, thisScreen.getAvatar().getXCor());
      assertEquals(275, thisScreen.getAvatar().getYCor());
    }
    @Test
    void testInputForwardThenHome(){
      writeInputTo(area, "fd 50 \nhome");
      clickOn(run);
      assertEquals(275, thisScreen.getAvatar().getXCor());
      assertEquals(275, thisScreen.getAvatar().getYCor());
    }
  }

  @Nested
  @DisplayName("AnimationSpeed")
  class AnimationSpeed {

    private Slider animationSlider;
    private TextArea animationInput;


    @BeforeEach
    void setUp() {
      animationSlider = lookup("#Animation-Slider").query();
      animationInput = lookup("#Animation-Input").query();
    }

    @Test
    void testAnimationSlider(){
      //This line is required for a click to register as an event, otherwise javafx will just set
      //the value of the animation Slider but not increase
      //PLEASE DO NOT MOVE YOUR MOUSE DOING THIS TEST
      moveTo(animationSlider);
      setValue(animationSlider, 10);
      press(MouseButton.PRIMARY);
      release(MouseButton.PRIMARY);
      assertEquals(10, thisScreen.getAnimationSpeed());
    }
    @Test
    void testAnimationTextBox(){
      writeInputTo(animationInput, "20");
      press(KeyCode.ENTER);
      assertEquals(20, thisScreen.getAnimationSpeed());
    }
    @Test
    void testInvalidAnimationStringInput(){
      writeInputTo(animationInput, "20");
      press(KeyCode.ENTER);
      writeInputTo(animationInput, "Hi");
      press(KeyCode.ENTER);
      assertEquals(20, thisScreen.getAnimationSpeed());

    }
    void testInvalidNegativeInput(){

    }
  }
  @Nested
  @DisplayName("History Tests")
  class HistoryContainer{
    private ComboBox<String> historyOptions;
    private TextArea display;
    @BeforeEach
    void setUp(){
      historyOptions = lookup("#History-DropDown").query();
      display = lookup("#History-Display").query();
    }

    @Test
    void testChangeHistoryValue(){
      String expected = "Command History";
      select(historyOptions, expected);
    }
    @Test
    void testDisplayProperlyUpdates(){
      String expected = "Command History";
      select(historyOptions, expected);
      String input = "fd 50 \nhome";
      writeInputTo(area, input);
      assertEquals(display.getText(), input);
    }
  }
}