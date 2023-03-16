package controllers;

import dto.User;
import dto.UserCard;
import exceptions.NotEnoughMoneyException;
import exceptions.UnauthorizedUserException;
import exceptions.UserNotFoundException;
import exceptions.WithdrawalFailedException;
import services.BankService;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class MenuController {

    private final AtmController atmController = new AtmController(new BankService());
    public MenuController() {}


    public void start() {
        JOptionPane.showConfirmDialog(null, "Welcome to ATM" +
                " Please choose an option on the next prompt");
        this.displayMainMenu();
    }

    private void displayMainMenu() {
        String option = this.getUserInput("Write the number for activity you want to perform\n" +
                "1. Insert Card\n" +
                "2. Withdraw\n" +
                "3. Deposit\n" +
                "4. See balance\n" +
                "5. Remove Card\n" +
                "6. Create Account\n" +
                "7. Close / Exit\n"
        );

        switch (option) {
            case "1":
                this.handleCardInsertion();
                break;
            case "2":
                this.handleWithdrawal();
                break;
            case "3":
                this.handleDeposit();
                break;
            case "4":
                this.handleSeeBalance();
                break;
            case "5":
                this.handleCardRemoval();
                break;
            case "6":
                this.handleAccountCreation();
                break;
            case "7":
                System.exit(0);
        }

        this.displayMainMenu();
    }

    private void handleCardRemoval() {
        try {
            this.displayMessage(this.atmController.removeCard());
        } catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }

    private void handleWithdrawal() {
        try {
            double amountToWithdraw = Double.parseDouble(this.getUserInput("Enter the amount to withdraw"));
            this.displayMessage(this.atmController.withdraw(amountToWithdraw));
        } catch (WithdrawalFailedException exception){
            System.out.println("Special exception block triggred");
            this.displayMessage( "Withdrawal Failed: " +exception.getMessage());
        }
        catch (NotEnoughMoneyException exception){
            this.displayMessage( "Insufficient funds exception: " +exception.getMessage());
        } catch (UnauthorizedUserException e) {
            this.displayMessage(e.getMessage());
        }
    }

    private void handleSeeBalance() {
        try {
            this.displayMessage(this.atmController.getUserBalance());
        }catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }

    private void handleDeposit() {
        try {
            double amountToDeposit = Double.parseDouble(this.getUserInput("Enter the amount to deposit"));
            this.displayMessage(this.atmController.deposit(amountToDeposit));
        } catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }

    private void handleAccountCreation() {
        String name = this.getUserInput("Enter your name");
        String cardPin = this.getUserInput("Choose your card pin");

        Date expiryDate = new Date(2025, Calendar.MARCH, 10);

        UserCard userCard = new UserCard(expiryDate, 0D, Integer.parseInt(cardPin));

        User user = new User(name, userCard, 2.5);

        this.atmController.bankService.createUser(user);
        this.displayMessage("Your account is created with account number: " + user.getAccountNumber() );

    }

    private void handleCardInsertion() {
        String accountNumber = this.getUserInput("Enter your account number");
        String pin = this.getUserInput("Enter your pin");

        try {
            User user = this.atmController.bankService.findUserByAccountNumber(accountNumber);
            this.displayMessage(this.atmController.insertAndValidateCard(user, Integer.parseInt(pin)));
        } catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }

    }


    private String getUserInput(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}

