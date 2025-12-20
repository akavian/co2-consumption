package ali.sustainability.di;

import dagger.Component;
import jakarta.inject.Singleton;
import picocli.CommandLine;

/**
 * Dagger component for providing dependencies to the command layer.
 */
@Singleton
@Component(modules = {CommandLineProvideModule.class, CommandLineBindModule.class})
public interface CommandLineComponent {

    /**
     * Builds and provides an instance of Co2Command.
     *
     * @return CommandLine instance
     */
    CommandLine getCommandLine();
}
