package sap.sustainability.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

class DataValidatorTest {

  @Test
  void testValidateCityName_whenCityIsNull_thenThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> DataValidator.validateCityName(null));
  }

  @Test
  void testValidateCityName_whenCityIsEmpty_thenThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> DataValidator.validateCityName(""));
  }

  @Test
  void testValidateCityName_whenCityContainsInvalidCharacters_thenThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> DataValidator.validateCityName("Berlin123"));
    assertThrows(IllegalArgumentException.class, () -> DataValidator.validateCityName("Berlin!@#"));
  }

  @Test
  void testValidateCityName_whenCityIsValid_thenPassValidation() {
    assertDoesNotThrow(() -> DataValidator.validateCityName("Berlin"));
    assertDoesNotThrow(() -> DataValidator.validateCityName("New York"));
    assertDoesNotThrow(() -> DataValidator.validateCityName("San Francisco"));
  }

  @Test
  void testValidateFeaturesExistsAndIsArray_whenFeaturesIsNotArray_thenThrowRuntimeException() {
    JsonNode nonArrayNode = mock(JsonNode.class);
    doReturn(false).when(nonArrayNode).isArray();

    assertThrows(
        RuntimeException.class,
        () -> DataValidator.validateFeaturesExistsAndIsArray("Berlin", nonArrayNode));
  }

  @Test
  void testValidateFeaturesExistsAndIsArray_whenFeaturesIsEmptyArray_thenThrowRuntimeException() {
    JsonNode emptyArrayNode = mock(JsonNode.class);
    doReturn(true).when(emptyArrayNode).isArray();
    doReturn(true).when(emptyArrayNode).isEmpty();

    assertThrows(
        RuntimeException.class,
        () -> DataValidator.validateFeaturesExistsAndIsArray("Berlin", emptyArrayNode));
  }

  @Test
  void testValidateFeaturesExistsAndIsArray_whenFeaturesIsValidArray_thenPassValidation() {
    JsonNode validArrayNode = mock(JsonNode.class);
    doReturn(true).when(validArrayNode).isArray();
    doReturn(false).when(validArrayNode).isEmpty();

    assertDoesNotThrow(
        () -> DataValidator.validateFeaturesExistsAndIsArray("Berlin", validArrayNode));
  }

  @Test
  void
      testValidateCoordinatesExistAndIsArrayWithTwoPoints_whenCoordinatesIsNotArray_thenThrowRuntimeException() {
    JsonNode nonArrayNode = mock(JsonNode.class);
    doReturn(false).when(nonArrayNode).isArray();

    assertThrows(
        RuntimeException.class,
        () ->
            DataValidator.validateCoordinatesExistAndIsArrayWithTwoPoints("Berlin", nonArrayNode));
  }

  @Test
  void
      testValidateCoordinatesExistAndIsArrayWithTwoPoints_whenCoordinatesHasLessThanTwoPoints_thenThrowRuntimeException() {
    JsonNode arrayNodeWithOnePoint = mock(JsonNode.class);
    doReturn(true).when(arrayNodeWithOnePoint).isArray();
    doReturn(1).when(arrayNodeWithOnePoint).size();

    assertThrows(
        RuntimeException.class,
        () ->
            DataValidator.validateCoordinatesExistAndIsArrayWithTwoPoints(
                "Berlin", arrayNodeWithOnePoint));
  }

  @Test
  void
      testValidateCoordinatesExistAndIsArrayWithTwoPoints_whenCoordinatesIsValid_thenPassValidation() {
    JsonNode validArrayNode = mock(JsonNode.class);
    doReturn(true).when(validArrayNode).isArray();
    doReturn(2).when(validArrayNode).size();

    assertDoesNotThrow(
        () ->
            DataValidator.validateCoordinatesExistAndIsArrayWithTwoPoints(
                "Berlin", validArrayNode));
  }

  @Test
  void
      testValidateCityWithCityNodeMatches_whenCityNodeDoesNotMatchCity_thenThrowRuntimeException() {
    JsonNode cityNode = mock(JsonNode.class);
    doReturn("Hamburg").when(cityNode).asText();

    assertThrows(
        RuntimeException.class,
        () -> DataValidator.validateCityWithCityNodeMatches("Berlin", cityNode));
  }

  @Test
  void testValidateCityWithCityNodeMatches_whenCityNodeMatchesCity_thenPassValidation() {
    JsonNode cityNode = mock(JsonNode.class);
    doReturn("Berlin").when(cityNode).asText();

    assertDoesNotThrow(() -> DataValidator.validateCityWithCityNodeMatches("Berlin", cityNode));
  }
}
