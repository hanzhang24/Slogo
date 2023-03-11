/**
 * TODO: clean up code, increase testing and doc, address history logging issues
 */
package slogo.Model;

import java.util.*;

import slogo.Model.AvatarManager.Avatar;
import slogo.Model.AvatarManager.AvatarGroupManager;
import slogo.Model.OperationFormat.OperationFormatter;
import slogo.Model.UpdateManager.History;
import slogo.Model.UpdateManager.OperationWorkspace;
import slogo.Payload.ViewPayloadManager.ChangeLog;
import slogo.Payload.ViewPayloadManager.ViewPayload;

/**
 * @author Alec Liu The ModelTracker class is the default implementation of the model. It primarily
 * manages access to the Model through a fixed process, and keeps temporary changes while the Model
 * is being actively modified. This class defers to other classes to help get and set Model
 * infromation, or to encode Model information to prevent collisions.
 */
public class ModelTracker implements Model {

  private static final String EXCEPTIONS_PATH = "Model.ModelExceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private static final String KEY_CODES_PATH = "Model.KeyCodes";
  private static final ResourceBundle KEY_CODES = ResourceBundle.getBundle(KEY_CODES_PATH);
  private static final String DEFAULT_PARAMETERS_FILENAME = "DefaultParameters";
  private static final String COLOR_REGEX = " ";
  private static final double UNSET_USER_VARIABLE_VALUE = 0.0;
  private AvatarGroupManager avatarGroupManager;
  private OperationFormatter operationFormatter;
  private OperationWorkspace operationWorkspace;
  private History history;
  private Map<String, Double> userVariables;
  private ViewPayload viewPayload;

  /**
   * Class constructor - default
   */
  public ModelTracker() {
    avatarGroupManager = new AvatarGroupManager(DEFAULT_PARAMETERS_FILENAME);
    initializePeripheralStructures();
  }

  /**
   * Class constructor - for custom filenames
   */
  public ModelTracker(String defaultParametersFilename) {
    avatarGroupManager = new AvatarGroupManager(defaultParametersFilename);
    initializePeripheralStructures();
  }

  /**
   * Initializes peripheral items used for bookkeeping
   */
  private void initializePeripheralStructures() {
    userVariables = new HashMap<>();
    history = new History();
    operationWorkspace = new OperationWorkspace();
    operationFormatter = new OperationFormatter();
  }

  /**
   * Initializes the proper workspace for a new Controller operation, generating a new associated
   * operation signature
   */
  public void startOp() {
    checkPreviousOperationClosed();
    viewPayload = new ViewPayload();
    operationWorkspace.setupWorkspace();
    operationFormatter.generateNewSignature();
  }

  /**
   * End a Controller operation, signifying a success batch of work has been finished. All updates
   * are committed to the original data.
   */
  public ViewPayload endOp(String userInput, List<Double> returnValues) throws RuntimeException {
    checkCurrentOperationConfigured();
    pushWorkspaceUpdates(operationWorkspace.getStagedChangesWorkspace());
    logSupplementalInformation(userInput, returnValues);
    return viewPayload;
  }

  /**
   * Pushes all updates to model parameters and user variables in the workspace back to the Model,
   * then refreshes the Operation Workspace.
   */
  private void pushWorkspaceUpdates(Map<String, String> stagedChangesWorkspace) {
    for (String key : stagedChangesWorkspace.keySet()) {
      if (operationFormatter.isEncodedKey(key)) { // belongs to an avatar
        updateAvatar(key);
      } else {
        userVariables.put(key, Double.parseDouble(operationWorkspace.getChange(key)));
      }
    }
    operationWorkspace.refreshWorkspace();
  }

  /**
   * Updates a specific Avatar with information associated with the given key
   *
   * @param key key that represents one updated Avatar value
   */
  private void updateAvatar(String key) {
    avatarGroupManager.setAvatarValue(operationFormatter.decodeID(key),
        operationFormatter.decodeParameter(key), operationWorkspace.getChange(key));
  }

