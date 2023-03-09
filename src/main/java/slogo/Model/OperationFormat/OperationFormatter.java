package slogo.Model.OperationFormat;

public class OperationFormatter {

  private OperationSignatureGenerator operationSignatureGenerator;
  private int currentOperationSignature;

  /**
   * Class constructor
   */
  public OperationFormatter() {
    operationSignatureGenerator = new OperationSignatureGenerator();
  }

  /**
   * Detects if a given key has been encoded
   * @param key access key
   * @return if it is encoded
   */
  public boolean isEncodedKey(String key){
    return key.contains(currentOperationSignature + "");
  }

  /**
   * Generates a new signature for each operation
   */
  public void generateNewSignature() {
    currentOperationSignature = operationSignatureGenerator.generateOperationSignature();
  }

  /**
   * Encodes an avatar key to prevent collisions using the parameter name, id, and operation signature
   * @param parameter parameter name
   * @param currentID avatar external ID
   * @return encoded key
   */
  public String encodeString(String parameter, int currentID) {
    return String.format("Signature:%d_ID:%d_%s", currentOperationSignature, currentID,
        parameter);
  }

  /**
   * Extracts avatar external ID from an encoded key
   * @param key encoded key
   * @return avatar external ID
   */
  public int decodeID(String key) {
    String[] splitKey = key.split("_");
    return Integer.parseInt(splitKey[1].substring(3));
  }

  /**
   * Extracts avatar parameters from encoded key
   * @param key encoded key
   * @return avatar parameter
   */
  public String decodeParameter(String key) {
    String[] splitKey = key.split("_");
    return splitKey[2];
  }
}
