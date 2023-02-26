package slogo.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Alec Liu The Avatar class is meant to manage the associated default set of parameters
 * describing an Avatar. These are core values, and the abstraction supports rules that prevent the
 * default parameters from changing in unexpected ways.
 */
public class Avatar {

  private static final String DEFAULT_PARAMETERS_BASE_PATH = "Model.";
  private static final String REGEX = ",";
  private static final String STRING_TYPE = "String";
  private static final String DOUBLE_TYPE = "Double";
  private static final int TYPE_INDEX = 0;
  private static final int VALUE_INDEX = 1;
  private ResourceBundle defaultParametersBundle;
  private ResourceBundle exceptionResourceBundle;
  private Map<String, Double> defaultNumericParameters;
  private Map<String, String> defaultStringParameters;

  /**
   * Class constructor
   *
   * @param defaultParametersFilename filename for the default parameters
   * @param exceptionResourceBundle   resource bundle for exception messages
   */
  Avatar(String defaultParametersFilename, ResourceBundle exceptionResourceBundle) {
    this.defaultParametersBundle = ResourceBundle.getBundle(
        DEFAULT_PARAMETERS_BASE_PATH + defaultParametersFilename);
    this.exceptionResourceBundle = exceptionResourceBundle;
    defaultNumericParameters = new HashMap<>();
    defaultStringParameters = new HashMap<>();
    addDefaultParameters();
  }

  /**
   * Add all default parameters and values to the default variable maps
   */
  private void addDefaultParameters() throws NumberFormatException {
    for (String key : defaultParametersBundle.keySet()) {
      String[] parsedParameters = defaultParametersBundle.getString(key).split(REGEX);
      String type = parsedParameters[TYPE_INDEX];
      String value = parsedParameters[VALUE_INDEX];
      if (type.equals(STRING_TYPE)) {
        defaultStringParameters.put(key, value);
      } else if (type.equals(DOUBLE_TYPE)) {
        try {
          defaultNumericParameters.put(key, Double.parseDouble(value));
        } catch (NumberFormatException numberFormatException) {
          throw new NumberFormatException(
              String.format(exceptionResourceBundle.getString("ConfigurationParseDoubleError"),
                  key)
          );
        }
      }
    }
  }

  /**
   * Checks if a given string is a default parameter
   *
   * @param key given key
   * @return if the key is a default parameter
   */
  public boolean contains(String key) {
    return defaultNumericParameters.containsKey(key) || defaultStringParameters.containsKey(key);
  }

  /**
   * Gets all elements in the default parameters
   *
   * @return all default parameters as Strings
   */
  public Map<String, String> getAllDefaultElements() {
    Map<String, String> combinedMap = new HashMap<>();
    combinedMap.putAll(defaultStringParameters);
    for (String key : defaultNumericParameters.keySet()) {
      combinedMap.put(key, defaultNumericParameters.get(key) + "");
    }
    return combinedMap;
  }

  /**
   * Adds a updated default parameter to the correct map. Assumes that each default parameter is the
   * correct type
   *
   * @param key   name of the parameter
   * @param value value of the parameter
   */
  public void put(String key, String value) {
    if (defaultStringParameters.containsKey(key)) {
      defaultStringParameters.put(key, value);
    } else {
      defaultNumericParameters.put(key, Double.parseDouble(value));
    }
  }

  /**
   * Checks if the given default variable and value are appropriate types
   *
   * @param key   default parameter name
   * @param value default parameter value
   * @return if it is an appropriate type
   */
  public boolean checkAppropriateType(String key, String value) {
    return defaultStringParameters.containsKey(key);
  }

  /**
   * Checks if the given default variable and value are appropriate types
   *
   * @param key   default parameter name
   * @param value default parameter value
   * @return if it is an appropriate type
   */
  public boolean checkAppropriateType(String key, double value) {
    return defaultNumericParameters.containsKey(key);
  }
}
