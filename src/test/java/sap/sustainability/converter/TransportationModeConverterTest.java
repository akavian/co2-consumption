package sap.sustainability.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import sap.sustainability.enums.TransportationMode;

class TransportationModeConverterTest {

  @Test
  void testConvert_whenValidTransportationMode() {
    TransportationModeConverter converter = new TransportationModeConverter();
    TransportationMode result = converter.convert("bus-default");
    assertEquals(TransportationMode.BUS_DEFAULT, result);
  }

  @Test
  void testConvert_whenValidTransportationModeCaseInsensitive() {
    TransportationModeConverter converter = new TransportationModeConverter();
    TransportationMode result = converter.convert("BUS-default");
    assertEquals(TransportationMode.BUS_DEFAULT, result);
  }

  @Test
  void testConvert_whenInvalidTransportationMode_throwsException() {
    TransportationModeConverter converter = new TransportationModeConverter();
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> converter.convert("INVALID_MODE"));
  }

  @Test
  void testConvert_whenNullValue_throwsIllegalArgumentException() {
    TransportationModeConverter converter = new TransportationModeConverter();
    assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
  }

  @Test
  void testConvert_whenEmptyString_throwsException() {
    TransportationModeConverter converter = new TransportationModeConverter();
    assertThrows(IllegalArgumentException.class, () -> converter.convert(""));
  }
}
