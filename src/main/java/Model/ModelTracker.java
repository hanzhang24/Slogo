/**
 * TODO: expose setReturnValue, work on History class,
 *       consider enumerations for things like colors and isPenDown,
 *       consider creating an avatar class
 */
package Model;

import Payload.ViewPayloadManager.ChangeLog;
import Payload.ViewPayloadManager.ViewPayload;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Alec Liu The ModelTracker class is the default implementation of the model. It manages
 * storage and access to default and user parameters, and throws exceptions for unexpected
 * behavior.
 */
public class ModelTracker implements Model {

  private static final String AVATAR_X_CODE = "AvatarX";
  private static final String AVATAR_Y_CODE = "AvatarY";
  private static final String POSITION_CODE = "AvatarPosition";
  private static final String AVATAR_ROTATION_CODE = "AvatarRotation";
  private static final String AVATAR_PEN_COLOR_CODE = "AvatarPenColor";
  private static final String AVATAR_IS_PEN_DOWN_CODE = "AvatarIsPenDown";
  private static final String EXCEPTIONS_PATH = "Model.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private List<Avatar> avatarList;
  private int currentAvatarID;
  private OperationSignatureGenerator operationSignatureGenerator;
  private int operationSignature;
  private Map<String, String> userParameters;
  private Map<String, String> workspace;
  private ViewPayload viewPayload;


  /**
   * Class constructor
   */
  public ModelTracker(String defaultParametersFilename) {
    initializeAvatars(defaultParametersFilename);
    userParameters = new HashMap<>();
    workspace = null;
    operationSignatureGenerator = new OperationSignatureGenerator();
    operationSignature = -1;

  }

  /**
   * Initialize avatar-related settings
   */
  private void initializeAvatars(String defaultParametersFilename) {
    Avatar initialAvatar = new Avatar(defaultParametersFilename, EXCEPTIONS);
    avatarList = new ArrayList<>();
    avatarList.add(initialAvatar);
    currentAvatarID = 0;
  }

