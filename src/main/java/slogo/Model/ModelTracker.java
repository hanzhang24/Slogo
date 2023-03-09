/**
 * TODO: implement strategy pattern for avatar initialization,
 *       implement external and internal ID tracking,
 *       change setID to also create an Avatar if needed and add a corresponding command to payload
 *       adopt new ID into ALL avatar payload objects
 *       expose new API methods to get current ID and avatar list size (may need new exceptions too)
 *       make sure that the ID's being generated are all external ID's for the payload
 *       finishing unit testing
 */
package slogo.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.Model.AvatarManager.Avatar;
import slogo.Payload.ViewPayloadManager.ChangeLog;
import slogo.Payload.ViewPayloadManager.ViewPayload;

/**
 * @author Alec Liu The ModelTracker class is the default implementation of the model. It manages
 * storage and access to Avatar parameters and user variable, and throws exceptions for unexpected
 * behavior.
 */
public class ModelTracker implements Model {

  private static final String EXCEPTIONS_PATH = "Model.Exceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private static final String KEY_CODES_PATH = "Model.KeyCodes";
  private static final ResourceBundle KEY_CODES = ResourceBundle.getBundle(KEY_CODES_PATH);
  private List<Avatar> avatarList;
  private String avatarDefaultParametersFilename;
  private int currentActiveAvatarIndex; // tracks the list index for a specific current ID
  private List<Integer> activeAvatarIDs; // keeps all IDs of active avatars
  private OperationSignatureGenerator operationSignatureGenerator;
  private int operationSignature;
  private Map<String, Double> userVariables;
  private History history;
  private Map<String, String> workspace;
  private ViewPayload viewPayload;

  /**
   * Class constructor - default
   */
  public ModelTracker() {
    initializeAvatars("DefaultParameters");
    initializePeripheralStructures();
  }

  /**
   * Class constructor - for custom filenames
   */
  public ModelTracker(String defaultParametersFilename) {
    initializeAvatars(defaultParametersFilename);
    initializePeripheralStructures();
  }

  /**
   * Initialize avatar-related settings
   */
  private void initializeAvatars(String defaultParametersFilename) {
    this.avatarDefaultParametersFilename = defaultParametersFilename;
    Avatar initialAvatar = new Avatar(1, defaultParametersFilename, EXCEPTIONS);
    avatarList = new ArrayList<>();
    avatarList.add(initialAvatar);
    currentActiveAvatarIndex = 0;
  }

  /**
   * Initializes peripheral items used for bookkeeping
   */
  private void initializePeripheralStructures() {
    userVariables = new HashMap<>();
    history = new History();
    workspace = null;
    operationSignatureGenerator = new OperationSignatureGenerator();
    operationSignature = -1;
    activeAvatarIDs = new ArrayList<>();
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
  public ViewPayload endOp(String userInput, List<Double> returnValues) throws RuntimeException {
    checkCurrentOperationConfigured();
    pushWorkspaceUpdates();
    logSupplementalInformation(userInput, returnValues);

    return viewPayload;
  }

  /**
   * Pushes all updates to model parameters and user variables in the workspace back to the Model
   */
  private void pushWorkspaceUpdates() {
    for (String key : workspace.keySet()) {
      if (key.contains(operationSignature + "")) { // belongs to an avatar
        updateAvatar(key);
      } else {
        userVariables.put(key, Double.parseDouble(workspace.get(key)));
      }
    }
    workspace = null;
  }

  /**
   * Updates a specific Avatar with information associated with the given key
   *
   * @param key key that represents one updated Avatar value
   */
  private void updateAvatar(String key) {
    String[] splitKey = key.split("_");
    int index = Integer.parseInt(splitKey[1].substring(6));
    String parameter = splitKey[2];
    avatarList.get(index).setValue(parameter, workspace.get(key));
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
    return String.format("Signature:%d_Index:%d_%s", operationSignature, currentActiveAvatarIndex,
        key);
  }

  /**
   * Helper method to get a particular Avatar parameter
   *
   * @param avatarKeyCode parameter name
   * @return value of the parameter
   */
  private double getAvatarParameter(String avatarKeyCode) {
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(avatarKeyCode);
      if (workspace.containsKey(formattedKey)) {
        return Double.parseDouble(workspace.get(formattedKey));
      } else {
        return avatarList.get(currentActiveAvatarIndex).getDouble(avatarKeyCode);
      }
    } else {
      return avatarList.get(currentActiveAvatarIndex).getDouble(avatarKeyCode);
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
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(KEY_CODES.getString("PenColor"));
      if (workspace.containsKey(formattedKey)) {
        return parseColors(workspace.get(formattedKey));
      } else {
        return parseColors(
            avatarList.get(currentActiveAvatarIndex).getString(KEY_CODES.getString("PenColor")));
      }
    } else {
      return parseColors(
          avatarList.get(currentActiveAvatarIndex).getString(KEY_CODES.getString("PenColor")));
    }
  }

