package slogo.Payload.ViewPayloadManager.ViewCommands;

import slogo.View.Screens.GameScreen;

import java.util.List;

/**
 * @author Alec Liu The Command class is a parent class for each View command. These Commands are
 * generically run in the ViewController by calling command.execute().
 */
public abstract class Command {

  GameScreen gameScreen;
  List<String> parameters;

  /**
   * Class constructor
   *
   * @param parameters list of parameters
   */
  public Command(List<String> parameters) {
    this.parameters = parameters;
  }

  /**
   * Method to set GameScreen. Called by the ViewController
   *
   * @param gameScreen
   */
  public void setGameScreen(GameScreen gameScreen) {
    this.gameScreen = gameScreen;
  }

  /**
   * Runs specified command
   */
  public abstract void execute();

  /**
   * Return the name of the command
   *
   * @return command name
   */
  public abstract String getName();

  /**
   * Return the parameters in the command
   *
   * @return parameters list
   */
  public List<String> getParameters() {
    return parameters;
  }
}
