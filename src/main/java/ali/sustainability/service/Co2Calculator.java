package ali.sustainability.service;

import ali.sustainability.enums.TransportationMode;

/**
 * Interface for calculating CO2 emissions based on transportation mode and city locations.
 */
public interface Co2Calculator {
    /**
     * Calculates the CO2 emissions for a trip between two cities based on the specified
     * transportation mode.
     *
     * @param startCity          the starting city
     * @param endCity            the destination city
     * @param transportationMode the mode of transportation used for the trip
     * @return the calculated CO2 emissions in Kilograms
     */
    double calculateEmissions(
            String startCity, String endCity, TransportationMode transportationMode);
}
