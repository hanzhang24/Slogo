package slogo.Payload.ViewPayloadManager;

import slogo.Payload.Payload;
import slogo.Payload.ViewPayloadManager.ViewCommands.ViewCommand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import slogo.Payload.ViewPayloadManager.ViewCommands.ViewCommandFactory;

/**
 * @author Alec Liu
 * The ViewPayload class is a simplified, linear sequence of update instructions
 * generated in the Model and ran in the ViewController.
 */
public class ViewPayload implements Payload, Iterable<ViewCommand> {
  private ViewCommandFactory viewCommandFactory;
  private List<ViewCommand> commandsList;

  /**
   * Class constructor
   */
  public ViewPayload() {
    commandsList = new ArrayList<>();
    viewCommandFactory = new ViewCommandFactory();
  }

  /**
   * Adds an instruction to the list
   *
   * @param changeLog given instruction
   */
  public void addCommand(ChangeLog changeLog) {
    commandsList.add(viewCommandFactory.createViewCommand(changeLog));
  }

  /**
   * Iterator constructor
   *
   * @return iterator for the View Controller to use
   */
  @Override
  public Iterator<ViewCommand> iterator() {
    return new ViewPayloadIterator();
  }

  class ViewPayloadIterator implements Iterator<ViewCommand> {

    int index = 0;

    /**
     * Checks if there is a next element in the ViewPayload
     *
     * @return whether there is a next object
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
    public ViewCommand next() {
      ViewCommand viewCommand = commandsList.get(index);
      index++;
      return viewCommand;
    }
  }

  /**
   * Generates String representation of the ViewPayload object
   *
   * @return the String representation
   */
  public String toString() {
    StringBuilder payloadDescription = new StringBuilder("ViewPayload{\n");
    for (ViewCommand viewCommand : commandsList) {
      payloadDescription.append(viewCommand.getDescription() + ":" + viewCommand.getParameters() + "\n");
    }
    payloadDescription.append("}\n");
    return payloadDescription.toString();
  }
}
