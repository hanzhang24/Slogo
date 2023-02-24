package Payload.ViewPayloadManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Meant to be specifically for the View pipeline (not for the Controller), as it has a simplified
 * command structure/flow
 */
public class ChangeLog {

  private String name;
  private List<String> parametersList;

  /**
   * Class constructor. Encodes certain commands that need zero parameters
   * @param name name of the instruction
   */
  public ChangeLog(String name){
    this.name = name;
    this.parametersList = new ArrayList<>();
  }

  /**
   * Class constructor
   * @param name name of the instruction or variable it references
   * @param parameters values of parameters
   */
  public ChangeLog(String name, String... parameters) {
    this.name = name;
    this.parametersList = new ArrayList<>();
    for(String str : parameters){
      parametersList.add(str);
    }
  }

  /**
   * Class constructor
   * @param name name of the variable it references
   * @param parameters values of parameters
   */
  public ChangeLog(String name, double... parameters) {
    this.name = name;
    this.parametersList = new ArrayList<>();
    for(double num : parameters){
      parametersList.add(num + "");
    }
  }
  /**
   * Getter method
   * @return name of the instruction
   */
  public String getName() {
    return name;
  }

  /**
   * Setter method
   * @param name new name of the instruction
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * Getter method
   *
   * @return the list of parameters
   */
  public List<String> getParametersList(){
    return parametersList;
  }
}
