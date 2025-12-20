package ali.sustainability.service.impl;

import static ali.sustainability.ApplicationMessage.FAILED_TO_CALCULATE_EMISSIONS_DUE_TO_IO_ERROR;
import static ali.sustainability.ApplicationMessage.FAILED_TO_CALCULATE_EMISSIONS_FROM_TO_USING;
import static ali.sustainability.ApplicationMessage.ORS_TOKEN_ENVIRONMENT_VARIABLE_IS_NOT_SET;

import jakarta.inject.Inject;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ali.sustainability.enums.TransportationMode;
import ali.sustainability.service.Co2Calculator;
import ali.sustainability.util.Environment;
import ali.sustainability.util.HttpUtility;

/**
 * Implementation of the CO2 calculator service.
 */
public class Co2CalculatorImpl implements Co2Calculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Co2CalculatorImpl.class);
    private final HttpUtility httpUtility;
    private final Environment environment;

    /**
     * Constructs a new Co2CalculatorImpl with the specified HTTP utility and environment.
     *
     * @param httpUtility the HTTP utility for API calls
     * @param environment the environment utility for accessing environment variables
     */
    @Inject
    public Co2CalculatorImpl(HttpUtility httpUtility, Environment environment) {
        this.httpUtility = httpUtility;
        this.environment = environment;
    }

    @Override
    public double calculateEmissions(
            String startCity, String endCity, TransportationMode transportationMode) {
        String apiKey = environment.getEnvironment("ORS_TOKEN");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException(ORS_TOKEN_ENVIRONMENT_VARIABLE_IS_NOT_SET);
        }
        try {
            double[] startCoordinates = httpUtility.fetchCoordinates(startCity, apiKey);
            double[] endCoordinates = httpUtility.fetchCoordinates(endCity, apiKey);
            double duration = httpUtility.fetchMatrix(startCoordinates, endCoordinates, apiKey);
            return (duration * transportationMode.getEmission()) / 1000;

        } catch (IOException e) {
            LOGGER.error(
                    FAILED_TO_CALCULATE_EMISSIONS_FROM_TO_USING,
                    startCity,
                    endCity,
                    transportationMode,
                    e.getMessage());
            throw new RuntimeException(FAILED_TO_CALCULATE_EMISSIONS_DUE_TO_IO_ERROR, e);
        }
    }
}
