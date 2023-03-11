package slogo.Model.AvatarManager;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Alec Liu
 * The AvatarInitilizerStrategy is a helper class for Avatar to load in parameters
 * from config files. It helps to sort each parameter into the correct map for the Avatar, and it
 * catches misformatted files.
 */
public class AvatarInitializerStrategy {

  private static final String STRING_TYPE = "String";
  private static final String DOUBLE_TYPE = "Double";
  private static final String BOOLEAN_TYPE = "Boolean";
  private ResourceBundle exceptionResourceBundle;
  private Map<String, Double> numericParameters;
  private Map<String, String> stringParameters;
  private Map<String, Boolean> booleanParameters;

  AvatarInitializerStrategy(Map<String, Double> numericParameters,
      Map<String, String> stringParameters, Map<String, Boolean> booleanParameters,
      ResourceBundle exceptionResourceBundle) {
    this.numericParameters = numericParameters;
    this.stringParameters = stringParameters;
    this.booleanParameters = booleanParameters;
    this.exceptionResourceBundle = exceptionResourceBundle;
  }

  /**
   * Add a String parameter to the String map
   *
   * @param key   parameter name
   * @param value parameter value
   */
  private void addStringParameter(String key, String value) {
    stringParameters.put(key, value);
  }

  /**
   * Add a Boolean parameter to the Boolean map
   *
   * @param key   parameter name
   * @param value parameter value
   */
  private void addBooleanParameter(String key, String value) {
    booleanParameters.put(key, Boolean.parseBoolean(value)); // no supported exception in Java
  }

  /**
   * Add a Double parameter to the Double map
   *
   * @param key   parameter name
   * @param value parameter value
   */
  private void addDoubleParameter(String key, String value) {
    try {
      numericParameters.put(key, Double.parseDouble(value));
    } catch (NumberFormatException numberFormatException) {
      throw new NumberFormatException(
          String.format(exceptionResourceBundle.getString("ConfigurationParsingError"),
              key)
      );
    }
  }

  /**
   * Generically add a parameter to the correct map
   *
   * @param type  parameter type
   * @param key   parameter name
   * @param value parameter value
   */
  void addParameter(String type, String key, String value) {
    switch (type) {
      case STRING_TYPE -> addStringParameter(key, value);
      case BOOLEAN_TYPE -> addBooleanParameter(key, value);
      case DOUBLE_TYPE -> addDoubleParameter(key, value);
      default -> throw new RuntimeException(
          exceptionResourceBundle.getString("UnsupportedDefaultTypeError"));
    }
  }
}
