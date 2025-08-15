package sap.sustainability.util;

import static sap.sustainability.ApplicationMessage.FAILED_TO_FETCH_COORDINATES_FOR_CITY_DUE_TO_HTTP_ERROR_STATUS;
import static sap.sustainability.ApplicationMessage.FAILED_TO_FETCH_MATRIX_WITH_RESPONSE_CODE;
import static sap.sustainability.validator.DataValidator.validateDistanceNodeExistsAndIsValid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Objects;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sap.sustainability.validator.DataValidator;

/** Utility class for making HTTP requests to external APIs such as OpenRouteService. */
public class HttpUtility {

  private static final String OPEN_ROUTE_MATRIX_DRIVING_CAR_URL =
      "https://api.openrouteservice.org/v2/matrix/driving-car";
  private static final String OPEN_ROUTE_HTTPS_GEO_SEARCH_URI =
      "https://api.openrouteservice.org/geocode/search";

  private final OkHttpClient client;

  /**
   * Constructs a new HttpUtility with the specified OkHttpClient.
   *
   * @param client the OkHttpClient instance to use for HTTP requests
   */
  public HttpUtility(OkHttpClient client) {
    this.client = client;
  }

  /**
   * Fetches the geographic coordinates (longitude and latitude) for the specified city using the
   * OpenRouteService API.
   *
   * @param city the name of the city to fetch coordinates for
   * @param apiKey the API key for OpenRouteService
   * @return an array containing longitude and latitude
   * @throws IOException if an I/O error occurs during the HTTP request
   */
  public double[] fetchCoordinates(String city, String apiKey) throws IOException {
    HttpUrl url = getHttpUrl(city, apiKey);

    Request request = new Request.Builder().url(url).build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful() || response.body() == null) {
        throw new RuntimeException(
            String.format(
                FAILED_TO_FETCH_COORDINATES_FOR_CITY_DUE_TO_HTTP_ERROR_STATUS,
                city,
                response.code()));
      }

      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode root = objectMapper.readTree(response.body().string());

      JsonNode features = root.get("features");
      DataValidator.validateFeaturesExistsAndIsArray(city, features);

      JsonNode cityNode = features.get(0).at("/properties/locality");
      DataValidator.validateCityWithCityNodeMatches(city, cityNode);

      JsonNode coordinates = features.get(0).at("/geometry/coordinates");
      DataValidator.validateCoordinatesExistAndIsArrayWithTwoPoints(city, coordinates);

      return new double[] {coordinates.get(0).asDouble(), coordinates.get(1).asDouble()};
    }
  }

  /**
   * Fetches the distance matrix between two geographic coordinates using the OpenRouteService API.
   *
   * @param startCoordinates the starting point coordinates (longitude, latitude)
   * @param endCoordinates the destination point coordinates (longitude, latitude)
   * @param apiKey the API key for OpenRouteService
   * @return the distance in kilometers between the two points
   * @throws IOException if an I/O error occurs during the HTTP request
   */
  public double fetchMatrix(double[] startCoordinates, double[] endCoordinates, String apiKey)
      throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    RequestBody requestBody =
        getRequestBodyForMatrixEndpoint(startCoordinates, endCoordinates, objectMapper);
    Request request =
        new Request.Builder()
            .url(OPEN_ROUTE_MATRIX_DRIVING_CAR_URL)
            .addHeader("Authorization", apiKey)
            .post(requestBody)
            .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful() || response.body() == null) {
        throw new RuntimeException(
            String.format(FAILED_TO_FETCH_MATRIX_WITH_RESPONSE_CODE, response.code()));
      }
      JsonNode root = objectMapper.readTree(response.body().string());
      JsonNode distance = root.at("/distances/0");
      validateDistanceNodeExistsAndIsValid(distance);

      return distance.get(1).asDouble();
    }
  }

  private HttpUrl getHttpUrl(String city, String apiKey) {
    return Objects.requireNonNull(HttpUrl.parse(OPEN_ROUTE_HTTPS_GEO_SEARCH_URI))
        .newBuilder()
        .addQueryParameter("api_key", apiKey)
        .addQueryParameter("text", city)
        .addQueryParameter("layers", "locality")
        .addQueryParameter("size", "1")
        .build();
  }

  private RequestBody getRequestBodyForMatrixEndpoint(
      double[] startCoordinates, double[] endCoordinates, ObjectMapper objectMapper)
      throws JsonProcessingException {
    ObjectNode bodyJson = objectMapper.createObjectNode();
    bodyJson
        .putArray("locations")
        .add(objectMapper.createArrayNode().add(startCoordinates[0]).add(startCoordinates[1]))
        .add(objectMapper.createArrayNode().add(endCoordinates[0]).add(endCoordinates[1]));
    bodyJson.putArray("metrics").add("distance");
    bodyJson.put("units", "km");

    return RequestBody.create(
        objectMapper.writeValueAsString(bodyJson), MediaType.get("application/json"));
  }
}
