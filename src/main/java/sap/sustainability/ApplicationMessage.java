package sap.sustainability;

/**
 * This class contains application-wide messages used for validation and error handling. It provides
 * static final strings that can be used throughout the application to ensure consistency in
 * messaging and to avoid hardcoding strings in multiple places.
 */
public final class ApplicationMessage {

  private ApplicationMessage() {
    // Prevent instantiation
  }

  public static final String CITY_NAME_NULL_OR_EMPTY = "City name cannot be null or empty.";
  public static final String CITY_NAME_CAN_ONLY_CONTAIN_LETTERS_AND_SPACES =
      "City name can only contain letters and spaces.";
  public static final String UNKNOWN_TRANSPORTATION_MODE_ALLOWED_VALUES_ARE =
      "Unknown transportation mode: \"%s\". Allowed values are: %s.";

  public static final String ORS_TOKEN_ENVIRONMENT_VARIABLE_IS_NOT_SET =
      "ORS_TOKEN environment variable is not set.";
  public static final String FAILED_TO_CALCULATE_EMISSIONS_DUE_TO_IO_ERROR =
      "Failed to calculate emissions due to I/O error.";
  public static final String AN_UNEXPECTED_ERROR_OCCURRED = "An unexpected error occurred: %s";

  public static final String FAILED_TO_FETCH_MATRIX_WITH_RESPONSE_CODE =
      "Failed to fetch matrix with response code: %s.";
  public static final String NO_FEATURES_FOUND_FOR_CITY = "No features found for city: %s.";
  public static final String NO_PERFECT_MATCH_FOUND_FOR_CITY =
      "A perfect match for city: %s was not found. The closest match is: %s.";
  public static final String FAILED_TO_FETCH_COORDINATES_FOR_CITY_DUE_TO_HTTP_ERROR_STATUS =
      "Failed to fetch coordinates for city: %s, due to http error with status: %d.";
  public static final String CITY_NODE_NOT_FOUND =
      "City node not found in the response for city: %s.";
  public static final String COORDINATES_NOT_FOUND = "Coordinates not found for city: %s.";
  public static final String RESULT_EMISSIONS_FOR_TRIP =
      "Your trip caused %.1fkg of CO2-equivalent.%n";
  public static final String EXECUTION_ERROR_OCCURRED = "Execution error occurred";

  public static final String FAILED_TO_CALCULATE_EMISSIONS_FROM_TO_USING =
      "Failed to calculate emissions from {} to {} using {}: {}";
  public static final String PARSE_ERROR_S = "Parse Error: %s";
  public static final String BUSINESS_ERROR_S = "Business Error: %s";
}
