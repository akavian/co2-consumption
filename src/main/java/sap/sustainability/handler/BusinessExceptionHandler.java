package sap.sustainability.handler;

import static sap.sustainability.ApplicationMessage.AN_UNEXPECTED_ERROR_OCCURRED;
import static sap.sustainability.ApplicationMessage.BUSINESS_ERROR_S;
import static sap.sustainability.ApplicationMessage.EXECUTION_ERROR_OCCURRED;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

/** Handles business exceptions that occur during the execution of commands. */
public class BusinessExceptionHandler implements IExecutionExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(BusinessExceptionHandler.class);

  @Override
  public int handleExecutionException(
      Exception exception, CommandLine commandLine, ParseResult parseResult) {
    LOGGER.error(EXECUTION_ERROR_OCCURRED, exception);
    String errorMessage = String.format(AN_UNEXPECTED_ERROR_OCCURRED, exception.getMessage());
    commandLine.getErr().println(String.format(BUSINESS_ERROR_S, errorMessage));
    return commandLine.getCommandSpec().exitCodeOnExecutionException();
  }
}
