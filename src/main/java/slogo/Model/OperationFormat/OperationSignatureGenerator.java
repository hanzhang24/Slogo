package slogo.Model.OperationFormat;

import java.util.Random;

/**
 * @author Alec Liu The OperationSignatureGeneratorClass is a random number generator that produces
 * unique, hard-to-guess signatures for each operation. These signatures help prevent collisions
 * between user variables and protected avatar parameters.
 */
public class OperationSignatureGenerator {

  private Random randomNumberGenerator;

  /**
   * Class constructor
   */
  OperationSignatureGenerator() {
    randomNumberGenerator = new Random();
  }

  /**
   * Generate a random operation signature to prevent collisions between user variables and Avatar
   * parameters
   *
   * @return new operation signature
   */
  int generateOperationSignature() {
    return randomNumberGenerator.nextInt(10000);
  }

}
