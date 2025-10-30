import java.util.Objects;

/**
 * Models a simple bank account using value objects for domain concepts.
 * This class ensures that the balance is never negative and that
 * transaction amounts are valid.
 */
public final class BankAccount {

    private final AccountHolder accountHolder;
    private final AccountID accountId;
    private Money balance;

    /**
     * Creates a new bank account with a zero balance.
     *
     * @param accountHolder The account holder. Must not be null.
     * @param accountId     The unique identifier for the account. Must not be null.
     */
    public BankAccount(AccountHolder accountHolder, AccountID accountId) {
        this.accountHolder = Objects.requireNonNull(accountHolder, "Account holder must not be null.");
        this.accountId = Objects.requireNonNull(accountId, "Account ID must not be null.");
        this.balance = Money.ofCents(0);
    }

    /**
     * Deposits a specified amount into the account.
     * The amount must be a positive value.
     *
     * @param amount The amount to deposit.
     * @throws IllegalArgumentException if the amount is not positive.
     */
    public void deposit(Money amount) {
        // Validation for positive deposit amount can be handled here or inside Money
        if (amount.getAmountInCents() <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance = this.balance.add(amount);
    }

    /**
     * Withdraws a specified amount from the account.
     * The amount must be positive and not exceed the current balance.
     *
     * @param amount The amount to withdraw.
     * @throws IllegalArgumentException if the amount is not positive.
     * @throws IllegalStateException    if the withdrawal amount exceeds the current balance.
     */
    public void withdraw(Money amount) {
        if (amount.getAmountInCents() <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        // The check for sufficient funds is now delegated to the Money object's subtract method.
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Gets the current account balance.
     *
     * @return The current account balance as a Money object.
     */
    public Money getBalance() {
        return this.balance;
    }

    /**
     * Gets the account holder.
     *
     * @return The account holder.
     */
    public AccountHolder getAccountHolder() {
        return this.accountHolder;
    }

    /**
     * Gets the account's unique ID.
     *
     * @return The account's unique ID.
     */
    public AccountID getAccountId() {
        return this.accountId;
    }

    @Override
    public String toString() {
        return "BankAccount[accountId=" + accountId +
               ", accountHolder=" + accountHolder +
               ", balance=" + balance + " cents]";
    }

    /**
     * A minimal demonstration of the BankAccount class.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("--- BankAccount Demo ---");

        // 1. Create a new account
        BankAccount account = new BankAccount(
            new AccountHolder("Jane Doe"),
            new AccountID("A123456789")
        );
        System.out.println("Created: " + account);

        // 2. Deposit money
        account.deposit(Money.ofCents(10000)); // $100.00
        System.out.println("After deposit 10000 cents: " + account.getBalance().getAmountInCents());

        // 3. Withdraw money
        account.withdraw(Money.ofCents(2550)); // $25.50
        System.out.println("After withdraw 2550 cents: " + account.getBalance().getAmountInCents());

        // 4. Demonstrate failed withdrawal (insufficient funds)
        try {
            System.out.println("Attempting to withdraw 99999 cents...");
            account.withdraw(Money.ofCents(99999));
        } catch (IllegalStateException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        // 5. Demonstrate failed deposit (invalid amount)
        try {
            System.out.println("Attempting to deposit -500 cents...");
            // The Money.ofCents factory method will throw an exception here
            account.deposit(Money.ofCents(-500));
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
        
        System.out.println("Final state: " + account);
        System.out.println("--- End of Demo ---");
    }
}