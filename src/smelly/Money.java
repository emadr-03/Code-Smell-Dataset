package smelly;

import java.util.Objects;

/**
 * Represents a monetary value in cents. This class is immutable.
 * It encapsulates the logic for handling money, ensuring that operations
 * are safe and expressive.
 */
public final class Money {

    private final long amountInCents;

    private Money(long amountInCents) {
        this.amountInCents = amountInCents;
    }

    /**
     * Factory method to create a Money instance from a given amount in cents.
     *
     * @param amountInCents The amount in cents. Must be non-negative.
     * @return A new Money instance.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public static Money ofCents(long amountInCents) {
        if (amountInCents < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative.");
        }
        return new Money(amountInCents);
    }
    
    /**
     * Returns a new Money object that is the sum of this and the other amount.
     *
     * @param other The Money object to add.
     * @return A new Money object representing the sum.
     */
    public Money add(Money other) {
        return new Money(this.amountInCents + other.amountInCents);
    }

    /**
     * Returns a new Money object that is the difference of this and the other amount.
     *
     * @param other The Money object to subtract.
     * @return A new Money object representing the difference.
     * @throws IllegalStateException if the resulting amount would be negative.
     */
    public Money subtract(Money other) {
        if (this.isLessThan(other)) {
            throw new IllegalStateException("Insufficient funds for this operation.");
        }
        return new Money(this.amountInCents - other.amountInCents);
    }

    /**
     * Checks if this amount is greater than another.
     *
     * @param other The Money object to compare against.
     * @return true if this amount is greater, false otherwise.
     */
    public boolean isGreaterThan(Money other) {
        return this.amountInCents > other.amountInCents;
    }

    /**
     * Checks if this amount is less than another.
     *
     * @param other The Money object to compare against.
     * @return true if this amount is less, false otherwise.
     */
    public boolean isLessThan(Money other) {
        return this.amountInCents < other.amountInCents;
    }

    public boolean isPositive() {
        return this.amountInCents > 0;
    }
    
    public long getAmountInCents() {
        return amountInCents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amountInCents == money.amountInCents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInCents);
    }

    @Override
    public String toString() {
        return String.valueOf(amountInCents);
    }
}