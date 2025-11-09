package smellybytype.unannotated.smell12;

import java.util.Objects;
import utility.*;

class BaseAccount {
    protected String accountCategory;
    protected boolean isInternational;
    
    public void enableInternationalTransactions() {
        this.isInternational = true;
    }
    
    public void setCategory(String category) {
        this.accountCategory = category;
    }
    
    public String getCategory() {
        return this.accountCategory;
    }
    
    public boolean supportsChecks() {
        return true;
    }
    
    public void issueCheckbook(int numberOfChecks) {
        System.out.println("Issuing " + numberOfChecks + " checks");
    }
}

public class BankAccountSmelly extends BaseAccount {

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

    @Override
    public boolean supportsChecks() {
        return false;
    }

    public void deposit(Money amount) {
        validatePositiveAmount(amount);
        this.balance = this.balance.add(amount);
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

    @Override
    public void issueCheckbook(int numberOfChecks) {
        throw new UnsupportedOperationException("This account type does not support checks");
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

    @Override
    public void enableInternationalTransactions() {
        throw new UnsupportedOperationException("International transactions not supported");
    }
}