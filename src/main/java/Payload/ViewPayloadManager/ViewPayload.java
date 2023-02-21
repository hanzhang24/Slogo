package Payload;

import java.util.Iterator;
import java.util.List;

public class ViewPayload implements Payload, Iterable<Instruction> {
  List<Instruction> instructionList;

  @Override
  public Iterator<Instruction> iterator() {
    return null;
  }
}
