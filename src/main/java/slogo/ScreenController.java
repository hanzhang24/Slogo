package slogo;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import slogo.Controller.Controller;
import slogo.Controller.ViewController;
import slogo.View.Screens.GameScreen;
import slogo.View.Screens.Screen;

public class ScreenController {
  private GameScreen gameScreen;
  private Controller modelController;
  private ViewController viewController;

  private ObservableList<Screen> screens;

  /**
   * Class constructor
   *
   * @param stage current stage
   */
  public ScreenController(Stage stage) {

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
