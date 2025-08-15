# Runbook

To set up the environment and run the project, we have advised a few options.
First, in `.run`, you can find Main.run.xml, which is a configuration for Intellij.
So, in case anyone interested, they can use this configuration to run the project or perform debugging.

Furthermore, we have provided to different ways to run the project as a command line application.
One is as fat jar, which is a single jar file that contains all the dependencies,
that can be run with java -jar command, shell script, or batch file.

Second, we have made use of GraalVM, which is a native image generator,
and we can create a native image of the application that can be executed on the host machine as an executable file.

Third, we have given some examples of executing our CLI app with different parameters to showcase different scenarios.

### Attention

* Please note that the project is using some libraries that work with annotation processing.
  if you see class definitions are not found, please run `./gradlew clean build` command to ensure that the
  annotation processing is done and extra classes are generated.
* This project is using JDK 21, so please make sure that you have JDK 21 installed on your machine.
  In case you are using sap machine JDK 21, which is a free and open-source JDK distribution,
  as it does not have GraalVM available, GraalVM native image generation needs additional steps as described below.
* Please note that the API key is defined as and environment variable as ORS_TOKEN.
* Before progressing, please use `gradlew.bat` for windows users, and `./gradlew` for Unix based systems.

### Building the Project and Test Execution

To build the project and run the tests, you can use the following command:

```text  
./gradlew clean build
```

Please note that the jar file that is generated in `build/libs` is not a fat jar, but rather a regular jar file.
This jar file will not include the dependencies required to run the application.
However, it is useful for running the tests and checking the code quality.

And, to only run the tests, you can use:

```text
./gradlew clean test
```

### Fat Jar Generation

In our build.gradle file, we have configured the shadow plugin to create a fat jar.
This jar file will include all the dependencies required to run the application.
To generate the fat jar, you can run the following command:

```text
./gradlew clean shadowJar
```

However, the above command will only generate the fat jar in `build/libs`, and to run it we can use java -jar command as
below:

```text
java -jar build/libs/co2-calculator.jar --start Hamburg --end Berlin --transportation-method diesel-car-medium
```

Nonetheless, this does not meet the criteria of the task indicating to run the CLI app as below:

```text
 ./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium
```

To this end, we have made use of the following commands to generate a TAR or ZIP containing a shell script, a batch
file in `/bin` and the fat jar in `/lib`.

```text
./gradlew clean shadowDistTar
```

```text
./gradlew clean shadowDistZip
```

Running the above commands generate a TAR or ZIP in build/distributions.
In case of TAR, after extracting the TAR, and navigating to the extracted folder, you can run the application using the
following command:

```text
./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium
```

Finally, for the sake of easiness, we have registered a command that generates a fat jar, creates a TAR and a ZIP
distribution.

```text
./gradlew clean outShadowJarAll
```

### Native Image Generation with GraalVM

The second option is to create a native image of the application.
In this case, we have used the GraalVM native image generator to compile the application into a native executable.
This approach is beneficial for performance and startup time, as it eliminates the need for a JVM at runtime.
Furthermore, in native images, memory footprint is reduced.

However, generating a native image has some prerequisites:

- You need to have GraalVM installed on your machine.
- Or use a JDK that supports native image generation, i.e., includes the `native-image` tool.
- Or use a docker image that has GraalVM installed. However, in this case, we were only able to create executables for
  Linux machines only.

Progressing with the first option, you can follow these steps to generate a native image for Unix based systems as we
did not have any Windows machine to test this approach:

- Install SDKMAN (if not already installed) by following the instructions at https://sdkman.io/install.
- Install GraalVM Community Edition with SDKMAN via by following the instructions
  at https://www.graalvm.org/latest/getting-started/linux/#sdkman
- At the root of the project, run the following command to generate the native image:
- ```text
  ./gradlew clean nativeCompile
  ```
- After the command completes, you will find the native executable in `build/native/nativeCompile/co2-calculator`.
- To run the native executable, you can use the following command:

```text
./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium
```

### Examples

Here are some examples of how to use the CLI application.
This command execution is same for GraalVM native images, and also for running the shadowJar in zip or the tar files
that can be generated as described in the previos sections.
For running the jar file, instead java -jar command should be used.

