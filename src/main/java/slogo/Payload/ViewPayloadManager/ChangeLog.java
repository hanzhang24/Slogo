package slogo.Payload.ViewPayloadManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alec Liu The ChangeLog object is built specifically for the View pipeline (not for the
 * Controller). When commands are modified in the Model, ChangeLogs are created to temporarily store
 * information about which command and what information should be updated. ChangeLogs are translated
 * into Commands by the ViewPayload object.
 */
public class ChangeLog {

  private static final int GENERAL_OP = -1;
  private String name;
  private List<String> parametersList;
  private int externalID;

  /**
   * Class constructor. Encodes certain commands that need zero parameters
   *
   * @param name name of the command
   */
  public ChangeLog(String name) {
    this.name = name;
    this.externalID = GENERAL_OP;
    this.parametersList = new ArrayList<>();
  }

  /**
   * Class constructor
   *
   * @param name       name of the command it references
   * @param parameters values of parameters
   */
  public ChangeLog(String name, String... parameters) {
    this.name = name;
    this.externalID = GENERAL_OP;
    this.parametersList = new ArrayList<>(List.of(parameters));
  }

  /**
   * Class constructor
   *
   * @param name       name of the command it references
   * @param id         externalID
   * @param parameters values of parameters
   */
  public ChangeLog(String name, int id, String... parameters) {
    this.name = name;
    this.externalID = id;
    this.parametersList = new ArrayList<>();
    for (String str : parameters) {
      parametersList.add(str);
    }
  }

  /**
   * Class constructor
   *
   * @param name       name of the command it references
   * @param parameters values of parameters
   * @params id        external ID
   */
  public ChangeLog(String name, int id, double... parameters) {
    this.name = name;
    this.externalID = id;
    this.parametersList = new ArrayList<>();
    for (double num : parameters) {
      parametersList.add(num + "");
    }
  }

  /**
   * Class constructor
   *
   * @param name       name of the command it references
   * @param parameters values of parameters
   */
  public ChangeLog(String name, List<Double> parameters) {
    this.name = name;
    this.externalID = GENERAL_OP;
    this.parametersList = new ArrayList<>();
    for (double num : parameters) {
      parametersList.add(num + "");
    }
  }


  /**
   * Getter method
   *
   * @return name of the command
   */
  public String getName() {
    return name;
  }

  /**
   * Getter method
   *
   * @return external ID
   */
  public int getExternalID() {
    return externalID;
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
