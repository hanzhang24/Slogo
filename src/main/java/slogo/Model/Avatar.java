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
  private Map<String, Double> numericParameters;
  private Map<String, String> stringParameters;
  private Map<String, Boolean> booleanParameters;

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
    numericParameters = new HashMap<>();
    stringParameters = new HashMap<>();
    booleanParameters = new HashMap<>();
    addDefaultParameters();
  }

  /**
   * Add all default parameters and values to the default parameter maps
   */
  private void addDefaultParameters() throws NumberFormatException {
    for (String key : defaultParametersBundle.keySet()) {
      String[] parsedParameters = defaultParametersBundle.getString(key).split(REGEX);
      String type = parsedParameters[TYPE_INDEX];
      String value = parsedParameters[VALUE_INDEX];

      switch (type) {
        case STRING_TYPE -> stringParameters.put(key, value);
        case BOOLEAN_TYPE ->
            booleanParameters.put(key, Boolean.parseBoolean(value)); // no supported exception
        case DOUBLE_TYPE -> {
          try {
            numericParameters.put(key, Double.parseDouble(value));
          } catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException(
                String.format(exceptionResourceBundle.getString("ConfigurationParsingError"),
                    key)
            );
          }
        }
        default -> throw new RuntimeException(
            exceptionResourceBundle.getString("UnsupportedDefaultTypeError"));
      }
    }
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
    return numericParameters.get("Default");
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
   * Adds a updated default parameter to the correct map. Assumes that each default parameter is the
   * correct type
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
