/**
 * Interface for Command design pattern. Classes implementing this interface should always have it's result
 * in StringBuilder so it can be used again if needed.
 */
public interface iWeatherCommand {
    StringBuilder result = new StringBuilder();
}
