package smellybytype.annotated.TemporaryField;

import java.util.Objects;
import utility.*;

/**
 * A simple bank account.
 * This class ensures that the balance is never negative and that
 * transaction amounts are valid.
 * 
 * Errors in the code are due to object types created specifically to prevent
 * new Primitive Obsession smells from appearing, but the relative classes do not actually exist in the project.
 */

public class BankAccountSmelly {

    private final AccountHolder accountHolder;
    private final AccountID accountId;
    private Money balance;
    //Temporary Field
    private Money pendingTransferAmount;
    //Temporary Field
    private BankAccountSmelly temporaryDestinationAccount;

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

    //Used for TF smell
    public void initiateWireTransfer(BankAccountSmelly destination, Money amount) {
        this.pendingTransferAmount = amount;
        this.temporaryDestinationAccount = destination;
    }

    //Used for TF smell
    public void completeWireTransfer() {
        if (this.pendingTransferAmount != null && this.temporaryDestinationAccount != null) {
            this.transferTo(this.temporaryDestinationAccount, this.pendingTransferAmount);
            this.pendingTransferAmount = null;
            this.temporaryDestinationAccount = null;
        }
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