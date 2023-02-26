package Payload.ViewPayloadManager;

import Payload.Payload;
import Payload.ViewPayloadManager.ViewCommands.Command;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Alec Liu The ViewPayload class is a simplified, linear sequence of update instructions
 * generated in the Model and ran in the ViewController.
 */
public class ViewPayload implements Payload, Iterable<Command> {

  private static final String COMMANDS_PATH = "Payload.ViewPayloadManager.ViewCommands.update";
  private static final String VIEW_METHODS_PATH = "Payload.ViewControllerMethods";
  private static final String EXCEPTIONS_PATH = "Payload.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private static final ResourceBundle METHODS = ResourceBundle.getBundle(VIEW_METHODS_PATH);
  List<Command> commandsList;

  /**
   * Class constructor
   */
  public ViewPayload() {
    commandsList = new ArrayList<>();
  }

  /**
   * Adds an instruction to the list
   *
   * @param changeLog given instruction
   */
  public void addCommand(ChangeLog changeLog) {
    // do the reflection here
    try {
      Class<?> command = Class.forName(COMMANDS_PATH + changeLog.getName());
      Constructor<?> constructor = command.getDeclaredConstructor(List.class);
      commandsList.add((Command) constructor.newInstance(changeLog.getParametersList()));
    } catch (Exception e) {
      throw new RuntimeException(EXCEPTIONS.getString("UnknownCommandCodeError"));
    }

  }

  /**
   * Iterator constructor
   *
   * @return iterator for the View Controller to use
   */
  @Override
  public Iterator<Command> iterator() {
    return new ViewPayloadIterator();
  }

  class ViewPayloadIterator implements Iterator<Command> {

    int index = 0;

    /**
     * Checks if there is a next element in the ViewPayload
     *
     * @return
     */
    @Override
    public boolean hasNext() {
      return index < commandsList.size();
    }

    /**
     * Gives the next element in the ViewPayload
     *
     * @return next Instruction
     */
    @Override
    public Command next() {
      Command command = commandsList.get(index);
      index++;
      return command;
    }
  }

  /**
   * Generates String representation of the ViewPayload object
   *
   * @return the String representation
   */
  public String toString() {
    String payloadDescription = "ViewPayload{\n";
    for (Command command : commandsList) {
      payloadDescription += command.getName() + ":" + command.getParameters() + "\n";
    }
    payloadDescription += "}\n";
    return payloadDescription;
  }
}
