package slogo;

import java.awt.Dimension;
import java.util.SortedMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import slogo.Controller.Controller;
import slogo.Controller.ViewController;
import slogo.View.Screens.GameScreen;
import slogo.View.Screens.SplashScreen;

public class SlogoInitializer {

  private static final String TITLE = "SLogo Team 7";
  private static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
  private static final String DEFAULT_LANGUAGE = "English";
  private static final ObservableList<String> LANGUAGE_OPTIONS =
      FXCollections.observableArrayList(
          "English",
          "Spanish"
      );
  private GameScreen gameScreen;
  private Controller modelController;
  private ViewController viewController;

  /**
   * Class constructor
   *
   * @param stage current stage
   */
  public SlogoInitializer(Stage stage) {
    initialize(stage);
  }

  /**
   * Declare initial splash screen
   *
   * @param stage current stage
   */
  private void initialize(Stage stage) {
    SplashScreen splashScreen = new SplashScreen(stage, DEFAULT_LANGUAGE, LANGUAGE_OPTIONS, this);
    stage.setScene(splashScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    stage.setTitle(TITLE);
    stage.show();
  }

  /**
   * Called by handler function in splash screen to continue setup
   * @param gameScreen initialized game screen
   */
  public void setGameScreen(GameScreen gameScreen) {
    this.gameScreen = gameScreen;
    // this.modelController = new Controller();
    this.viewController = new ViewController(gameScreen);
    // Controller.setViewController(viewController);
    //gameScreen.getCommandBoxView().setController(modelController);
  }
}
