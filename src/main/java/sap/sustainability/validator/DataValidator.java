package sap.sustainability.validator;

import static sap.sustainability.ApplicationMessage.*;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Validates city names for the CO2 Calculator application. Ensures that city names are not null,
 * empty, and only contain letters and spaces.
 */
public class DataValidator {

  /**
   * Validates the given city name. Ensures the city name is not null, empty, and only contains
   * letters and spaces.
   *
   * @param city the city name to validate
   * @throws IllegalArgumentException if the city name is invalid
   */
  public static void validateCityName(String city) {
    if (city == null || city.isEmpty()) {
      throw new IllegalArgumentException(CITY_NAME_NULL_OR_EMPTY);
    }

    if (!city.matches("^[a-zA-Z\\s]+$")) {
      throw new IllegalArgumentException(CITY_NAME_CAN_ONLY_CONTAIN_LETTERS_AND_SPACES);
    }
  }

  /**
   * Validates that the provided features exist and are in an array format.
   *
   * @param city the name of the city being validated
   * @param features the JSON node containing the features to validate
   * @throws RuntimeException if the features are null, not an array, or empty
   */
  public static void validateFeaturesExistsAndIsArray(String city, JsonNode features) {
    if (features == null || !features.isArray() || features.isEmpty()) {
      throw new RuntimeException(String.format(NO_FEATURES_FOUND_FOR_CITY, city));
    }
  }

  /**
   * Validates that the provided coordinates exist, are in an array format, and contain at least two
   * points.
   *
   * @param city the name of the city being validated
   * @param coordinates the JSON node containing the coordinates to validate
   * @throws RuntimeException if the coordinates are null, not an array, or contain fewer than two
   *     points
   */
  public static void validateCoordinatesExistAndIsArrayWithTwoPoints(
      String city, JsonNode coordinates) {
    if (coordinates == null || !coordinates.isArray() || coordinates.size() < 2) {
      throw new RuntimeException(String.format(COORDINATES_NOT_FOUND, city));
    }
  }

  /**
   * Validates that the provided city name matches the city name in the given JSON node.
   *
   * @param city the name of the city being validated
   * @param cityNode the JSON node containing the city name to validate against
   * @throws RuntimeException if the city node is null or does not match the provided city name
   */
  public static void validateCityWithCityNodeMatches(String city, JsonNode cityNode) {
    if (cityNode == null) {
      throw new RuntimeException(String.format(CITY_NODE_NOT_FOUND, city));
    }
    if (!cityNode.asText().equalsIgnoreCase(city)) {
      throw new RuntimeException(
          String.format(String.format(NO_PERFECT_MATCH_FOUND_FOR_CITY, city, cityNode.asText())));
    }
  }
}
