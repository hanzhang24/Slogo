package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.ResourceBundle;
import slogo.View.Screens.GameScreen;

import java.util.List;

/**
 * @author Alec Liu
 * The View Command class is a parent class for each View command. These Commands
 * are generically run in the ViewController by calling command.execute(). See each subclass for
 * specific details on implementation.
 */
public abstract class ViewCommand {

  private static final String EXCEPTIONS_PATH = "Payload.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private static final String DESCRIPTIONS_PATH = "Payload.ViewCommandDescriptions";
  private static final ResourceBundle DESCRIPTIONS = ResourceBundle.getBundle(DESCRIPTIONS_PATH);
  protected int externalID;
  GameScreen gameScreen;
  List<String> parameters;

  /**
   * Class constructor
   *
   * @param parameters list of parameters
   */
  public ViewCommand(List<String> parameters, int externalID) {
    this.parameters = parameters;
    this.externalID = externalID;
  }

  /**
   * Method to set GameScreen. Called by the ViewController
   *
   * @param gameScreen sets the Game Screen to modify
   */
  public void setGameScreen(GameScreen gameScreen) {
    this.gameScreen = gameScreen;
  }

  /**
   * Checks that there are valid parameters before execution
   */
  protected void checkParameters() {
    if (parameters == null || parameters.size() == 0) {
      throw new RuntimeException(EXCEPTIONS.getString("InvalidParametersError"));
    }
  }

  /**
   * Checks for valid parameters, then attempts to execute the command
   */
  public void run() {
    checkParameters();
    executeSpecificCommand();
  }

  /**
   * Executes command-specific code
   */
  abstract void executeSpecificCommand();

  /**
   * Return the name of the command
   *
   * @return command name
   */
  public String getDescription() {
    return DESCRIPTIONS.getString(this.getClass().getName());
  }

  /**
   * Return the parameters in the command
   *
   * @return parameters list
   */
  public List<String> getParameters() {
    return parameters;
  }
}
