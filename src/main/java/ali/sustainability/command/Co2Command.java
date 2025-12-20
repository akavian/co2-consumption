package ali.sustainability.command;

import jakarta.inject.Inject;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ali.sustainability.converter.TransportationModeConverter;
import ali.sustainability.enums.TransportationMode;
import ali.sustainability.service.Co2Calculator;
import ali.sustainability.validator.DataValidator;

/**
 * The Co2Command class provides command-line functionality for calculating CO2 emissions based on
 * transportation modes and city locations. It allows users to specify a starting city, an ending
 * city, and a mode of transportation to compute the CO2 emissions for a trip.
 */
@Command(
        name = "co2-calculator",
        description = "Commands related to CO2 emissions and sustainability.")
public class Co2Command implements Callable<Double> {

    private String startCity;
    private String endCity;
    private TransportationMode transportationMode;

    private final Co2Calculator co2Calculator;

    /**
     * Constructs a new Co2Command with the specified CO2 calculator service.
     *
     * @param co2Calculator the service used to calculate CO2 emissions
     */
    @Inject
    public Co2Command(Co2Calculator co2Calculator) {
        this.co2Calculator = co2Calculator;
    }

    /**
     * Executes the CO2 calculation command.
     *
     * @return the calculated CO2 emissions as a Double value.
     */
    @Override
    public Double call() {
        return co2Calculator.calculateEmissions(startCity, endCity, transportationMode);
    }

    /**
     * Sets the starting city for the CO2 calculation. Validates the city name to ensure it is not
     * null, empty, and contains only letters and spaces.
     *
     * @param startCity the starting city for the CO2 calculation
     * @throws IllegalArgumentException if the city name is invalid (null, empty, or contains invalid
     *                                  characters)
     * @see DataValidator#validateCityName(String)
     */
    @Option(
            names = {"--start", "-s"},
            required = true,
            description = "The starting city for the CO2 calculation.")
    private void setStartCity(String startCity) {
        DataValidator.validateCityName(startCity);
        this.startCity = startCity;
    }

    /**
     * Sets the destination city for the CO2 calculation. Validates the city name to ensure it is not
     * null, empty, and contains only letters and spaces.
     *
     * @param endCity the destination city for the CO2 calculation
     * @throws IllegalArgumentException if the city name is invalid (null, empty, or contains invalid
     *                                  characters)
     * @see DataValidator#validateCityName(String)
     */
    @Option(
            names = {"--end", "-e"},
            required = true,
            description = "The destination city for the CO2 calculation.")
    private void setEndCity(String endCity) {
        DataValidator.validateCityName(endCity);
        this.endCity = endCity;
    }

    @Option(
            names = {"--transportation-method", "-tm"},
            required = true,
            description = "The mode of transportation for the CO2 calculation.",
            converter = TransportationModeConverter.class)
    private void setTransportationMode(TransportationMode transportationMode) {
        this.transportationMode = transportationMode;
    }
}
