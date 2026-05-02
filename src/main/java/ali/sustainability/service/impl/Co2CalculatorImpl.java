package ali.sustainability.service.impl;

import ali.sustainability.service.Co2Calculator;
import ali.sustainability.util.Environment;
import ali.sustainability.util.HttpUtility;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ali.sustainability.ApplicationMessage.*;

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
            String startCity, String endCity, int emission) {
        String apiKey = environment.getEnvironment("ORS_TOKEN");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException(ORS_TOKEN_ENVIRONMENT_VARIABLE_IS_NOT_SET);
        }
        try {
            double[] startCoordinates = httpUtility.fetchCoordinates(startCity, apiKey);
            double[] endCoordinates = httpUtility.fetchCoordinates(endCity, apiKey);
            double duration = httpUtility.fetchMatrix(startCoordinates, endCoordinates, apiKey);
            return (duration * emission) / 1000;

        } catch (IOException e) {
            LOGGER.error(
                    FAILED_TO_CALCULATE_EMISSIONS_FROM_TO_USING,
                    startCity,
                    endCity,
                    emission,
                    e.getMessage());
            throw new RuntimeException(FAILED_TO_CALCULATE_EMISSIONS_DUE_TO_IO_ERROR, e);
        }
    }
}
