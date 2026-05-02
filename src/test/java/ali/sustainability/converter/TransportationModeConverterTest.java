package ali.sustainability.converter;

import ali.sustainability.enums.TransportationMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransportationModeConverterTest {

    @Test
    void testConvert_whenValidTransportationMode() {
        TransportationModeConverter converter = new TransportationModeConverter();
        Integer result = converter.convert("bus-default");
        assertEquals(TransportationMode.BUS_DEFAULT.getEmission(), result);
    }

    @Test
    void testConvert_whenValidTransportationModeCaseInsensitive() {
        TransportationModeConverter converter = new TransportationModeConverter();
        Integer result = converter.convert("BUS-default");
        assertEquals(TransportationMode.BUS_DEFAULT.getEmission(), result);
    }

    @Test
    void testConvert_whenInvalidTransportationMode_throwsException() {
        TransportationModeConverter converter = new TransportationModeConverter();
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
