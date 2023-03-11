package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ResourceBundle;
import slogo.Payload.ViewPayloadManager.ChangeLog;

/**
 * @author Alec Liu
 * The ViewCommandFactory is a helper class for the ViewPayload, which uses this
 * class to translate temproary ChangeLogs into real commands for the View.
 */
public class ViewCommandFactory {

  private static final String COMMANDS_PATH = "slogo.Payload.ViewPayloadManager.ViewCommands.Update";
  private static final String EXCEPTIONS_PATH = "Payload.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);

  public ViewCommand createViewCommand(ChangeLog changeLog) {
    try {
      Class<?> command = Class.forName(COMMANDS_PATH + changeLog.getName());
      Constructor<?> constructor = command.getDeclaredConstructor(List.class, int.class);
      return (ViewCommand) constructor.newInstance(changeLog.getParametersList(),
          changeLog.getExternalID());
    } catch (Exception e) {
      throw new RuntimeException(EXCEPTIONS.getString("UnknownCommandCodeError"));
    }
  }
}
