package com.leomottadev.desafiopicpaysimplificado.services;

import com.leomottadev.desafiopicpaysimplificado.domain.transaction.Transaction;
import com.leomottadev.desafiopicpaysimplificado.domain.user.User;
import com.leomottadev.desafiopicpaysimplificado.dtos.TransactionDTO;
import com.leomottadev.desafiopicpaysimplificado.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final UserService userService;
    private final TransactionRepository repository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    public TransactionService(UserService userService, TransactionRepository repository, AuthorizationService authorizationService, NotificationService notificationService) {
        this.userService = userService;
        this.repository = repository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
    }

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(sender, transaction.value());

        if(!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        sender.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender,"Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;
    }

}
