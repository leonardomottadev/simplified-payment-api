package com.leomottadev.desafiopicpaysimplificado.services;

import com.leomottadev.desafiopicpaysimplificado.domain.user.User;
import com.leomottadev.desafiopicpaysimplificado.domain.user.UserType;
import com.leomottadev.desafiopicpaysimplificado.dtos.UserDTO;
import com.leomottadev.desafiopicpaysimplificado.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo lojista não está autorizado a realizar transação");
        }

        if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }
}
