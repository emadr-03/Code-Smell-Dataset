package smellybytype.annotated.PrimitiveObsession11;

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
    private String accountStatus; //Primitive Obsession
    private int accountTypeCode; //Primitive Obsession

    public BankAccountSmelly(AccountHolder accountHolder, AccountID accountId) {
        this.accountHolder = Objects.requireNonNull(accountHolder, "Account holder must not be null.");
        this.accountId = Objects.requireNonNull(accountId, "Account ID must not be null.");
        this.balance = Money.ofCents(0);
        this.accountStatus = "ACTIVE"; //Primitive Obsession
        this.accountTypeCode = 1; //Primitive Obsession
    }

    private void validatePositiveAmount(Money amount) {
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    public void deposit(Money amount) {
        validatePositiveAmount(amount);
        
        //Primitive Obsession
        //"Comments" SMELL
        //@@Check if account is not closed before depositing
        if (!this.accountStatus.equals("CLOSED")) {
            this.balance = this.balance.add(amount);
        }
    }

    //Long Method
    public String generateAccountStatement(String startDate, String endDate) {
        StringBuilder statement = new StringBuilder();
        statement.append("=== ACCOUNT STATEMENT ===\n");
        statement.append("Account ID: ").append(this.accountId).append("\n");
        statement.append("Account Holder: ").append(this.accountHolder).append("\n");
        statement.append("Statement Period: ").append(startDate).append(" to ").append(endDate).append("\n");
        statement.append("Current Balance: ").append(this.balance).append("\n");
        statement.append("------------------------\n");

        //Primitive Obsession
        if (this.accountTypeCode == 1) {
            statement.append("Account Type: Checking\n");
        } else if (this.accountTypeCode == 2) {
            statement.append("Account Type: Savings\n");
        } else {
            statement.append("Account Type: Business\n");
        }
        
        statement.append("Interest Rate: 0.00%\n");
        statement.append("Monthly Fee: $0.00\n");
        statement.append("Overdraft Protection: No\n");
        statement.append("========================\n");
        return statement.toString();
    }

    //Primitive Obsession
    public void setAccountStatus(String status) {
        if (status.equals("ACTIVE") || status.equals("FROZEN") || status.equals("CLOSED")) {
            this.accountStatus = status;
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