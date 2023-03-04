package slogo;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import slogo.Controller.Controller;
import slogo.Controller.ViewController;
import slogo.View.Screens.GameScreen;
import slogo.View.Screens.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenController {

  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String SCREEN_RESOURCES = "Screens";

  private GameScreen gameScreen;
  private Controller modelController;
  private ViewController viewController;

  private ObservableList<Screen> screens;
  private ResourceBundle screenResources;

  /**
   * Class constructor
   *
   * @param stage current stage
   */
  public ScreenController(Stage stage) {
    screenResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + SCREEN_RESOURCES);
  }

  /**
   * Called by handler function in splash screen to continue setup
   * @param gameScreen initialized game screen
   */
  public void setGameScreen(GameScreen gameScreen) throws ClassNotFoundException {
    this.gameScreen = gameScreen;
    this.modelController = new Controller();
    this.viewController = new ViewController(gameScreen);
    modelController.setViewController(viewController);
    gameScreen.getCommandBoxView().setController(modelController);
  }
}
