package Burst.Engine.Source.Core.Util;

/**
 * @author Oliver Schuetz
 * @brief This class is used as a pointer to a value.
 *
 * <h1>Scalar</h1>
 *
 * <p>
 * This class is used as a pointer to a value.
 * Because Java doesn't have pointers, this class is used to simulate a pointer.
 * The main purpose of this class is to allow ImGui to modify values.
 * </p>
 *
 * <p>
 * This class contains a single value of type T.
 * the type T can be any primitive number type or a String.
 * The value passed in the constructor is the reset value.
 * </p>
 *
 * <p>
 * It has several methods for manipulating the value.
 * The value can be set, incremented, decremented, and reset.
 * The value can also be converted to a String.
 * The value can also be compared to another Scalar.
 * </p>
 */
public class Scalar<T> {
  private T value;
  private T resetValue;
  private String name;

  /**
   * Constructor for the Scalar class.
   * @param value the value to be assigned
   */
  public Scalar(T value) {
    this.value = value;
    this.resetValue = value;
    this.name = "";
  }

  public Scalar(String name, T value) {
    this(value);
    this.name = name;
  }

  /**
   * Sets the value to a new value.
   * @param newValue the new value to be assigned
   */
  public void set(T newValue) {
    this.value = newValue;
  }

    /**
     * Returns the current value.
     * @return the current value
     */

    public T get() {
        return value;
    }

    /**
     * Returns the name of the Scalar.
     * @return the name of the Scalar
     */
    public String getName() {
        return name;
    }

  /**
   * Increments the current value by the specified value.
   * @param incrementValue the value by which to increment
   */
  public void increment(T incrementValue) {
    // Check if the type T is a number
    if (value instanceof Number && incrementValue instanceof Number) {
      Number currentValue = (Number) value;
      Number increment = (Number) incrementValue;

      if (currentValue instanceof Byte || currentValue instanceof Short ||
              currentValue instanceof Integer || currentValue instanceof Long) {
        long sum = currentValue.longValue() + increment.longValue();
        value = (T) Long.valueOf(sum);
      } else if (currentValue instanceof Float || currentValue instanceof Double) {
        double sum = currentValue.doubleValue() + increment.doubleValue();
        value = (T) Double.valueOf(sum);
      }
    }
  }

  /**
   * Decrements the current value by the specified value.
   * @param decrementValue the value by which to decrement
   */
  public void decrement(T decrementValue) {
    // Check if the type T is a number
    if (value instanceof Number && decrementValue instanceof Number) {
      Number currentValue = (Number) value;
      Number decrement = (Number) decrementValue;

      if (currentValue instanceof Byte || currentValue instanceof Short ||
              currentValue instanceof Integer || currentValue instanceof Long) {
        long diff = currentValue.longValue() - decrement.longValue();
        value = (T) Long.valueOf(diff);
      } else if (currentValue instanceof Float || currentValue instanceof Double) {
        double diff = currentValue.doubleValue() - decrement.doubleValue();
        value = (T) Double.valueOf(diff);
      }
    }
  }

  /**
   * Resets the value to the original value.
   */
  public void reset() {
    this.value = resetValue;
  }

  /**
   * Returns the current value as a string.
   * @return the current value as a string
   */
  public String toString() {
    return value.toString();
  }

  /**
   * Compares the current value with another Scalar.
   * @param other the other Scalar to compare with
   * @return true if the values are equal, false otherwise
   */
  public boolean equals(Scalar<T> other) {
    return this.value.equals(other.value);
  }
}
