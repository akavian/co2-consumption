package ali.sustainability.service.impl;

import ali.sustainability.enums.TransportationMode;
import ali.sustainability.util.Environment;
import ali.sustainability.util.HttpUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class Co2CalculatorImplTest {

    @Mock
    private HttpUtility httpUtility;

    @Mock
    private Environment environment;

    @InjectMocks
    private Co2CalculatorImpl co2Calculator;

    @Test
    void testCalculateEmission_whenOrsTokenIsNotSet_thenThrowIllegalStateException() {
        assertThrows(
                IllegalStateException.class,
                () -> co2Calculator.calculateEmissions("Berlin",
                        "Munich",
                        TransportationMode.BUS_DEFAULT.getEmission()));
    }

    @Test
    void testCalculateEmissions_whenOrsTokenIsSet_thenReturnCalculatedEmissions() throws IOException {
        doReturn("test-token").when(environment).getEnvironment(anyString());
        doReturn(new double[]{0, 0}).when(httpUtility).fetchCoordinates(eq("Berlin"), anyString());
        doReturn(new double[]{3, 4}).when(httpUtility).fetchCoordinates(eq("Munich"), anyString());
        doReturn(5.0)
                .when(httpUtility)
                .fetchMatrix(any(double[].class), any(double[].class), anyString());
        assertEquals(
                TransportationMode.BUS_DEFAULT.getEmission() * 5.0 / 1000,
                co2Calculator.calculateEmissions("Berlin",
                        "Munich",
                        TransportationMode.BUS_DEFAULT.getEmission()),
                0.1);
    }

    @Test
    void testCalculateEmissions_whenIoExceptionOccurs_thenThrowsRuntimeException()
            throws IOException {
        doReturn("test-token").when(environment).getEnvironment(anyString());
        doReturn(new double[]{0, 0}).when(httpUtility).fetchCoordinates(eq("Berlin"), anyString());
        doReturn(new double[]{3, 4}).when(httpUtility).fetchCoordinates(eq("Munich"), anyString());
        doThrow(IOException.class)
                .when(httpUtility)
                .fetchMatrix(any(double[].class), any(double[].class), anyString());

        assertThrows(
                RuntimeException.class,
                () -> co2Calculator.calculateEmissions("Berlin",
                        "Munich",
                        TransportationMode.BUS_DEFAULT.getEmission()));
    }
}
