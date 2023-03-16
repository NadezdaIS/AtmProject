package dto;

import java.util.UUID;

public class User {

    private UUID id;
    private String fullName;
    private String accountNumber;

    private UserCard userCard;

    private double transactionFee;

    public User(UUID id, String fullName, String accountNumber, UserCard userCard, double transactionFee) {
        this.id = id;
        this.fullName = fullName;
        this.accountNumber = accountNumber;
        this.userCard = userCard;
        this.transactionFee = transactionFee;
    }

    public User(String fullName, UserCard userCard, double transactionFee) {
        this.fullName = fullName;
        this.userCard = userCard;
        this.transactionFee = transactionFee;
        this.id = UUID.randomUUID();
        this.accountNumber = String.valueOf(Math.round(Math.floor(Math.random() * 10000000)));
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public UserCard getUserCard() {
        return userCard;
    }

    public void setUserCard(UserCard userCard) {
        this.userCard = userCard;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }
}

