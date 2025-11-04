package smellyunannotated;

import java.util.Objects;

/**
 * Represents a bank account's unique identifier. This class is immutable.
 * It ensures the ID is always in a valid state (not null or blank).
 */
public final class AccountID {

    private final String value;

    public AccountID(String value) {
        if (Objects.requireNonNull(value, "Account ID must not be null.").isBlank()) {
            throw new IllegalArgumentException("Account ID must not be blank.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountID accountId = (AccountID) o;
        return value.equals(accountId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}