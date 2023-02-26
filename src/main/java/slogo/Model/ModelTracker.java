/**
 * TODO: expose setReturnValue, work on History class,
 *       consider enumerations for things like colors and isPenDown,
 *       consider creating an avatar class
 */
package slogo.Model;

import slogo.Payload.ViewPayloadManager.ChangeLog;
import slogo.Payload.ViewPayloadManager.ViewPayload;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Alec Liu The ModelTracker class is the default implementation of the model. It manages
 * storage and access to default and user parameters, and throws exceptions for unexpected
 * behavior.
 */
public class ModelTracker implements Model {
  private static final String EXCEPTIONS_PATH = "Model.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private static final String AVATAR_X = "AvatarX";
  private static final String AVATAR_Y = "AvatarY";
  private static final String POSITION_CODE = "AvatarPosition";
  private Avatar avatar;
  private Map<String, String> userParameters;
  private Map<String, String> workspace;
  private ViewPayload viewPayload;

  /**
   * Class constructor
   */
  public ModelTracker(String defaultParametersFilename) {
    avatar = new Avatar(defaultParametersFilename, EXCEPTIONS);
    userParameters = new HashMap<>();
    workspace = null;
  }

  /**
   * Initializes the proper workspace for a new Controller operation
   */
  public void startOp() {
    checkPreviousOperationClosed();
    viewPayload = new ViewPayload();
    initializeWorkspace();
  }

  /**
   * Initializes a workspace copy of all model parameters and user parameters that is safe work
   * with.
   */
  private void initializeWorkspace() {
    workspace = new HashMap<>();
    workspace.putAll(avatar.getAllDefaultElements());
    workspace.putAll(userParameters);
  }

  /**
   * End a Controller operation, signifying a success batch of work has been finished. All updates
   * are committed to the original data.
   */
  public ViewPayload endOp() {
    checkCurrentOperationConfigured();

    pushWorkspaceUpdates();
    workspace = null;

    viewPayload.addCommand(new ChangeLog("History"));
    return viewPayload;
  }

  /**
   * Pushes all updates to model parameters and user parameters in the workspace back to the Model
   */
  private void pushWorkspaceUpdates() {
    for (String key : workspace.keySet()) {
      if (avatar.contains(key)) {
        avatar.put(key, workspace.get(key));
      } else {
        userParameters.put(key, workspace.get(key));
      }
    }
    workspace = null;
  }

  /**
   * End a Controller operation abruptly, signifying an unsuccessful batch of work. All updates are
   * rejected
   */
  public void bail() {
    viewPayload = null;
    workspace = null;
  }

  /**
   * Protects against cases when startOp() was not called before working with the Model
   */
  private void checkCurrentOperationConfigured() {
    if (workspace == null) {
      throw new RuntimeException(EXCEPTIONS.getString("StartOpNotCalled"));
    }
  }

  /**
   * Protects against cases when endOp() was not called before closing the previous operation
   */
  private void checkPreviousOperationClosed() {
    if (workspace != null) {
      throw new RuntimeException(EXCEPTIONS.getString("EndOpNotCalled"));
    }
  }

  /**
   * Gets a value as a String
   *
   * @param key name of the variable
   * @return value of the variable
   */
  public String getString(String key) {
    checkCurrentOperationConfigured();
    return workspace.get(key);
  }

  /**
   * Gets a value as a double
   *
   * @param key name of the variable
   * @return value of the variable
   * @throws NumberFormatException if the variable represents a String, not a double
   */
  public double getNumber(String key) {
    checkCurrentOperationConfigured();
    try {
      return Double.parseDouble(workspace.get(key));
    } catch (NumberFormatException numberFormatException) {
      throw new NumberFormatException(EXCEPTIONS.getString("ReadStringAsDouble"));
    }
  }

  /**
   * Sets the value of a parameter
   *
   * @param key   name of the parameter
   * @param value value of the parameter
   * @throws NumberFormatException if the method tries to reassign a default parameter's type
   */
  public void setValue(String key, String value) {
    checkCurrentOperationConfigured();
    if (avatar.contains(key)) {
      if (avatar.checkAppropriateType(key, value)) {
        workspace.put(key, value);
        viewPayload.addCommand(new ChangeLog(key, value));
      } else {
        throw new NumberFormatException(
            String.format(EXCEPTIONS.getString("DefaultTypeReassignment"), key));
      }
    } else {
      workspace.put(key, value);
    }

  }

  /**
   * Sets the value of a parameter
   *
   * @param key   name of the parameter
   * @param value value of the parameter
   * @throws NumberFormatException if the method tries to reassign a default parameter's type
   */
  public void setValue(String key, double value) {
    checkCurrentOperationConfigured();
    if (avatar.contains(key)) {
      if (avatar.checkAppropriateType(key, value)) {
        workspace.put(key, value + "");
        viewPayload.addCommand(new ChangeLog(key, value));
      } else {
        throw new NumberFormatException(
            String.format(EXCEPTIONS.getString("DefaultTypeReassignment"), key));
      }
    } else {
      workspace.put(key, value + "");
    }
  }

  /**
   * Simultaneously update the Avatar's x and y position - might be expanded to execute an arbitrary
   * number of simultaneous updates
   *
   * @param avatarX avatar's new x position
   * @param avatarY avatar's new y position
   */
  public void setPosition(double avatarX, double avatarY) {
    checkCurrentOperationConfigured();
    workspace.put(AVATAR_X, avatarX + "");
    workspace.put(AVATAR_Y, avatarY + "");
    viewPayload.addCommand(new ChangeLog(POSITION_CODE, avatarX, avatarY));
  }


  /**
   * Retrieves a copy of all user declared parameters. Works inside or outside active operation.
   *
   * @return map of all user parameters
   */
  public Map<String, String> getAllUserParameters() {
    Map<String, String> userParametersCollection = new HashMap<>();
    if (workspace != null) {
      for (String key : workspace.keySet()) {
        if (!avatar.contains(key)) {
          userParametersCollection.put(key, workspace.get(key));
        }
      }
    } else {
      userParametersCollection.putAll(userParameters);
    }
    return userParametersCollection;
  }

  /**
   * Creates a copy of the Model state
   *
   * @return copy of the current variables in the Model
   */
  public Map<String, String> getBackendState() {
    Map<String, String> backendCopy = new HashMap<>();
    backendCopy.putAll(avatar.getAllDefaultElements());
    backendCopy.putAll(userParameters);
    return backendCopy;
  }

  public void forward(double distance) throws NoSuchMethodException {
    System.out.println("Forward registered");
    throw new NoSuchMethodException("Not implemented yet");
  }

  public void rotate(double degrees) throws NoSuchMethodException {
    System.out.println("Rotation registered");
    throw new NoSuchMethodException("Not implemented yet");
  }
}
