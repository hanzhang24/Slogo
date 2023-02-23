package Controller;

import Payload.ViewPayloadManager.ViewCommands.Command;
import Payload.ViewPayloadManager.ViewPayload;

public class ViewController {
  private int gameScreen;
  public ViewController(int gameScreen){
    this.gameScreen = gameScreen;
  }

  /**
   * Runs all commands in the view payload
   * @param viewPayload model-generated payload after one complete operation
   */
  public void runPayload(ViewPayload viewPayload){
    for(Command command : viewPayload){
      command.setGameScreen(gameScreen);
      command.execute();
    }
  }
}
