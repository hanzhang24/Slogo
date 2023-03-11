package slogo.Model.AvatarManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Alec Liu
 * The AvatarGroupManager class is a helper class to the Model which stores the
 * collection of Avatars. This class tracks "active turtles", the current turtle being modified, and
 * internally translates external IDs to internal indicies.
 */
public class AvatarGroupManager {

  private static final String EXCEPTIONS_PATH = "Model.AvatarExceptions";
  private static final ResourceBundle EXCEPTIONS = ResourceBundle.getBundle(EXCEPTIONS_PATH);
  private static final int START_ID = 1;
  private List<Avatar> avatarList;
  private String avatarDefaultParametersFilename;
  private int currentActiveAvatarIndex; // tracks the list index for a specific current ID
  private List<Integer> activeAvatarIDs; // keeps all IDs of active avatars

  /**
   * Class constructor
   *
   * @param defaultParametersFilename file name to draw default parameters from
   */
  public AvatarGroupManager(String defaultParametersFilename) {
    this.avatarDefaultParametersFilename = defaultParametersFilename;
    Avatar initialAvatar = new Avatar(START_ID, defaultParametersFilename, EXCEPTIONS);
    avatarList = new ArrayList<>();
    avatarList.add(initialAvatar);
    currentActiveAvatarIndex = 0;
    activeAvatarIDs = new ArrayList<>(List.of(START_ID));
  }

  /**
   * Retrieves true value of an avatar numeric parameter
   *
   * @param avatarKeyCode name of the parameter
   * @return value of the parameter
   */
  public double getAvatarDouble(String avatarKeyCode) {
    return avatarList.get(currentActiveAvatarIndex).getDouble(avatarKeyCode);
  }

  /**
   * Retrieves true value of an avatar String parameter
   *
   * @param avatarKeyCode name of the parameter
   * @return value of the parameter
   */
  public String getAvatarString(String avatarKeyCode) {
    return avatarList.get(currentActiveAvatarIndex).getString(avatarKeyCode);
  }

  /**
   * Retrieves true value of an avatar Boolean parameter
   *
   * @param avatarKeyCode name of the parameter
   * @return value of the parameter
   */
  public boolean getAvatarBoolean(String avatarKeyCode) {
    return avatarList.get(currentActiveAvatarIndex).getBoolean(avatarKeyCode);
  }

  /**
   * Retrieves a list of all avatar external IDs. Helpful for Model commands that change all
   * avatars.
   *
   * @return unmodifiable list of all IDs
   */
  public List<Integer> getAllExternalIDs() {
    List<Integer> IDList = new ArrayList<>();
    for (Avatar avatar : avatarList) {
      IDList.add(avatar.getExternalID());
    }
    return Collections.unmodifiableList(IDList);
  }

  /**
   * Gets the numeric default for all avatars. Helpful when resetting all avatars
   *
   * @return numeric default
   */
  public double getNumericDefault() {
    return avatarList.get(0).getNumericDefault();
  }

  /**
   * Gets the list of active avatar external IDs. Returned list is immutable for safety
   *
   * @return list of active avatar external IDs
   */
  public List<Integer> getActiveAvatars() {
    return Collections.unmodifiableList(activeAvatarIDs);
  }

  /**
   * Gets the current avatar's external ID
   *
   * @return external ID
   */
  public int getCurrentAvatarID() {
    return avatarList.get(currentActiveAvatarIndex).getExternalID();
  }

  /**
   * Gets the total number of avatars
   *
   * @return total number of avatars
   */
  public int getTotalNumberOfAvatars() {
    return avatarList.size();
  }

  /**
   * Sets the value of an avatar's parameter
   *
   * @param id        avatar external ID
   * @param parameter parameter name
   * @param value     parameter value
   */
  public void setAvatarValue(int id, String parameter, String value) {
    int index = searchForAvatarID(id);
    avatarList.get(index).setValue(parameter, value);
  }

  /**
   * Sets the list of Avatar IDs to be active. Any previously active avatars are removed. Based upon
   * the max ID given, avatars are created to fill each nonexistent ID.
   *
   * @param externalIDs unmodifiable list of new active avatarIDs
   */
  public List<Avatar> setActiveAvatars(List<Integer> externalIDs) {
    List<Avatar> newAvatarList = new ArrayList<>();
    activeAvatarIDs.clear();
    int maxID = Collections.max(externalIDs);
    for (int i = 1; i <= maxID; i++) {
      if (searchForAvatarID(i) == Integer.MIN_VALUE) {
        Avatar newAvatar = addAvatar(i);
        newAvatarList.add(newAvatar);
      }
      activeAvatarIDs.add(i);
    }
    return Collections.unmodifiableList(newAvatarList);
  }

  /**
   * Adds a new avatar with the desired external ID
   *
   * @param externalID assigned externally-generated ID
   */
  private Avatar addAvatar(int externalID) throws RuntimeException {
    int index = searchForAvatarID(externalID);
    if (index != Integer.MIN_VALUE) {
      throw new RuntimeException(EXCEPTIONS.getString("OverlappingIDError"));
    } else {
      Avatar newAvatar = new Avatar(externalID, avatarDefaultParametersFilename, EXCEPTIONS);
      avatarList.add(newAvatar);
      return newAvatar;
    }
  }

  /**
   * Removes a list of avatars from the true avatar list. Called when an operation fails, helping to
   * revert to previous Model state.
   *
   * @param avatarRemovalList list of avatars to remove
   */
  public void removeAvatars(List<Avatar> avatarRemovalList) {
    avatarList.removeAll(avatarRemovalList);
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

  /**
   * Switches the avatar to the one with the specified ID.
   *
   * @param id new avatar ID
   */
  public void setCurrentAvatarID(int id) throws RuntimeException {
    int index = searchForAvatarID(id);
    if (index != Integer.MIN_VALUE) {
      currentActiveAvatarIndex = index;
    } else {
      throw new RuntimeException(EXCEPTIONS.getString("NonexistentAvatarError"));
    }
  }
}
