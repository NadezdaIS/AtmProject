package controllers;

import dto.User;
import exceptions.NotEnoughMoneyException;
import exceptions.UnauthorizedUserException;
import exceptions.WithdrawalFailedException;
import services.BankService;

public class AtmController {

    public final BankService bankService;

    private User activeUser;
    private boolean isValidated;

    public AtmController(BankService bankService){
        this.bankService = bankService;
    }

    public String insertAndValidateCard(User user, int pin) throws Exception {
        if (this.activeUser != null) throw new Exception("Please remove active card!!");
        if (!user.getUserCard().isPinValid(pin)) throw new Exception("Invalid pin!!!");

        this.isValidated = true;
        this.activeUser = user;

        return "User card validated successfully";
    }

    public String removeCard() {
        this.activeUser = null;
        this.isValidated = false;

        return "Good bye!";
    }

    public String getUserBalance() throws Exception {
        this.verifyUserAuthentication();
        return "your current balance is: " + this.activeUser.getUserCard().getBalance();
    }

    public String deposit(Double amountToDeposit) throws Exception {
        this.verifyUserAuthentication();
        this.bankService.addFundsToUser(this.activeUser, amountToDeposit);
        this.setActiveUser(this.bankService.findUserById(this.activeUser.getId()));
        return "Deposit successful, new balance: " + this.activeUser.getUserCard().getBalance();
    }

    public String withdraw(double amountToWithdraw) throws NotEnoughMoneyException, WithdrawalFailedException, UnauthorizedUserException {
        this.verifyUserAuthentication();
        this.bankService.removeFundsFromUser(this.activeUser, amountToWithdraw);
        return "Withdrawal Successful, new balance is: " + this.activeUser.getUserCard().getBalance();
    }

    private void verifyUserAuthentication() throws UnauthorizedUserException {
        if (!this.isValidated) throw new UnauthorizedUserException("Please validate your card!");
    }

    private void setActiveUser(User user) {
        this.activeUser = user;
    }
}

