package smellyunannotated;

public class BankAccountDemo {
    /**
     * A minimal demonstration of the BankAccount class.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("--- BankAccount Demo ---");

        BankAccountSmelly account = new BankAccountSmelly(
            new AccountHolder("Jane Doe"),
            new AccountID("A123456789")
        );
        System.out.println("Created: " + account);

        BankAccountSmelly account2 = new BankAccountSmelly(
            new AccountHolder("John Smith"),
            new AccountID("B987654321")
        );
        System.out.println("Created: " + account2);

        account.deposit(Money.ofCents(10000)); // $100.00
        System.out.println("After depositing 10000 cents in Account 1: " + account.getBalance().getAmountInCents());

        account.withdraw(Money.ofCents(2550)); // $25.50
        System.out.println("After withdrawing 2550 cents in Account 1: " + account.getBalance().getAmountInCents());

        try {
            System.out.println("Attempting to withdraw 99999 cents from Account 1...");
            account.withdraw(Money.ofCents(99999));
        } catch (IllegalStateException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        try {
            System.out.println("Attempting to deposit -500 cents from Account 1...");
            account.deposit(Money.ofCents(-500));
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        try {
            System.out.println("Attempting to transfer 5000 cents from Account 1 to Account 2");
            account.transferTo(account2,Money.ofCents(10000000));
            System.out.println("Transfer successful.");
            System.out.println("Account 1 new balance: " + account.getBalance().getAmountInCents());
            System.out.println("Account 2 new balance: " + account2.getBalance().getAmountInCents());
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
        
        System.out.println("Final state of Account 1: " + account);
        System.out.println("Final state of Account 2: " + account2);
        System.out.println("--- End of Demo ---");
    }
}
