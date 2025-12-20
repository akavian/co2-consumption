package ali.sustainability.util;

/**
 * Utility class for environment-related operations.
 */
public class Environment {

    /**
     * Returns the value of the specified environment variable.
     *
     * @param key the name of the environment variable
     * @return the value of the environment variable, or {@code null} if not found
     */
    public String getEnvironment(String key) {
        return System.getenv(key);
    }
}
