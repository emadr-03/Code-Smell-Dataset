package smellyannotated;

import java.util.Objects;

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
    private String accountStatus;
    private int accountTypeCode;
    private Money pendingTransferAmount;
    private BankAccountSmelly temporaryDestinationAccount;
    private BankBranch homeBranch;
    private TransactionLogger transactionLogger;
    private AccountSecurityManager securityManager;
    private NotificationService notificationService;

    public BankAccountSmelly(AccountHolder accountHolder, AccountID accountId) {
        this.accountHolder = Objects.requireNonNull(accountHolder, "Account holder must not be null.");
        this.accountId = Objects.requireNonNull(accountId, "Account ID must not be null.");
        this.balance = Money.ofCents(0);
        this.accountStatus = "ACTIVE";
        this.accountTypeCode = 1;
    }

    public void updateContactInfo(String street, String city, String zipCode) {
        System.out.println("Updating address: " + street + ", " + city + ", " + zipCode);
    }

    private void validatePositiveAmount(Money amount) {
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    public void deposit(Money amount) {
        validatePositiveAmount(amount);
        
        //Check if account is not closed before depositing
        if (!this.accountStatus.equals("CLOSED")) {
            this.balance = this.balance.add(amount);
        }
    }

    public boolean canAffordPurchase(Money purchaseAmount) {
        return this.balance.getAmountInCents() >= purchaseAmount.getAmountInCents();
    }

    public String generateAccountStatement(String startDate, String endDate) {
        StringBuilder statement = new StringBuilder();
        statement.append("=== ACCOUNT STATEMENT ===\n");
        statement.append("Account ID: ").append(this.accountId).append("\n");
        statement.append("Account Holder: ").append(this.accountHolder).append("\n");
        statement.append("Statement Period: ").append(startDate).append(" to ").append(endDate).append("\n");
        statement.append("Current Balance: ").append(this.balance).append("\n");
        statement.append("------------------------\n");

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

    private void validatePositiveAmountAgain(Money amount) {
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    private void validatePositiveMoneyAmount(Money amount) {
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    public void setOwnerDetails(String firstName, String lastName, String middleName) {
        System.out.println("Owner: " + firstName + " " + middleName + " " + lastName);
    }

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

        //Reject high loan amounts for low credit scores
        if (loanAmount.getAmountInCents() > 100000000 && creditScore < 750) {
            return false;
        }
        return true;
    }

    public boolean checkSecurityAlert(AccountID accountId) {
        return this.securityManager.hasAlert(accountId);
    }

    public void setAccountStatus(String status) {
        if (status.equals("ACTIVE") || status.equals("FROZEN") || status.equals("CLOSED")) {
            this.accountStatus = status;
        }
    }

    public void performEndOfYearProcessing(int year, double interestRate, double maintenanceFee, boolean sendStatement) {
        long currentCents = this.balance.getAmountInCents();
        double yearlyInterest = currentCents * interestRate;
        long interestCents = Math.round(yearlyInterest);
        this.balance = Money.ofCents(currentCents + interestCents);
        long feeCents = Math.round(maintenanceFee * 100);
        if (this.balance.getAmountInCents() >= feeCents) {
            this.balance = this.balance.subtract(Money.ofCents(feeCents));
        }
        if (sendStatement) {
            String statement = "Year-End Statement for " + year + "\n";
            statement += "Account: " + this.accountId + "\n";
            statement += "Interest Applied: $" + String.format("%.2f", yearlyInterest / 100) + "\n";
            statement += "Maintenance Fee: $" + String.format("%.2f", maintenanceFee) + "\n";
            statement += "Final Balance: " + this.balance + "\n";
            System.out.println(statement);
        }
    }

    private void transferFundsTo(BankAccountSmelly destinationAccount, Money transferAmount) {
        Objects.requireNonNull(destinationAccount, "Destination account must not be null.");

        this.withdraw(transferAmount);
        destinationAccount.deposit(transferAmount);
    }

    public boolean isBalanceGreaterThan(Money threshold) {
        return this.balance.getAmountInCents() > threshold.getAmountInCents();
    }

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

    public String getBranchManagerName() {
        return this.homeBranch.getManager().getPersonalInfo().getName();
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

    public boolean authorizeTransaction(Money amount, String merchantName, String merchantCategory, String location, String currency, double conversionRate, boolean requiresPin, String transactionId) {
        validatePositiveAmount(amount);
        //Ensure sufficient balance before authorizing
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

    public void sendNotification(NotificationMessage message) {
        this.notificationService.send(message);
    }

    public TransactionHistory getTransactionHistory() {
        return this.transactionLogger.getHistory();
    }

    public String calculateTaxReport(int year, double taxRate) {
        StringBuilder report = new StringBuilder();
        report.append("Tax Report for Year: ").append(year).append("\n");
        report.append("Account: ").append(this.accountId).append("\n");
        report.append("Holder: ").append(this.accountHolder).append("\n");
        long balanceCents = this.balance.getAmountInCents();
        double balanceDollars = balanceCents / 100.0;
        report.append("Current Balance: $").append(String.format("%.2f", balanceDollars)).append("\n");
        double estimatedInterest = balanceDollars * 0.02;
        report.append("Estimated Interest Earned: $").append(String.format("%.2f", estimatedInterest)).append("\n");
        double taxOwed = estimatedInterest * taxRate;
        report.append("Tax Rate: ").append(String.format("%.2f%%", taxRate * 100)).append("\n");
        report.append("Estimated Tax Owed: $").append(String.format("%.2f", taxOwed)).append("\n");
        report.append("Report Generated: ").append(java.time.LocalDate.now()).append("\n");
        return report.toString();
    }

    public String getBranchCity() {
        return this.homeBranch.getAddress().getCity().getName();
    }

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

    public void initiateWireTransfer(BankAccountSmelly destination, Money amount) {
        this.pendingTransferAmount = amount;
        this.temporaryDestinationAccount = destination;
    }

    public void completeWireTransfer() {
        if (this.pendingTransferAmount != null && this.temporaryDestinationAccount != null) {
            this.transferTo(this.temporaryDestinationAccount, this.pendingTransferAmount);
            this.pendingTransferAmount = null;
            this.temporaryDestinationAccount = null;
        }
    }

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

    public String formatHolderName() {
        return "Name of the Account Holder: " + this.accountHolder.getName();
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

    @Override
    public boolean supportsChecks() {
        return false;
    }

    @Override
    public void issueCheckbook(int numberOfChecks) {
        throw new UnsupportedOperationException("This account type does not support checks");
    }

    public boolean verifyIdentity(String documentType, String documentNumber, String issuingCountry) {
        return documentType != null && documentNumber != null && issuingCountry != null;
    }

    public void applyMonthlyInterest(double interestRate, boolean compoundDaily, int daysInMonth) {
        if (interestRate < 0 || interestRate > 1) {
            throw new IllegalArgumentException("Interest rate must be between 0 and 1");
        }
        long currentCents = this.balance.getAmountInCents();
        double interestAmount;
        if (compoundDaily) {
            double dailyRate = interestRate / 365.0;
            double compoundFactor = Math.pow(1 + dailyRate, daysInMonth);
            interestAmount = currentCents * (compoundFactor - 1);
        } else {
            double monthlyRate = interestRate / 12.0;
            interestAmount = currentCents * monthlyRate;
        }
        long interestCents = Math.round(interestAmount);
        this.balance = Money.ofCents(currentCents + interestCents);
    }

    public PhoneNumber getAccountHolderPhoneNumber() {
        return this.accountHolder.getContactInfo().getPrimaryPhone().getNumber();
    }

    @Override
    public void enableInternationalTransactions() {
        throw new UnsupportedOperationException("International transactions not supported");
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