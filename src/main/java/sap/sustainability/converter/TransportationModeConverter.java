package sap.sustainability.converter;

import picocli.CommandLine.ITypeConverter;
import sap.sustainability.enums.TransportationMode;

/**
 * Converts transportation mode strings to their corresponding enum values. Used for command-line
 * argument parsing in the CO2 Calculator application.
 */
public class TransportationModeConverter implements ITypeConverter<TransportationMode> {
  @Override
  public TransportationMode convert(String value) {
    return TransportationMode.fromString(value);
  }
}
