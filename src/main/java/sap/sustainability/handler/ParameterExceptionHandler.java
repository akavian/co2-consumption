package sap.sustainability.handler;

import static sap.sustainability.ApplicationMessage.PARSE_ERROR_S;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.IParameterExceptionHandler;
import picocli.CommandLine.ParameterException;

/** Handles parameter exceptions that occur during command-line parsing. */
public class ParameterExceptionHandler implements IParameterExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParameterExceptionHandler.class);

  @Override
  public int handleParseException(ParameterException exception, String[] args) {
    LOGGER.error(exception.getMessage(), exception);
    CommandLine commandLine = exception.getCommandLine();
    if (exception.getCause() != null) {
      commandLine.getErr().println(String.format(PARSE_ERROR_S, exception.getCause().getMessage()));
    } else {
      commandLine.getErr().println(String.format(PARSE_ERROR_S, exception.getMessage()));
    }
    commandLine.usage(commandLine.getErr());
    return commandLine.getCommandSpec().exitCodeOnInvalidInput();
  }
}
