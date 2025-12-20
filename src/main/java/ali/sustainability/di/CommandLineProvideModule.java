package ali.sustainability.di;

import dagger.Module;
import dagger.Provides;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import picocli.CommandLine;
import ali.sustainability.command.Co2Command;
import ali.sustainability.handler.BusinessExceptionHandler;
import ali.sustainability.handler.ParameterExceptionHandler;
import ali.sustainability.service.Co2Calculator;
import ali.sustainability.util.Environment;
import ali.sustainability.util.HttpUtility;

/**
 * Dagger module that provides core dependencies for the command layer.
 */
@Module
public class CommandLineProvideModule {

    /**
     * Provides a singleton instance of OkHttpClient.
     *
     * @return OkHttpClient instance
     */
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    /**
     * Provides a singleton instance of HttpUtility.
     *
     * @param okHttpClient the OkHttpClient to use
     * @return HttpUtility instance
     */
    @Provides
    @Singleton
    public HttpUtility provideHttpUtility(OkHttpClient okHttpClient) {
        return new HttpUtility(okHttpClient);
    }

    /**
     * Provides a singleton instance of Environment.
     *
     * @return Environment instance
     */
    @Provides
    @Singleton
    public Environment provideEnvironment() {
        return new Environment();
    }

    /**
     * Provides a singleton instance of Co2Command.
     *
     * @param co2Calculator the HttpUtility to use for network operations
     * @return Co2Command instance
     */
    @Provides
    @Singleton
    public Co2Command provideCo2Command(Co2Calculator co2Calculator) {
        return new Co2Command(co2Calculator);
    }

    /**
     * Provides a singleton instance of ParameterExceptionHandler.
     *
     * @return ParameterExceptionHandler instance
     */
    @Provides
    @Singleton
    public ParameterExceptionHandler provideParameterExceptionHandler() {
        return new ParameterExceptionHandler();
    }

    /**
     * Provides a singleton instance of BusinessExceptionHandler.
     *
     * @return BusinessExceptionHandler instance
     */
    @Provides
    @Singleton
    public BusinessExceptionHandler provideBusinessExceptionHandler() {
        return new BusinessExceptionHandler();
    }

    /**
     * Provides a singleton instance of CommandLine.
     *
     * @param co2Command                the Co2Command instance to use as the main command
     * @param parameterExceptionHandler the handler for parameter-related exceptions
     * @param businessExceptionHandler  the handler for business logic-related exceptions
     * @return CommandLine instance configured with the provided handlers and command
     */
    @Provides
    @Singleton
    public CommandLine provideCommandLine(
            Co2Command co2Command,
            ParameterExceptionHandler parameterExceptionHandler,
            BusinessExceptionHandler businessExceptionHandler) {

        CommandLine commandLine = new CommandLine(co2Command);
        commandLine.setParameterExceptionHandler(parameterExceptionHandler);
        commandLine.setExecutionExceptionHandler(businessExceptionHandler);
        return commandLine;
    }
}