  /**
   * Initializes the proper workspace for a new Controller operation, generating a new associated
   * operation signature
   */
  public void startOp() {
    checkPreviousOperationClosed();
    viewPayload = new ViewPayload();
    workspace = new HashMap<>();
    operationSignature = operationSignatureGenerator.generateOperationSignature();
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
      if (key.contains(operationSignature + "")) { // belongs to an avatar
        updateAvatar(key);
      } else {
        userParameters.put(key, workspace.get(key));
      }
    }
  }

  /**
   * Updates a specific Avatar with information associated with the given key
   *
   * @param key key that represents one updated Avatar value
   */
  private void updateAvatar(String key) {
    String[] splitKey = key.split("_");
    int id = Integer.parseInt(splitKey[1].substring(3));
    String parameter = splitKey[2];
    avatarList.get(id).setValue(parameter, workspace.get(key));
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
   * Gets if there is an active operation running
   *
   * @return if there is an active operation
   */
  private boolean activeOpRunning() {
    return workspace != null;
  }


  /**
   * Protects against cases when startOp() was not called before working with the Model
   */
  private void checkCurrentOperationConfigured() {
    if (!activeOpRunning()) {
      throw new RuntimeException(EXCEPTIONS.getString("StartOpNotCalledError"));
    }
  }

  /**
   * Protects against cases when endOp() was not called before closing the previous operation
   */
  private void checkPreviousOperationClosed() {
    if (activeOpRunning()) {
      throw new RuntimeException(EXCEPTIONS.getString("EndOpNotCalledError"));
    }
  }

  /**
   * Formats the lookup string
   *
   * @param key original lookup String
   * @return formatted lookup String
   */
  private String formatLookupString(String key) {
    return String.format("Signature:%d_ID:%d_%s", operationSignature, currentAvatarID, key);
  }

  /**
   * Switches the avatar to the one with the specified ID. By default, the ID is set to zero for the
   * initial avatar.
   *
   * @param id new avatar ID
   */
  @Override
  public void setCurrentAvatar(int id) {
    if(id >= 0 && id < avatarList.size()){
      currentAvatarID = id;
    } else {
      throw new RuntimeException(EXCEPTIONS.getString("NonexistentAvatarError"));
    }
  }

  /**
   * Get the x position of the current avatar
   *
   * @return avatar's x-position
   */
  @Override
  public double getAvatarX() {
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(AVATAR_X_CODE);
      if (workspace.containsKey(formattedKey)) {
        return Double.parseDouble(workspace.get(formattedKey));
      } else {
        return avatarList.get(currentAvatarID).getDouble(AVATAR_X_CODE);
      }
    } else {
      return avatarList.get(currentAvatarID).getDouble(AVATAR_X_CODE);
    }
  }

  /**
   * Get the y position of the current avatar
   *
   * @return avatar's y-position
   */
  @Override
  public double getAvatarY() {
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(AVATAR_Y_CODE);
      if (workspace.containsKey(formattedKey)) {
        return Double.parseDouble(workspace.get(formattedKey));
      } else {
        return avatarList.get(currentAvatarID).getDouble(AVATAR_Y_CODE);
      }
    } else {
      return avatarList.get(currentAvatarID).getDouble(AVATAR_Y_CODE);
    }
  }

  /**
   * Get the rotation of the current avatar
   *
   * @return avatar's
   */
  @Override
  public double getAvatarRotation() {
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(AVATAR_ROTATION_CODE);
      if (workspace.containsKey(formattedKey)) {
        return Double.parseDouble(workspace.get(formattedKey));
      } else {
        return avatarList.get(currentAvatarID).getDouble(AVATAR_ROTATION_CODE);
      }
    } else {
      return avatarList.get(currentAvatarID).getDouble(AVATAR_ROTATION_CODE);
    }
  }

  /**
   * Gets the pen color of the current avatar
   *
   * @return pen color
   */
  @Override
  public String getAvatarPenColor() {
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(AVATAR_PEN_COLOR_CODE);
      if (workspace.containsKey(formattedKey)) {
        return workspace.get(formattedKey);
      } else {
        return avatarList.get(currentAvatarID).getString(AVATAR_PEN_COLOR_CODE);
      }
    } else {
      return avatarList.get(currentAvatarID).getString(AVATAR_PEN_COLOR_CODE);
    }
  }

  /**
   * Gets the state of the pen
   *
   * @return if the pen is down
   */
  @Override
  public boolean getAvatarIsPenDown() {
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(AVATAR_IS_PEN_DOWN_CODE);
      if (workspace.containsKey(formattedKey)) {
        return Boolean.parseBoolean(workspace.get(formattedKey));
      } else {
        return avatarList.get(currentAvatarID).getBoolean(AVATAR_IS_PEN_DOWN_CODE);
      }
    } else {
      return avatarList.get(currentAvatarID).getBoolean(AVATAR_IS_PEN_DOWN_CODE);
    }
  }


  /**
   * Gets the value of the variable with the specified key
   *
   * @param key
   * @return the variable's value
   */
  @Override
  public double getUserVariable(String key) {
    if (activeOpRunning()) {
      if (workspace.containsKey(key)) {
        return Double.parseDouble(workspace.get(key));
      } else {
        return Double.parseDouble(userParameters.get(key));
      }
    } else {
      return Double.parseDouble(userParameters.get(key));
    }
  }

  /**
   * Sets the current avatar's x position
   *
   * @param x new x position
   */
  @Override
  public void setAvatarX(double x) {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(AVATAR_X_CODE), x + "");
    viewPayload.addCommand(new ChangeLog(AVATAR_X_CODE, x));
  }

  /**
   * Sets the current avatar's y position
   *
   * @param y new y position
   */
  @Override
  public void setAvatarY(double y) {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(AVATAR_Y_CODE), y + "");
    viewPayload.addCommand(new ChangeLog(AVATAR_X_CODE, y));
  }

  /**
   * Simultaneously update the current Avatar's x and y position
   *
   * @param x new x position
   * @param y new y position
   */
  @Override
  public void setAvatarPosition(double x, double y) {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(AVATAR_X_CODE), x + "");
    workspace.put(formatLookupString(AVATAR_Y_CODE), y + "");
    viewPayload.addCommand(new ChangeLog(POSITION_CODE, x, y));
  }

  /**
   * Sets the current avatar's rotation
   *
   * @param rotation new rotation
   */
  @Override
  public void setAvatarRotation(double rotation) {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(AVATAR_ROTATION_CODE), rotation + "");
    viewPayload.addCommand(new ChangeLog(AVATAR_ROTATION_CODE, rotation));
  }

  /**
   * Sets the current avatar's pen color
   *
   * @param color new color
   */
  @Override
  public void setAvatarPenColor(String color) {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(AVATAR_PEN_COLOR_CODE), color);
    viewPayload.addCommand(new ChangeLog(AVATAR_PEN_COLOR_CODE, color));
  }

  /**
   * Sets the current avatar's pen enable setting
   *
   * @param isPenDown new pen setting
   */
  @Override
  public void setAvatarPenDown(boolean isPenDown) {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(AVATAR_IS_PEN_DOWN_CODE), isPenDown + "");
    viewPayload.addCommand(new ChangeLog(AVATAR_IS_PEN_DOWN_CODE, isPenDown + ""));
  }

  /**
   * Sets the value of a user variable
   *
   * @param key   variable name
   * @param value variable value
   */
  @Override
  public void setUserVariable(String key, double value) {
    checkCurrentOperationConfigured();
    workspace.put(key, value + "");
  }
}
