package slogo;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Controller.Controller;
import slogo.Controller.ViewController;
import slogo.View.Screens.GameScreen;

public class ScreenController {
  private GameScreen gameScreen;
  private Controller modelController;
  private ViewController viewController;

  private Stage stage;

  /**
   * Class constructor
   *
   * @param stage current stage
   */
  public ScreenController(Stage stage) {
    this.stage = stage;
  }

  /**
   * Called by handler function in splash screen to continue setup
   */
  public void launchGame(Color color, String language) throws ClassNotFoundException {
    gameScreen = new GameScreen(stage, language, color);
    stage.setScene(gameScreen.makeScene(1000, 750));
    this.modelController = new Controller();
    this.viewController = new ViewController(gameScreen);
    modelController.setViewController(viewController);
    gameScreen.getCommandBoxView().setController(modelController);
  }

  public GameScreen getGameScreen() {
    return gameScreen;
  }
  public void setGameScreen(GameScreen screen){
    gameScreen = screen;
  }
}
