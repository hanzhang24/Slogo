package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ResourceBundle;
import slogo.Payload.ViewPayloadManager.ChangeLog;

public class ViewCommandFactory {
  private static final String COMMANDS_PATH = "slogo.Payload.ViewPayloadManager.ViewCommands.Update";
  private static final String EXCEPTIONS_PATH = "Payload.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  public ViewCommand createViewCommand(ChangeLog changeLog){
    try {
      Class<?> command = Class.forName(COMMANDS_PATH + changeLog.getName());
      Constructor<?> constructor = command.getDeclaredConstructor(List.class);
      return (ViewCommand) constructor.newInstance(changeLog.getParametersList());
    } catch (Exception e) {
      throw new RuntimeException(EXCEPTIONS.getString("UnknownCommandCodeError"));
    }
  }
}