  /**
   * Logs history and return value information into the Model and ViewPayload
   *
   * @param userInput    user typed command
   * @param returnValues controller generated return values
   */
  private void logSupplementalInformation(String userInput, List<Double> returnValues) {
    history.addEntry(userInput);
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("History"), userInput));

    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("ReturnValues"), returnValues));
  }

  /**
   * End a Controller operation abruptly, signifying an unsuccessful batch of work. All updates are
   * rejected
   */
  public void bail() {
    undoCreations(operationWorkspace.getStagedAvatarCreationWorkspace());

    viewPayload = null;
    operationWorkspace.refreshWorkspace();
  }

  /**
   * Deletes created Avatars in the event that the overall operation fails
   *
   * @param avatarRemovalList list of avatars to remove
   */
  private void undoCreations(List<Avatar> avatarRemovalList) {
    if (avatarRemovalList != null) {
      avatarGroupManager.removeAvatars(avatarRemovalList);
    }
  }

  /**
   * Protects against cases when startOp() was not called before working with the Model
   */
  private void checkCurrentOperationConfigured() {
    if (!operationWorkspace.activeOpRunning()) {
      throw new RuntimeException(EXCEPTIONS.getString("StartOpNotCalledError"));
    }
  }

  /**
   * Protects against cases when endOp() was not called before closing the previous operation
   */
  private void checkPreviousOperationClosed() {
    if (operationWorkspace.activeOpRunning()) {
      throw new RuntimeException(EXCEPTIONS.getString("EndOpNotCalledError"));
    }
  }

  /**
   * Helper method to get a particular Avatar parameter
   *
   * @param avatarKeyCode parameter name
   * @return value of the parameter
   */
  private double getAvatarParameter(String avatarKeyCode) {
    if (operationWorkspace.activeOpRunning()) {
      String formattedKey = operationFormatter.encodeString(avatarKeyCode,
          avatarGroupManager.getCurrentAvatarID());
      if (operationWorkspace.containsKey(formattedKey)) {
        return Double.parseDouble(operationWorkspace.getChange(formattedKey));
      } else {
        return avatarGroupManager.getAvatarDouble(avatarKeyCode);
      }
    } else {
      return avatarGroupManager.getAvatarDouble(avatarKeyCode);
    }
  }

  /**
   * Get the x position of the current avatar
   *
   * @return avatar's x-position
   */
  @Override
  public double getAvatarX() {
    return getAvatarParameter(KEY_CODES.getString("X"));
  }

  /**
   * Get the y position of the current avatar
   *
   * @return avatar's y-position
   */
  @Override
  public double getAvatarY() {
    return getAvatarParameter(KEY_CODES.getString("Y"));
  }

  /**
   * Get the rotation of the current avatar
   *
   * @return avatar's
   */
  @Override
  public double getAvatarRotation() {
    return getAvatarParameter(KEY_CODES.getString("Rotation"));
  }

  /**
   * Gets the pen color of the current avatar
   *
   * @return pen color
   */
  @Override
  public int[] getAvatarPenColor() {
    if (operationWorkspace.activeOpRunning()) {
      String formattedKey = operationFormatter.encodeString(KEY_CODES.getString("PenColor"),
          avatarGroupManager.getCurrentAvatarID());
      if (operationWorkspace.containsKey(formattedKey)) {
        return parseColors(operationWorkspace.getChange(formattedKey));
      } else {
        return parseColors(
            avatarGroupManager.getAvatarString(KEY_CODES.getString("PenColor")));
      }
    } else {
      return parseColors(
          avatarGroupManager.getAvatarString(KEY_CODES.getString("PenColor")));
    }
  }

  /**
   * Parses colors from String representation into RGB values
   *
   * @param color String representation of color
   * @return RGB values
   */
  private int[] parseColors(String color) {
    String[] parsedString = color.split(COLOR_REGEX);
    int[] parsedColors = new int[parsedString.length];
    for (int i = 0; i < parsedColors.length; i++) {
      parsedColors[i] = Integer.parseInt(parsedString[i]);
    }
    return parsedColors;
  }

  /**
   * Gets the state of the pen
   *
   * @return if the pen is down
   */
  @Override
  public boolean getAvatarIsPenDown() {
    if (operationWorkspace.activeOpRunning()) {
      String formattedKey = operationFormatter.encodeString(KEY_CODES.getString("IsPenDown"),
          avatarGroupManager.getCurrentAvatarID());
      if (operationWorkspace.containsKey(formattedKey)) {
        return Boolean.parseBoolean(operationWorkspace.getChange(formattedKey));
      } else {
        return avatarGroupManager.getAvatarBoolean(KEY_CODES.getString("IsPenDown"));
      }
    } else {
      return avatarGroupManager.getAvatarBoolean(KEY_CODES.getString("IsPenDown"));
    }
  }

  /**
   * Gets whether the avatar is currently visible
   *
   * @return if the avatar is visible
   */
  @Override
  public boolean getAvatarVisible() {
    if (operationWorkspace.activeOpRunning()) {
      String formattedKey = operationFormatter.encodeString(KEY_CODES.getString("Visible"),
          avatarGroupManager.getCurrentAvatarID());
      if (operationWorkspace.containsKey(formattedKey)) {
        return Boolean.parseBoolean(operationWorkspace.getChange(formattedKey));
      } else {
        return avatarGroupManager.getAvatarBoolean(KEY_CODES.getString("Visible"));
      }
    } else {
      return avatarGroupManager.getAvatarBoolean(KEY_CODES.getString("Visible"));
    }
  }


  /**
   * Gets the value of the user variable with the specified key. Throws an exception for nonexistent
   * variable names.
   *
   * @param key variable name
   * @return variable value
   */
  @Override
  public double getUserVariable(String key) throws RuntimeException {
    if (operationWorkspace.activeOpRunning()) {
      if (operationWorkspace.containsKey(key)) {
        return Double.parseDouble(operationWorkspace.getChange(key));
      } else {
        return userVariables.getOrDefault(key, UNSET_USER_VARIABLE_VALUE);
      }
    } else {
      return userVariables.getOrDefault(key, UNSET_USER_VARIABLE_VALUE);
    }
  }

  /**
   * Gets all user variables in the Model. Should not be called while there is an active operation
   *
   * @return copy of all user variables
   */
  @Override
  public Map<String, Double> getAllUserVariables() {
    return new HashMap<>(userVariables);
  }

  /**
   * Fetches all successful user typed commands in the current instance of the application
   *
   * @return copy of all successful commands
   */
  @Override
  public List<String> getAllHistory() {
    return new ArrayList<>(history.getAllHistory());
  }

  /**
   * Simultaneously update the current Avatar's x and y position
   *
   * @param x new x position
   * @param y new y position
   */
  @Override
  public void setAvatarPosition(double x, double y) throws RuntimeException {
    checkCurrentOperationConfigured();
    operationWorkspace.setChange(
        operationFormatter.encodeString(KEY_CODES.getString("X"), getCurrentAvatarID()),
        x + "");
    operationWorkspace.setChange(
        operationFormatter.encodeString(KEY_CODES.getString("Y"), getCurrentAvatarID()),
        y + "");
    viewPayload.addCommand(
        new ChangeLog(KEY_CODES.getString("Position"), avatarGroupManager.getCurrentAvatarID(), x,
            y));
  }

  /**
   * Sets the current avatar's rotation
   *
   * @param rotation new rotation
   */
  @Override
  public void setAvatarRotation(double rotation) throws RuntimeException {
    checkCurrentOperationConfigured();
    operationWorkspace.setChange(operationFormatter.encodeString(KEY_CODES.getString("Rotation"),
        avatarGroupManager.getCurrentAvatarID()), rotation + "");
    viewPayload.addCommand(
        new ChangeLog(KEY_CODES.getString("Rotation"), avatarGroupManager.getCurrentAvatarID(),
            rotation));
  }

  /**
   * Sets the current avatar's pen color
   *
   * @param red   red value 0-255
   * @param green green value 0-255
   * @param blue  blue value 0-255
   */
  @Override
  public void setAvatarPenColor(double red, double green, double blue) throws RuntimeException {
    checkCurrentOperationConfigured();
    int castedRed = (int) red;
    int castedGreen = (int) green;
    int castedBlue = (int) blue;
    String convertedColor = castedRed + COLOR_REGEX + castedGreen + COLOR_REGEX + castedBlue;
    operationWorkspace.setChange(operationFormatter.encodeString(KEY_CODES.getString("PenColor"),
        avatarGroupManager.getCurrentAvatarID()), convertedColor);
    viewPayload.addCommand(
        new ChangeLog(KEY_CODES.getString("PenColor"), avatarGroupManager.getCurrentAvatarID(),
            convertedColor));
  }

  /**
   * Sets the current avatar's pen enable setting
   *
   * @param isPenDown new pen setting
   */
  @Override
  public void setAvatarPenDown(boolean isPenDown) throws RuntimeException {
    checkCurrentOperationConfigured();
    operationWorkspace.setChange(operationFormatter.encodeString(KEY_CODES.getString("IsPenDown"),
        avatarGroupManager.getCurrentAvatarID()), isPenDown + "");
    viewPayload.addCommand(
        new ChangeLog(KEY_CODES.getString("IsPenDown"), avatarGroupManager.getCurrentAvatarID(),
            isPenDown + ""));
  }

  /**
   * Sets the current avatar's visibility setting
   *
   * @param visible whether avatar is visible
   */
  @Override
  public void setAvatarVisible(boolean visible) throws RuntimeException {
    checkCurrentOperationConfigured();
    operationWorkspace.setChange(operationFormatter.encodeString(KEY_CODES.getString("Visible"),
        avatarGroupManager.getCurrentAvatarID()), visible + "");
    viewPayload.addCommand(
        new ChangeLog(KEY_CODES.getString("Visible"), avatarGroupManager.getCurrentAvatarID(),
            visible + ""));
  }

  /**
   * Sets the value of a user variable
   *
   * @param key   variable name
   * @param value variable value
   */
  @Override
  public void setUserVariable(String key, double value) throws RuntimeException {
    checkCurrentOperationConfigured();
    operationWorkspace.setChange(key, value + "");
  }

  /**
   * Sets all avatars to the default position and rotation values. RE
   */
  @Override
  public void resetOrientation() throws RuntimeException {
    checkCurrentOperationConfigured();
    double numericDefault = avatarGroupManager.getNumericDefault();
    List<Integer> IDs = avatarGroupManager.getAllExternalIDs();
    for (Integer externalID : IDs) {
      setCurrentAvatarID(externalID);
      setAvatarPosition(numericDefault, numericDefault);
      setAvatarRotation(numericDefault); // Remove magic numbers
    }
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("ClearScreen"), IDs.size(), IDs.size()));
  }

  /**
   * Gets the list of active avatar external IDs. Returned list is immutable for safety
   *
   * @return list of active avatar external IDs
   */
  @Override
  public List<Integer> getActiveAvatars() {
    return avatarGroupManager.getActiveAvatars();
  }

  /**
   * Sets the list of Avatar IDs to be active. Any previously active avatars are removed. Based upon
   * the max ID given, avatars are created to fill each nonexistent ID.
   *
   * @param externalIDs list of new active avatarIDs
   */
  @Override
  public void setActiveAvatars(List<Integer> externalIDs) {
    checkCurrentOperationConfigured();
    List<Avatar> newAvatarList = avatarGroupManager.setActiveAvatars(externalIDs);
    operationWorkspace.recordAvatarAdditions(newAvatarList);
    for (Avatar newAvatar : newAvatarList) {
      viewPayload.addCommand(
          new ChangeLog(KEY_CODES.getString("CreateAvatar"), newAvatar.getExternalID(),
              newAvatar.getNumericDefault() + "",
              newAvatar.getBooleanDefault() + ""));
    }
  }

  /**
   * Gets the current avatar's external ID
   *
   * @return external ID
   */
  @Override
  public int getCurrentAvatarID() {
    return avatarGroupManager.getCurrentAvatarID();
  }


  /**
   * Switches the avatar to the one with the specified ID.
   *
   * @param id new avatar ID
   */
  @Override
  public void setCurrentAvatarID(int id) throws RuntimeException {
    checkCurrentOperationConfigured();
    avatarGroupManager.setCurrentAvatarID(id);
  }

  /**
   * Gets the total number of avatars
   *
   * @return total number of avatars
   */
  @Override
  public int getTotalNumberOfAvatars() {
    return avatarGroupManager.getTotalNumberOfAvatars();
  }
}
