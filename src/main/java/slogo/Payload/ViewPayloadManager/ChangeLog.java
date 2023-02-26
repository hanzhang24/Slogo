package slogo.Payload.ViewPayloadManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alec Liu The ChangeLog object is built specifically for the View pipeline (not for the
 * Controller). When variables are modified in the Model, ChangeLogs are created to store
 * information about which variable and what information should be updated. ChangeLogs are
 * translated into Commands by the ViewPayload object.
 */
public class ChangeLog {

  private String name;
  private List<String> parametersList;

  /**
   * Class constructor. Encodes certain commands that need zero parameters
   *
   * @param name name of the instruction
   */
  public ChangeLog(String name) {
    this.name = name;
    this.parametersList = new ArrayList<>();
  }

  /**
   * Class constructor
   *
   * @param name       name of the instruction or variable it references
   * @param parameters values of parameters
   */
  public ChangeLog(String name, String... parameters) {
    this.name = name;
    this.parametersList = new ArrayList<>();
    for (String str : parameters) {
      parametersList.add(str);
    }
  }

  /**
   * Class constructor
   *
   * @param name       name of the variable it references
   * @param parameters values of parameters
   */
  public ChangeLog(String name, double... parameters) {
    this.name = name;
    this.parametersList = new ArrayList<>();
    for (double num : parameters) {
      parametersList.add(num + "");
    }
  }

  /**
   * Getter method
   *
   * @return name of the instruction
   */
  public String getName() {
    return name;
  }

  /**
   * Getter method
   *
   * @return the list of parameters
   */
  public List<String> getParametersList() {
    return parametersList;
  }
}
