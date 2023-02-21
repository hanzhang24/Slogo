package Payload;

import java.util.List;

public class Instruction {
  String name;
  List<String> parameters;
  public Instruction(String name, List<String> parameters){
    this.name = name;
    this.parameters = parameters;
  }
}
