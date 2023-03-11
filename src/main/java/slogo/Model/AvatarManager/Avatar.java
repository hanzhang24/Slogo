package slogo.Model.AvatarManager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Alec Liu
 * The Avatar class is meant to manage the associated default set of parameters
 * describing an Avatar. These are core values, and the abstraction supports rules that prevent the
 * default parameters from changing in unexpected ways.
 */
public class Avatar {

  private static final String DEFAULT_PARAMETERS_BASE_PATH = "Model.";
  private static final String REGEX = ",";
  private static final int TYPE_INDEX = 0;
  private static final int VALUE_INDEX = 1;
  private int externalID;
  private ResourceBundle defaultParametersBundle;
  private ResourceBundle exceptionResourceBundle;
  private Map<String, Double> numericParameters;
  private Map<String, String> stringParameters;
  private Map<String, Boolean> booleanParameters;

  /**
   * Class constructor
   *
   * @param defaultParametersFilename filename for the default parameters
   * @param exceptionResourceBundle   resource bundle for exception messages
   */
  public Avatar(int externalID, String defaultParametersFilename,
      ResourceBundle exceptionResourceBundle) {
    this.externalID = externalID;
    this.defaultParametersBundle = ResourceBundle.getBundle(
        DEFAULT_PARAMETERS_BASE_PATH + defaultParametersFilename);
    this.exceptionResourceBundle = exceptionResourceBundle;
    numericParameters = new HashMap<>();
    stringParameters = new HashMap<>();
    booleanParameters = new HashMap<>();
    addDefaultParameters();
  }

  /**
   * Add all default parameters and values to the default parameter maps
   */
  private void addDefaultParameters() throws NumberFormatException {
    AvatarInitializerStrategy avatarInitializerStrategy = new AvatarInitializerStrategy(
        numericParameters, stringParameters, booleanParameters, exceptionResourceBundle);
    for (String key : defaultParametersBundle.keySet()) {
      String[] parsedParameters = defaultParametersBundle.getString(key).split(REGEX);
      String type = parsedParameters[TYPE_INDEX];
      String value = parsedParameters[VALUE_INDEX];
      avatarInitializerStrategy.addParameter(type, key, value);
    }
  }

  /**
   * Gets the external ID of the Avatar
   *
   * @return external ID
   */
  public int getExternalID() {
    return externalID;
  }

  /**
   * Gets the double associated with a given key
   *
   * @param key given parameter
   * @return value of the given parameter
   */
  public double getDouble(String key) {
    return numericParameters.get(key);
  }

  /**
   * Gets the default value for numeric parameters
   *
   * @return numeric default
   */
  public double getNumericDefault() {
    return numericParameters.get("NumericDefault");
  }

  /**
   * Gets the default value for boolean parameters
   *
   * @return numeric default
   */
  public boolean getBooleanDefault() {
    return booleanParameters.get("BooleanDefault");
  }


  /**
   * Gets the String associated with a given key
   *
   * @param key given parameter
   * @return value of the given parameter
   */
  public String getString(String key) {
    return stringParameters.get(key);
  }

  /**
   * Gets the boolean associated with a given key
   *
   * @param key given parameter
   * @return value of the given parameter
   */
  public boolean getBoolean(String key) {
    return booleanParameters.get(key);
  }

  /**
   * Adds an updated default parameter to the correct map. Assumes that each default parameter is
   * the correct type
   *
   * @param key   name of the parameter
   * @param value value of the parameter
   */
  public void setValue(String key, String value) {
    if (stringParameters.containsKey(key)) {
      stringParameters.put(key, value);
    } else if (numericParameters.containsKey(key)) {
      numericParameters.put(key, Double.parseDouble(value));
    } else if (booleanParameters.containsKey(key)) {
      booleanParameters.put(key, Boolean.parseBoolean(value));
    } else {
      throw new RuntimeException(exceptionResourceBundle.getString("UnknownAvatarParameterError"));
    }
  }
}
