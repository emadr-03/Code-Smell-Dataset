package smellybytype.unannotated.smell8;

import java.util.Objects;
import utility.*;

public class BankAccountSmelly {

    private final AccountHolder accountHolder;
    private final AccountID accountId;
    private Money balance;

    public BankAccountSmelly(AccountHolder accountHolder, AccountID accountId) {
        this.accountHolder = Objects.requireNonNull(accountHolder, "Account holder must not be null.");
        this.accountId = Objects.requireNonNull(accountId, "Account ID must not be null.");
        this.balance = Money.ofCents(0);
    }

    private void validatePositiveAmount(Money amount) {
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    public void setupRecurringTransfer(BankAccountSmelly destinationAccount, Money amount, String frequency, String startDate, String endDate, boolean notifyOnTransfer, int maxRetries) {
        Objects.requireNonNull(destinationAccount, "Destination account must not be null.");
        validatePositiveAmount(amount);
        if (frequency.equals("DAILY") || frequency.equals("WEEKLY") || frequency.equals("MONTHLY")) {
            System.out.println("Recurring transfer set up from " + startDate + " to " + endDate);
            if (notifyOnTransfer) {
                System.out.println("Notifications enabled with max retries: " + maxRetries);
            }
        }
    }

    public void deposit(Money amount) {
        validatePositiveAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public boolean authorizeTransaction(Money amount, String merchantName, String merchantCategory, String location, String currency, double conversionRate, boolean requiresPin, String transactionId) {
        validatePositiveAmount(amount);
        if (this.balance.getAmountInCents() < amount.getAmountInCents()) {
            return false;
        }
        if (!currency.equals("USD")) {
            long convertedAmount = Math.round(amount.getAmountInCents() * conversionRate);
            amount = Money.ofCents(convertedAmount);
        }
        if (requiresPin) {
            System.out.println("PIN verification required for transaction: " + transactionId);
        }
        System.out.println("Transaction authorized at " + merchantName + " (" + merchantCategory + ") in " + location);
        return true;
    }

    public void configureOverdraftProtection(Money overdraftLimit, double overdraftFee, boolean autoTransferFromSavings, BankAccountSmelly savingsAccount, boolean notifyOnOverdraft, String notificationEmail, int maxOverdraftsPerMonth) {
        validatePositiveAmount(overdraftLimit);
        if (autoTransferFromSavings) {
            Objects.requireNonNull(savingsAccount, "Savings account must not be null for auto-transfer.");
        }
        System.out.println("Overdraft protection configured with limit: " + overdraftLimit);
        if (notifyOnOverdraft) {
            System.out.println("Notifications will be sent to: " + notificationEmail);
        }
    }

    public void withdraw(Money amount) {
        validatePositiveAmount(amount);
        this.balance = this.balance.subtract(amount);
    }

    public void transferTo(BankAccountSmelly otherAccount, Money amount) {
        Objects.requireNonNull(otherAccount, "Destination account must not be null.");

        this.withdraw(amount);
        otherAccount.deposit(amount);
    }

    public Money withdrawAll() {
        Money amount = this.balance;
        this.balance = Money.ofCents(0);
        return amount;
    }

    public Money getBalance() {
        return this.balance;
    }

    public AccountHolder getAccountHolder() {
        return this.accountHolder;
    }

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