package smellybytype.unannotated.smell5;

import java.util.Objects;
import utility.*;

public class BankAccountSmelly {

    private final AccountHolder accountHolder;
    private final AccountID accountId;
    private Money balance;
    private BankBranch homeBranch;

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

    public void deposit(Money amount) {
        validatePositiveAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public boolean canAffordPurchase(Money purchaseAmount) {
        return this.balance.getAmountInCents() >= purchaseAmount.getAmountInCents();
    }

    public boolean isBalanceGreaterThan(Money threshold) {
        return this.balance.getAmountInCents() > threshold.getAmountInCents();
    }

    public String checkBranchOperationalStatus(java.time.LocalTime now) {
        if (this.homeBranch.isOpen(now) && this.homeBranch.hasStaff()) {
            return "Branch is fully operational.";
        } else {
            return "Branch is currently closed or understaffed.";
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