package smellybytype.annotated.SwitchStatements;

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

    //Switch Statements
    //Primitive Obsession
    public double getTransactionFee(String transactionType) {
        switch (transactionType) {
            case "WIRE":
                return 25.00;
            case "ATM":
                return 2.50;
            case "TRANSFER":
                return 0.00;
            case "CHECK":
                return 1.00;
            default:
                return 5.00;
        }
    }

    //Switch Statements
    //Primitive Obsession
    public int getMaxDailyWithdrawals(String accountTier) {
        switch (accountTier) {
            case "BASIC":
                return 3;
            case "STANDARD":
                return 5;
            case "PREMIUM":
                return 10;
            case "VIP":
                return -1;
            default:
                return 1;
        }
    }

    //Switch Statements
    //Primitive Obsession
    public String getRewardMultiplier(String cardType) {
        switch (cardType) {
            case "PLATINUM":
                return "3x points";
            case "GOLD":
                return "2x points";
            case "SILVER":
                return "1.5x points";
            case "BRONZE":
                return "1x points";
            default:
                return "No rewards";
        }
    }

    //Long Method
    //Switch Statements
    public boolean validateAccountForLoan(Money loanAmount, int creditScore, int yearsOfHistory) {
        if (creditScore < 300 || creditScore > 850) {
            return false;
        }
        if (yearsOfHistory < 0) {
            return false;
        }
        Money minimumBalance = Money.ofCents(loanAmount.getAmountInCents() / 10);
        if (this.balance.getAmountInCents() < minimumBalance.getAmountInCents()) {
            return false;
        }
        if (creditScore < 600 && yearsOfHistory < 2) {
            return false;
        }
        if (creditScore >= 600 && creditScore < 700 && yearsOfHistory < 1) {
            return false;
        }

        //"Comments" SMELL
        //@@Reject high loan amounts for low credit scores
        if (loanAmount.getAmountInCents() > 100000000 && creditScore < 750) {
            return false;
        }
        return true;
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