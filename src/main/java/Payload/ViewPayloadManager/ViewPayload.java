package Payload.ViewPayloadManager;

import Payload.Payload;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class ViewPayload implements Payload, Iterable<Instruction> {

  private static final String VIEW_METHODS_PATH = "View.ViewControllerMethods";
  private static final ResourceBundle METHODS = ResourceBundle.getBundle(VIEW_METHODS_PATH);
  List<Instruction> instructionList;

  /**
   * Class constructor
   */
  public ViewPayload() {
    instructionList = new ArrayList<>();
  }

  /**
   * Adds an instruction to the list
   * @param instruction given instruction
   */
  public void addInstruction(Instruction instruction){
    instruction.setName(METHODS.getString(instruction.getName()));
    instructionList.add(instruction);
  }

  /**
   * Iterator constructor
   *
   * @return iterator for the View Controller to use
   */
  @Override
  public Iterator<Instruction> iterator() {
    return new ViewPayloadIterator();
  }

  class ViewPayloadIterator implements Iterator<Instruction> {

    int index = 0;

    /**
     * Checks if there is a next element in the ViewPayload
     *
     * @return
     */
    @Override
    public boolean hasNext() {
      return index < instructionList.size();
    }

    /**
     * Gives the next element in the ViewPayload
     *
     * @return next Instruction
     */
    @Override
    public Instruction next() {
      Instruction instr = instructionList.get(index);
      index++;
      return instr;
    }
  }

  /**
   * Generates String representation of the ViewPayload object
   *
   * @return
   */
  public String toString() {
    String payloadDescription = "ViewPayload{\n";
    for(Instruction instr : instructionList){
      payloadDescription += instr.getName() + ":" + instr.getParametersList() + "\n";
    }
    payloadDescription += "}\n";
    return payloadDescription;
  }
}
