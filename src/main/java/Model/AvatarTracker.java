/**
 * TODO: add change log support, test with another test class,
 *       consider changing everything to map implementation to allow for backup copies
 */
package Model;

import Payload.ViewPayloadManager.Instruction;
import Payload.ViewPayloadManager.ViewPayload;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Meant to hold parameters related to the AvatarTracker
 */
public class AvatarTracker {

  private static final String DEFAULT_PARAMETERS_PATH = "Model.DefaultParameters.properties";
  private static final ResourceBundle DEFAULT_PARAMETERS = ResourceBundle.getBundle(
      DEFAULT_PARAMETERS_PATH);
  private static final int X_INDEX = 0;
  private static final int Y_INDEX = 1;
  private static final int ROTATION_INDEX = 2;
  private static final int PEN_COLOR_INDEX = 3;
  public static final String AVATAR_POSITION = "AvatarPosition";
  private List<String> modelParameterNames;
  private Map<String, String> defaultVariables;
  private Map<String, String> userVariables;
  private Map<String, String> workspace;
  private List<Instruction> changeLog;


  /**
   * Class constructor
   */
  public AvatarTracker() {
    modelParameterNames = new ArrayList<>();
    setupInitialMaps();
    changeLog = null;
    workspace = null;
  }

  private void setupInitialMaps() {
    defaultVariables = new HashMap<>();
    addDefaultParameters();

    userVariables = new HashMap<>();
  }

  /**
   * Add all default parameters and values to the HashMap
   */
  private void addDefaultParameters() {
    modelParameterNames.addAll(DEFAULT_PARAMETERS.keySet());
    for (String name : modelParameterNames) {
      defaultVariables.put(name, DEFAULT_PARAMETERS.getString(name));
    }
  }

  /**
   * Opens a change log and creates workspace at the beginning of each operation conducted by the
   * Controller
   */
  public void startOp() {
    changeLog = new ArrayList<>();
    initializeWorkspace();
  }

  /**
   * Initializes a workspace copy of all model parameters and user variables that is safe work
   * with.
   */
  private void initializeWorkspace() {
    workspace = new HashMap<>();
    workspace.putAll(defaultVariables);
    workspace.putAll(userVariables);
  }

  public void endOp() {
    ViewPayload viewPayload = new ViewPayload(changeLog);
    changeLog = null;

    pushWorkspaceUpdates();
    workspace = null;

  }

  /**
   * Pushes all updates to model parameters and user variables in the workspace back to the Model
   */
  private void pushWorkspaceUpdates() {
    for (String key : workspace.keySet()) {
      if (defaultVariables.containsKey(key)) {
        defaultVariables.put(key, workspace.get(key));
      } else {
        userVariables.put(key, workspace.get(key));
      }
    }
    workspace = null;
  }

  /**
   * Getter method
   *
   * @return avatar's x position
   */
  public double getAvatarX() throws NumberFormatException {
    try {
      return Double.parseDouble(workspace.get(modelParameterNames.get(X_INDEX)));
    } catch (NumberFormatException numberFormatException) {
      workspace.put(modelParameterNames.get(X_INDEX),
          DEFAULT_PARAMETERS.getString(modelParameterNames.get(X_INDEX)));
      return Double.parseDouble(workspace.get(modelParameterNames.get(X_INDEX)));
    }
  }

  /**
   * Setter method
   *
   * @param avatarX avatar's new x position
   */
  public void setAvatarX(double avatarX) {
    String convertedX = avatarX + "";
    workspace.put(modelParameterNames.get(X_INDEX), convertedX);
    changeLog.add(new Instruction(modelParameterNames.get(X_INDEX), convertedX));
  }

  /**
   * Getter method
   *
   * @return avatar's y position
   */
  public double getAvatarY() {
    try {
      return Double.parseDouble(workspace.get(modelParameterNames.get(Y_INDEX)));
    } catch (NumberFormatException numberFormatException) {
      workspace.put(modelParameterNames.get(Y_INDEX),
          DEFAULT_PARAMETERS.getString(modelParameterNames.get(Y_INDEX)));
      return Double.parseDouble(workspace.get(modelParameterNames.get(Y_INDEX)));
    }
  }

  /**
   * Setter method
   *
   * @param avatarY avatar's new y-position
   */
  public void setAvatarY(double avatarY) {
    String convertedY = avatarY + "";
    workspace.put(modelParameterNames.get(Y_INDEX), convertedY);
    changeLog.add(new Instruction(modelParameterNames.get(Y_INDEX), convertedY));
  }

  /**
   * Sets a new avatar position simultaneously
   *
   * @param avatarX avatar's new x position
   * @param avatarY avatar's new y position
   */
  public void setAvatarPosition(double avatarX, double avatarY) {
    String convertedX = avatarX + "";
    String convertedY = avatarY + "";
    workspace.put(modelParameterNames.get(X_INDEX), convertedX);
    changeLog.add(new Instruction(AVATAR_POSITION, convertedX, convertedY));
  }

  /**
   * Getter method
   *
   * @return avatar's rotation
   */
  public double getAvatarRotation() {
    try {
      return Double.parseDouble(workspace.get(modelParameterNames.get(ROTATION_INDEX)));
    } catch (NumberFormatException numberFormatException) {
      workspace.put(modelParameterNames.get(ROTATION_INDEX),
          DEFAULT_PARAMETERS.getString(modelParameterNames.get(ROTATION_INDEX)));
      return Double.parseDouble(workspace.get(modelParameterNames.get(ROTATION_INDEX)));
    }
  }

  /**
   * Setter method
   *
   * @param avatarRotation avatar's new rotation
   */
  public void setAvatarRotation(double avatarRotation) {
    String convertedRotation = avatarRotation + "";
    workspace.put(modelParameterNames.get(ROTATION_INDEX), convertedRotation);
    changeLog.add(new Instruction(modelParameterNames.get(ROTATION_INDEX), convertedRotation));
  }

  /**
   * Getter method
   *
   * @return current pen color
   */
  public String getAvatarPenColor() {
    return workspace.get(modelParameterNames.get(PEN_COLOR_INDEX));
  }

  public void setAvatarPenColor(String avatarPenColor){
    avatarPenColor = avatarPenColor.toLowerCase();
    workspace.put(modelParameterNames.get(PEN_COLOR_INDEX), avatarPenColor);
    changeLog.add(new Instruction(modelParameterNames.get(PEN_COLOR_INDEX), avatarPenColor));
  }

}
