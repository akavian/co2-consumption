package ali.sustainability.converter;

import picocli.CommandLine.ITypeConverter;
import ali.sustainability.enums.TransportationMode;

import java.io.IOException;
import java.util.Properties;

/**
 * Converts transportation mode strings to their corresponding enum values. Used for command-line
 * argument parsing in the CO2 Calculator application.
 */
public class TransportationModeConverter implements ITypeConverter<Integer> {
    @Override
    public Integer convert(String value) {

        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("application.properties"));
            return (int) properties.get(value);
        } catch (IOException | NullPointerException e) {
            return TransportationMode.fromString(value).getEmission();
        }
    }
}
