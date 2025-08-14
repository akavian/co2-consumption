package sap.sustainability.di;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class DependencyInjectionTest {

  @Test
  public void testDaggerDependencyInjection() {
    CommandLineComponent commandLineComponent = DaggerCommandLineComponent.create();
    CommandLine firstCommandLine = commandLineComponent.getCommandLine();
    CommandLine secondCommandLine = commandLineComponent.getCommandLine();
    assertNotNull(firstCommandLine, "First command line should not be null");
    assertNotNull(secondCommandLine, "Second command line should not be null");
    assertEquals(
        firstCommandLine, secondCommandLine, "Both command lines should be the same instance");
    assertSame(
        firstCommandLine.getCommand(),
        secondCommandLine.getCommand(),
        "Both command lines should have the same command");
  }
}