  /**
   * Parses colors from String representation into RGB values
   *
   * @param color String representation of color
   * @return RGB values
   */
  private int[] parseColors(String color) {
    String[] parsedString = color.split(" ");
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
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(KEY_CODES.getString("IsPenDown"));
      if (workspace.containsKey(formattedKey)) {
        return Boolean.parseBoolean(workspace.get(formattedKey));
      } else {
        return avatarList.get(currentActiveAvatarIndex)
            .getBoolean(KEY_CODES.getString("IsPenDown"));
      }
    } else {
      return avatarList.get(currentActiveAvatarIndex).getBoolean(KEY_CODES.getString("IsPenDown"));
    }
  }

  /**
   * Gets whether the avatar is currently visible
   *
   * @return if the avatar is visible
   */
  @Override
  public boolean getAvatarVisible() {
    if (activeOpRunning()) {
      String formattedKey = formatLookupString(KEY_CODES.getString("Visible"));
      if (workspace.containsKey(formattedKey)) {
        return Boolean.parseBoolean(workspace.get(formattedKey));
      } else {
        return avatarList.get(currentActiveAvatarIndex).getBoolean(KEY_CODES.getString("Visible"));
      }
    } else {
      return avatarList.get(currentActiveAvatarIndex).getBoolean(KEY_CODES.getString("Visible"));
    }
  }


  /**
   * Gets the value of the user variable with the specified key
   *
   * @param key variable name
   * @return variable value
   */
  @Override
  public double getUserVariable(String key) {
    if (activeOpRunning()) {
      if (workspace.containsKey(key)) {
        return Double.parseDouble(workspace.getOrDefault(key, "0"));
      } else {
        return userVariables.getOrDefault(key, 0.0);
      }
    } else {
      return userVariables.getOrDefault(key, 0.0);
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
    workspace.put(formatLookupString(KEY_CODES.getString("X")), x + "");
    workspace.put(formatLookupString(KEY_CODES.getString("Y")), y + "");
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("Position"), fetchExternalIDFromIndex(), x, y));
  }

  /**
   * Sets the current avatar's rotation
   *
   * @param rotation new rotation
   */
  @Override
  public void setAvatarRotation(double rotation) throws RuntimeException {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(KEY_CODES.getString("Rotation")), rotation + "");
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("Rotation"), fetchExternalIDFromIndex(), rotation));
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
    String convertedColor = castedRed + " " + castedGreen + " " + castedBlue;
    workspace.put(formatLookupString(KEY_CODES.getString("PenColor")), convertedColor);
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("PenColor"), fetchExternalIDFromIndex(), convertedColor));
  }

  /**
   * Sets the current avatar's pen enable setting
   *
   * @param isPenDown new pen setting
   */
  @Override
  public void setAvatarPenDown(boolean isPenDown) throws RuntimeException {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(KEY_CODES.getString("IsPenDown")), isPenDown + "");
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("IsPenDown"), fetchExternalIDFromIndex(), isPenDown + ""));
  }

  /**
   * Sets the current avatar's visibility setting
   *
   * @param visible whether avatar is visible
   */
  @Override
  public void setAvatarVisible(boolean visible) throws RuntimeException {
    checkCurrentOperationConfigured();
    workspace.put(formatLookupString(KEY_CODES.getString("Visible")), visible + "");
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("Visible"), fetchExternalIDFromIndex(), visible + ""));
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
    workspace.put(key, value + "");
  }

  /**
   * Sets all avatars to the default position and rotation values
   */
  @Override
  public void resetOrientation() throws RuntimeException {
    checkCurrentOperationConfigured();
    for (Avatar avatar : avatarList) {
      setCurrentAvatarID(avatar.getExternalID());
      double numericDefault = avatar.getNumericDefault();
      setAvatarPosition(numericDefault, numericDefault);
      setAvatarRotation(numericDefault); // Remove magic numbers
    }
    viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("ClearScreen")));
  }

  /**
   * Gets the list of active avatar external IDs. Returned list is immutable for safety
   *
   * @return list of active avatar external IDs
   */
  @Override
  public List<Integer> getActiveAvatars() {
    return Collections.unmodifiableList(activeAvatarIDs);
  }

  /**
   * Sets the list of Avatar IDs to be active. Any previously active avatars are removed. Based upon
   * the max ID given, avatars are created to fill each nonexistent ID.
   *
   * @param externalIDs list of new active avatarIDs
   */
  @Override
  public void setActiveAvatars(List<Integer> externalIDs) {
    activeAvatarIDs.clear();
    int maxID = Collections.max(externalIDs);
    for (int i = 0; i < maxID; i++) {
      if (searchForAvatarID(i) == Integer.MIN_VALUE) {
        addAvatar(i);
      }
      activeAvatarIDs.add(i);
    }
  }

  /**
   * Adds a new avatar with the desired external ID
   *
   * @param externalID externally-generated ID
   */
  public void addAvatar(int externalID) throws RuntimeException {
    checkCurrentOperationConfigured();
    int index = searchForAvatarID(externalID);
    if (index != Integer.MIN_VALUE) {
      throw new RuntimeException(EXCEPTIONS.getString("OverlappingIDError"));
    } else {
      Avatar newAvatar = new Avatar(externalID, avatarDefaultParametersFilename, EXCEPTIONS);
      avatarList.add(newAvatar);
      viewPayload.addCommand(new ChangeLog(KEY_CODES.getString("CreateAvatar"), newAvatar.getExternalID(), newAvatar.getNumericDefault() + "",
          newAvatar.getBooleanDefault() + ""));
    }
  }

  /**
   * Searches for the internal index of an Avatar with a specified external ID. If not found,
   * returns Integer.MIN_VALUE;
   *
   * @param externalID external given ID
   * @return internal list index
   */
  private int searchForAvatarID(int externalID) {
    for (int i = 0; i < avatarList.size(); i++) {
      if (avatarList.get(i).getExternalID() == externalID) {
        return i;
      }
    }
    return Integer.MIN_VALUE;
  }

  private int fetchExternalIDFromIndex(){
    return avatarList.get(currentActiveAvatarIndex).getExternalID();
  }

  /**
   * Gets the current avatar's external ID
   *
   * @return external ID
   */
  @Override
  public int getCurrentAvatarID() {
    return avatarList.get(currentActiveAvatarIndex).getExternalID();
  }


  /**
   * Switches the avatar to the one with the specified ID.
   *
   * @param id new avatar ID
   */
  @Override
  public void setCurrentAvatarID(int id) throws RuntimeException {
    checkCurrentOperationConfigured();
    int index = searchForAvatarID(id);
    if (index != Integer.MIN_VALUE) {
      currentActiveAvatarIndex = index;
    } else {
      throw new RuntimeException(EXCEPTIONS.getString("NonexistentAvatarError"));
    }
  }

  /**
   * Gets the total number of avatars
   *
   * @return total number of avatars
   */
  @Override
  public int getTotalNumberOfAvatars() {
    return avatarList.size();
  }
}
