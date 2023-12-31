package View.Screens;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import slogo.Controller.Controller;
import slogo.Controller.ViewController;
import slogo.View.Screens.GameScreen;
import util.DukeApplicationTest;


class GameScreenTest extends DukeApplicationTest {


  private GameScreen thisScreen;
  private Node run;
  private TextArea area;
  private Button clear;

  @Override
  public void start(Stage stage) throws ClassNotFoundException {

    thisScreen = new GameScreen(stage, "English", Color.BLACK);
    stage.setScene(thisScreen.makeScene(750, 750));
    Controller modelController = new Controller();
    ViewController viewController = new ViewController(thisScreen);
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
      thisScreen.updateAvatarPosXY(1,10, 10);
      assertEquals(260.6066017177983, thisScreen.getAvatar(1).getXCor());
      assertEquals(239.3933982822017, thisScreen.getAvatar(1).getYCor());
    });
  }

  @Test
  void testUpdatePenStatus() {
    boolean test = false;
    thisScreen.updateAvatarIsPenDown(1, test);
     assertEquals(test, thisScreen.getAvatar(1).getPenActive());
    Color PenTest = Color.RED;
    thisScreen.updatePenColor(1, PenTest);
    assertEquals(PenTest, thisScreen.getAvatar(1).getColor());
  }
  @Nested
  @DisplayName("Run Payload Tests")
  class RunInputCommands {
    @Test
    void testInputFd50() {
      writeInputTo(area, "fd 50");
      clickOn(run);
      assertEquals(300, thisScreen.getAvatar(1).getXCor());
      assertEquals(250, thisScreen.getAvatar(1).getYCor());
    }
    @Test
    void testInputHome() {
      writeInputTo(area, "home");
      clickOn(run);
      assertEquals(250, thisScreen.getAvatar(1).getXCor());
      assertEquals(250, thisScreen.getAvatar(1).getYCor());
    }
    @Test
    void testInputMultiple50(){
      writeInputTo(area, "fd 50 \nfd 50");
      clickOn(run);
      assertEquals(350, thisScreen.getAvatar(1).getXCor());
      assertEquals(250, thisScreen.getAvatar(1).getYCor());
    }
    @Test
    void testInputForwardThenHome(){
      writeInputTo(area, "fd 50 \nhome");
      clickOn(run);
      assertEquals(250, thisScreen.getAvatar(1).getXCor());
      assertEquals(250, thisScreen.getAvatar(1).getYCor());
    }
    @Test
    void testInputBoundaryFd251(){
      writeInputTo(area, "fd 251");
      clickOn(run);
      assertEquals(1, thisScreen.getAvatar(1).getXCor());
      assertEquals(250, thisScreen.getAvatar(1).getYCor());
    }
    @Test
    void testRT(){
      writeInputTo(area, "rt 90");
      clickOn(run);
      assertEquals(180, thisScreen.getAvatar(1).getRot());
    }
    @Test
    void testLT(){
      writeInputTo(area, "LT 90");
      clickOn(run);
      assertEquals(0, thisScreen.getAvatar(1).getRot());
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
      writeInputTo(animationInput, "Hi");
      press(KeyCode.ENTER);
      String message = getDialogMessage();
      assertEquals("Invalid Input, must be a number", message);
      assertEquals(60, thisScreen.getAnimationSpeed());
    }
    @Test
    void testInvalidNegativeInput(){
      writeInputTo(animationInput, "-10");
      press(KeyCode.ENTER);
      String message = getDialogMessage();
      assertEquals("Input must be from 1-100", message);
      assertEquals(60, thisScreen.getAnimationSpeed());
    }
  }
  @Nested
  @DisplayName("History Tests")
  class HistoryContainer{
    private ComboBox<String> historyOptions;
    private TextArea display;
    @BeforeEach
    void setUp(){
      historyOptions = lookup("#History-Selector").query();
      display = lookup("#History-Display").query();
    }
    @Test
    void testChangeHistoryValue(){
      String expected = "Commands";
      select(historyOptions, expected);
    }
    @Test
    void testDisplayProperlyUpdates(){
      String input = "fd 50";
      writeInputTo(area, input);
      clickOn(run);
      String expected = "Commands";
      select(historyOptions, expected);
      String buffer = "\n------------\n";
      assertEquals(display.getText(), input+buffer);
    }
  }
}