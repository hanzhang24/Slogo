package slogo.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.Model.AvatarManager.Avatar;

/**
 * @author Alec Liu The OperationWorkspace class is responsible for holding a temporary copy of
 * changed values during an operation. During an active operation, any "writes" to values in the
 * Model are redirected here. At the conclusion of an operation, these updates are sent back to the
 * true sources in the Model. Importantly, by writing to this workspace instead of directly to the
 * Model during an operation, the program can easily recover the previous state in case an error
 * occurs.
 */
public class OperationWorkspace {

  private Map<String, String> stagedChangesWorkspace;
  private List<Avatar> stagedAvatarCreationWorkspace;

  /**
   * Class constructor
   */
  public OperationWorkspace() {
    refreshWorkspace();
  }

  /**
   * Prepares the workspace for the beginning of an operation
   */
  public void setupWorkspace() {
    stagedChangesWorkspace = new HashMap<>();
    stagedAvatarCreationWorkspace = new ArrayList<>();
  }

  /**
   * Clears the workspace at the end of the operation
   */
  public void refreshWorkspace() {
    stagedChangesWorkspace = null;
    stagedAvatarCreationWorkspace = null;
  }

  /**
   * Gets if there is an active operation running, crucial for enforcing Model access
   *
   * @return if there is an active operation
   */
  public boolean activeOpRunning() {
    return stagedChangesWorkspace != null && stagedAvatarCreationWorkspace != null;
  }

  /**
   * Determines if the workspace already contains a parameter or variable, allowing the program to
   * retrieve the most recently written version.
   *
   * @param key variable/parameter name
   * @return variable/parameter value
   */
  public boolean containsKey(String key) {
    return stagedChangesWorkspace.containsKey(key);
  }

  /**
   * Getter method
   *
   * @return all staged changes to existing elements in the Model
   */
  public Map<String, String> getStagedChangesWorkspace() {
    return stagedChangesWorkspace;
  }

  /**
   * Getter method
   *
   * @return all staged additions to the Model
   */
  public List<Avatar> getStagedAvatarCreationWorkspace() {
    return stagedAvatarCreationWorkspace;
  }


  /**
   * Retrieves a value from the collection of staged changes
   *
   * @param key variable/parameter name
   * @return variable/parameter value
   */
  public String getChange(String key) {
    return stagedChangesWorkspace.get(key);
  }

  /**
   * Sets the new value of a staged variable/parameter change
   *
   * @param key   variable/parameter name
   * @param value variable/parameter value
   */
  public void setChange(String key, String value) {
    stagedChangesWorkspace.put(key, value);
  }

  /**
   * Adds additional creations to the staged creation list
   *
   * @param avatarAdditionList new additions list
   */
  public void recordAvatarAdditions(List<Avatar> avatarAdditionList) {
    stagedAvatarCreationWorkspace.addAll(avatarAdditionList);
  }
}
