package sap.sustainability.di;

import dagger.Binds;
import dagger.Module;
import jakarta.inject.Singleton;
import sap.sustainability.service.Co2Calculator;
import sap.sustainability.service.impl.Co2CalculatorImpl;

/** Dagger module for binding the Co2Calculator interface to its implementation. */
@Module
public abstract class CommandLineBindModule {

  @Binds
  @Singleton
  abstract Co2Calculator bindCo2Calculator(Co2CalculatorImpl calculatorImpl);
}