```text

./co2-calculator --start "Los Angeles" --end "New York" --transportation-method=diesel-car-medium
Your trip caused 770.5kg of CO2-equivalent.

./co2-calculator --end "New York" --start "Los Angeles" --transportation-method=electric-car-large
Your trip caused 328.9kg of CO2-equivalent.

./co2-calculator --start Hamburg --end Berilai --transportation-method diesel-car-medium 
Business Error$ An unexpected error occurred: No features found for city: Berilai.

./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium         
Your trip caused 49.2kg of CO2-equivalent.

./co2-calculator --start "New yorkk" --end Berlin --transportation-method diesel-car-medium
Business Error$ An unexpected error occurred: A perfect match for city: New yorkk was not found. The closest match is: New York City.

./co2-calculator  --end Berlin --transportation-method diesel-car-medium 
Parse Error$ Missing required option: '--start=<startCity>'

./co2-calculator -s Heidelberg -e Berlin -tm=diesel-car-medium
Your trip caused 109.6kg of CO2-equivalent.

./co2-calculator -s Heidelberg -e Berlin -tm=diesel-car-medium (with no internet) 
Business Error$ An unexpected error occurred: Failed to calculate emissions due to I/O error.

./co2-calculator -s Heidelberg -e Berlin -tm=diesel-car-medium (with invalid API key)
Business Error$ An unexpected error occurred: Failed to fetch coordinates for city: Heidelberg, due to http error with status: 403.

./co2-calculator -s Heidelberg -e Berlin -tm=diesel-car-medium
Business Error$ An unexpected error occurred: ORS_TOKEN environment variable is not set.

./co2-calculator --start "New York" --end Berlin --transportation-method diesel-car-medium
Business Error$ An unexpected error occurred: No driving car path exists between the two locations.
```

# Tools and Libraries Decisions

### CLI Library

As for the design and implementation of the project, instead of reinventing the wheel, we have used a sophisticated
CLI library and dependencies.
PICOCLI is a powerful library that allows us to create command line applications with ease.
We have used it to parse the command line arguments and provide a user-friendly interface for the application.
With the powerful APIs and annotations provided by PICOCLI, we were able to implement the command line interface with
little effort.
Furthermore, performing validations and error handling was made easier with PICOCLI's features.

### Dependency Injection

In addition, we thought that maybe Springifying our project could be helpful for using Dependency Injection.
However, we realized that the project is too small to require a full-fledged framework such as Spring as it could be an
absolute overkill.
Furthermore, probably this project is also too small for any kind of Dependency Injection framework.
Nonetheless, we decided to use Dagger, to make sure that we could scale our application in the future.
Dagger is a very popular Dependency Injection library and is widely adopted in industry especially in Android
development.

### Http Client

Moreover, instead of using an asynchronous HTTP client, we have used a synchronous one as this is just a CLI application
and using reactive paradigms does not contribute to this projedct in any positive way,
but rather adds complexity.
Furthermore, instead of using jave.net.http.HttpClient, or Apache HttpClient (though powerful, but always a headache due
to poor documentations),
we have used the OkHttp library, which is a simple and powerful HTTP client that is easy to use and has a good
documentation.
what's more, this OkHttp library is used widely in the industry especially in Android development.

### JSON Parsing

We have used the Jackson library parsing JSON responses from the Open Route Service API.
Instead of using the streaming APIs, which again increases the complexity of the project, we decided to load the entire
json into memory, as when the result is calculated and returned, the application will exit.
Therefore, the allocated memory will by freed as opposed to a long running application, such as web applications.

### Checkstyle and Code Format

We have adopted google code format for java: https://checkstyle.sourceforge.io/reports/google-java-style-20170228.html.
Also, we have made added the checkstyle validation for our project via checkstyle plugin in our gradle file and
also added the corresponding checkstyle xml file at `./config/checkstyle/checkstyle.xml`.
Then, when running the `./gradlew build` command,
the checkstyle validation will be performed for both main and test classes.
We have address so that we do not have any checkstyle violations in our main classes,
but we could not put the same effort into our test classes, and therefore, there are some checkstyle violations.

### Logging Configuration

We have added a logging configuration file at `./config/logback/logback.xml`, in which we have configured a log
appender,
so the logs will be written to a file at the same directory as the executable file, when the executable is run.
This is done to avoid cluttering the console output with logs, and also to have a persistent log file that can be
reviewed later.
