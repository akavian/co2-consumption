package ali.sustainability.di;

import ali.sustainability.service.Co2Calculator;
import ali.sustainability.service.impl.Co2CalculatorImpl;
import dagger.Binds;
import dagger.Module;
import jakarta.inject.Singleton;

/**
 * Dagger module for binding the Co2Calculator interface to its implementation.
 */
@Module
public abstract class CommandLineBindModule {

    @Binds
    @Singleton
    abstract Co2Calculator bindCo2Calculator(Co2CalculatorImpl calculatorImpl);
}
