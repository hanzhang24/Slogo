package slogo.Controller;

import slogo.Payload.ViewPayloadManager.ViewCommands.Command;
import slogo.Payload.ViewPayloadManager.ViewPayload;
import slogo.View.Screens.GameScreen;

/**
 * @author Alec Liu The ViewController class is responsible for modifying the View based on the
 * updates logged in the Model and delivered by the Payload.
 */
public class ViewController {

  private GameScreen gameScreen;

  /**
   * Class constructor
   * @param gameScreen GameScreen being managed
   */
  public ViewController(GameScreen gameScreen) {
    this.gameScreen = gameScreen;
  }

  /**
   * Runs all commands in the view payload
   *
   * @param viewPayload model-generated payload after one complete operation
   */
  public void runPayload(ViewPayload viewPayload) {
    for (Command command : viewPayload) {
      command.setGameScreen(gameScreen);
      command.execute();
    }
  }
}
