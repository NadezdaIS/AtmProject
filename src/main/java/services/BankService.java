package services;

import dto.User;
import exceptions.NotEnoughMoneyException;
import exceptions.UserNotFoundException;
import exceptions.WithdrawalFailedException;

import java.util.ArrayList;
import java.util.UUID;

public class BankService {

    private final ArrayList<User> users = new ArrayList<>();

    public void createUser(User user){
        this.users.add(user);
    }

    public User findUserByAccountNumber(String accountNumber) throws Exception {

        for (User currentUser: this.users){
            if (currentUser.getAccountNumber().equals(accountNumber)){
                return currentUser;
            }
        }

        throw new UserNotFoundException();
    }

    public User findUserByCardId(UUID cardId) throws Exception {
        for (User user : this.users){
            if (user.getUserCard().getId().equals(cardId)) return user;
        }

        throw new UserNotFoundException("User with this card id not found");
    }

    public User findUserById(UUID userId) throws UserNotFoundException {
        for (User user : this.users){
            if (user.getId().equals(userId)) return user;
        }

        throw new UserNotFoundException("User with id not found");
    }

    public void addFundsToUser(User user, Double amount) throws UserNotFoundException {

        for(User currentUser : this.users){
            if (currentUser.getId().equals(user.getId())){
                currentUser.getUserCard().setBalance(currentUser.getUserCard().getBalance() + amount);
                return;
            }
        }

        throw new UserNotFoundException();

    }

    public void removeFundsFromUser(User user, double amount) throws NotEnoughMoneyException, WithdrawalFailedException {
        for (User currentUser : this.users){
            if (currentUser.getId().equals(user.getId())){
                if (currentUser.getUserCard().getBalance() < amount) throw new NotEnoughMoneyException("Insufficient funds!");
                currentUser.getUserCard().setBalance(currentUser.getUserCard().getBalance() - amount);
                return;
            }
        }

        throw new WithdrawalFailedException("Problem with withdrawal");
    }
}
