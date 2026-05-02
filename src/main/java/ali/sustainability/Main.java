package ali.sustainability;

import ali.sustainability.di.DaggerCommandLineComponent;
import picocli.CommandLine;

import static ali.sustainability.ApplicationMessage.RESULT_EMISSIONS_FOR_TRIP;

/**
 * The entry point of the CO2 Calculator application. This class initializes and executes the
 * command-line interface.
 */
public class Main {

    /**
     * The main method serves as the entry point for the application. It initializes and executes the
     * command-line interface.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        CommandLine commandLine = DaggerCommandLineComponent.create().getCommandLine();
        commandLine.execute(args);
        Double result = commandLine.getExecutionResult();
        if (result != null) {
            commandLine.getOut().printf(RESULT_EMISSIONS_FOR_TRIP, result);
        }
    }
}
