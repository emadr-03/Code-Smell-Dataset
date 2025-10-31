package smelly;

import java.util.Objects;

/**
 * A simple bank account.
 * This class ensures that the balance is never negative and that
 * transaction amounts are valid.
 */
public class BankAccountSmelly {

    private final AccountHolder accountHolder;
    private final AccountID accountId;
    private Money balance;

    /**
     * Creates a new bank account with a zero balance.
     *
     * @param accountHolder The account holder. Must not be null.
     * @param accountId     The unique identifier for the account. Must not be null.
     */
    public BankAccountSmelly(AccountHolder accountHolder, AccountID accountId) {
        this.accountHolder = Objects.requireNonNull(accountHolder, "Account holder must not be null.");
        this.accountId = Objects.requireNonNull(accountId, "Account ID must not be null.");
        this.balance = Money.ofCents(0);
    }


    /**
     * Validates if the given amount is positive or not.
     * 
     * @param amount The given amount.
     * @throws IllegalArgumentException if the amount is not positive.
     */
    private void validatePositiveAmount(Money amount) {
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    /**
     * Deposits a specified amount into the account.
     * The amount must be a positive value.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(Money amount) {
        validatePositiveAmount(amount);
        this.balance = this.balance.add(amount);
    }

    /**
     * Withdraws a specified amount from the account.
     * The amount must be positive and not exceed the current balance.
     *
     * @param amount The amount to withdraw.
     */
    public void withdraw(Money amount) {
        validatePositiveAmount(amount);
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Transfers a specified amount from this account to another account.
     *
     * @param otherAccount The account to transfer money to. Must not be null.
     * @param amount       The amount to transfer. Must be positive and not exceed balance.
     * @throws NullPointerException     if otherAccount is null.
     * @throws IllegalStateException    if this account has insufficient funds.
     * @throws IllegalArgumentException if the amount is not positive.
     */
    public void transferTo(BankAccountSmelly otherAccount, Money amount) {
        Objects.requireNonNull(otherAccount, "Destination account must not be null.");

        this.withdraw(amount);
        otherAccount.deposit(amount);
    }

    /**
     * Withdraws all money from the account, leaving it with zero balance.
     *
     * @return The amount withdrawn (previous balance).
     */
    public Money withdrawAll() {
        Money amount = this.balance;
        this.balance = Money.ofCents(0);
        return amount;
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
}