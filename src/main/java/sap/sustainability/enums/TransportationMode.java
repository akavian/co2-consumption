package sap.sustainability.enums;

import static sap.sustainability.ApplicationMessage.UNKNOWN_TRANSPORTATION_MODE_ALLOWED_VALUES_ARE;

import java.util.Arrays;

/**
 * Enumeration of transportation modes available for CO2 emission calculations. Represents different
 * types of transportation that can be used for travel between cities.
 */
public enum TransportationMode {
  DIESEL_CAR_SMALL("diesel-car-small", 142),
  PETROL_CAR_SMALL("petrol-car-small", 154),
  PLUGIN_HYBRID_CAR_SMALL("plugin-hybrid-car-small", 73),
  ELECTRIC_CAR_SMALL("electric-car-small", 50),
  DIESEL_CAR_MEDIUM("diesel-car-medium", 171),
  PETROL_CAR_MEDIUM("petrol-car-medium", 192),
  PLUGIN_HYBRID_CAR_MEDIUM("plugin-hybrid-car-medium", 110),
  ELECTRIC_CAR_MEDIUM("electric-car-medium", 58),
  DIESEL_CAR_LARGE("diesel-car-large", 209),
  PETROL_CAR_LARGE("petrol-car-large", 282),
  PLUGIN_HYBRID_CAR_LARGE("plugin-hybrid-car-large", 126),
  ELECTRIC_CAR_LARGE("electric-car-large", 73),
  BUS_DEFAULT("bus-default", 27),
  TRAIN_DEFAULT("train-default", 6);

  private final String mode;
  private final int emission;

  TransportationMode(String mode, int emission) {
    this.mode = mode;
    this.emission = emission;
  }

  public int getEmission() {
    return emission;
  }

  public String getMode() {
    return mode;
  }

  /**
   * To convert mode received as a string from commandline to Transportation Mode.
   *
   * @param mode received from command line
   * @return TransportationMode
   * @throws IllegalArgumentException when there is no correspondence
   */
  public static TransportationMode fromString(String mode) {
    for (TransportationMode transportationMode : values()) {
      if (transportationMode.mode.equalsIgnoreCase(mode)) {
        return transportationMode;
      }
    }

    throw new IllegalArgumentException(
        String.format(
            UNKNOWN_TRANSPORTATION_MODE_ALLOWED_VALUES_ARE,
            mode,
            Arrays.toString(
                java.util.Arrays.stream(values())
                    .map(TransportationMode::getMode)
                    .toArray(String[]::new))));
  }
}
