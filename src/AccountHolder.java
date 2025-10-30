import java.util.Objects;

/**
 * Represents the name of an account holder. This class is immutable.
 * It ensures the name is always in a valid state (not null or blank).
 */
public final class AccountHolder {

    private final String name;

    public AccountHolder(String name) {
        if (Objects.requireNonNull(name, "Account holder must not be null.").isBlank()) {
            throw new IllegalArgumentException("Account holder must not be blank.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHolder that = (AccountHolder) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}