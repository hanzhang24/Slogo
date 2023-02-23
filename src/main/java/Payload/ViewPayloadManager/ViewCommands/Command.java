package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public abstract class Command {
  int gameScreen;
  List<String> parameters;

  /**
   * Class constructor
   * @param parameters list of parameters
   */
  public Command(List<String> parameters){
    this.parameters = parameters;
  }

  /**
   * Method to set GameScreen. Called by the ViewController
   * @param gameScreen
   */
  public void setGameScreen(int gameScreen){
    this.gameScreen = gameScreen;
  }

  /**
   * Runs specified command
   */
  public abstract void execute();

  /**
   * Return the name of the command
   * @return command name
   */
  public abstract String getName();

  /**
   * Return the parameters in the command
   * @return parameters list
   */
  public abstract List<String> getParameters();
}
