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
  private static final String BOOLEAN_TYPE = "Boolean";
  private static final int TYPE_INDEX = 0;
  private static final int VALUE_INDEX = 1;
  private ResourceBundle defaultParametersBundle;
  private ResourceBundle exceptionResourceBundle;
  private Map<String, Double> defaultNumericParameters;
  private Map<String, String> defaultStringParameters;
  private Map<String, Boolean> defaultBooleanParameters;

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
    defaultBooleanParameters = new HashMap<>();
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
      } else if (type.equals(BOOLEAN_TYPE)){
        defaultBooleanParameters.put(key, Boolean.parseBoolean(value)); // no supported exception
      } else if (type.equals(DOUBLE_TYPE)) {
        try {
          defaultNumericParameters.put(key, Double.parseDouble(value));
        } catch (NumberFormatException numberFormatException) {
          throw new NumberFormatException(
              String.format(exceptionResourceBundle.getString("ConfigurationParsingError"),
                  key)
          );
        }
      } else {
        throw new RuntimeException(exceptionResourceBundle.getString("UnsupportedDefaultTypeError"));
      }
    }
  }

  /**
   * Gets the double associated with a given key
   * @param key given parameter
   * @return value of the given parameter
   */
  public double getDouble(String key){
    return defaultNumericParameters.get(key);
  }

  /**
   * Gets the String associated with a given key
   * @param key given parameter
   * @return value of the given parameter
   */
  public String getString(String key){
    return defaultStringParameters.get(key);
  }

  /**
   * Gets the boolean associated with a given key
   * @param key given parameter
   * @return value of the given parameter
   */
  public boolean getBoolean(String key){
    return defaultBooleanParameters.get(key);
  }

  /**
   * Adds a updated default parameter to the correct map. Assumes that each default parameter is the
   * correct type
   *
   * @param key   name of the parameter
   * @param value value of the parameter
   */
  public void setValue(String key, String value) {
    if (defaultStringParameters.containsKey(key)) {
      defaultStringParameters.put(key, value);
    } else if(defaultNumericParameters.containsKey(key)){
      defaultNumericParameters.put(key, Double.parseDouble(value));
    } else if(defaultBooleanParameters.containsKey(key)){
      defaultBooleanParameters.put(key, Boolean.parseBoolean(value));
    } else {
      throw new RuntimeException(exceptionResourceBundle.getString("UnknownAvatarParameterError"));
    }
  }
}
