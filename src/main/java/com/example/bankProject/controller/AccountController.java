package com.example.bankProject.controller;

import com.example.bankProject.exception.BadRequestException;
import com.example.bankProject.exception.ResourceNotFoundException;
import com.example.bankProject.model.Account;
import com.example.bankProject.repository.AccountRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    private ObjectNode data;

    @GetMapping("/accounts/")
    public List<Account> showAllAccounts(){
        return this.accountRepository.findAll();
    }

    @PostMapping("/accounts/withdraw/{accountId}")
    public Account withdraw(@RequestBody ObjectNode data, @PathVariable Long accountId) throws ResourceNotFoundException, BadRequestException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));

        Double value = transformJsonToDoble(data);

        if (value <= 0){
            throw new BadRequestException("Withdrawal amount must be greater than 0");
        }

        Double newBalance = account.getBalance() - value;

        if (newBalance < 0){
            throw new BadRequestException("Insufficient balance for withdrawal");
        }

        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    @PostMapping("/accounts/deposit/{accountId}")
    public Account deposit(@PathVariable Long accountId, @RequestBody ObjectNode data) throws ResourceNotFoundException, BadRequestException{
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));

        Double value = transformJsonToDoble(data);

        if (value <= 0){
            throw new BadRequestException("Deposited amount must be greater than 0");
        }

        Double newBalance = value + account.getBalance();

        account.setBalance(newBalance);
        return accountRepository.save(account);
    }


    @PostMapping("/accounts/transfer/{accountAId}/{accountBId}")
    public Account transfer(@RequestBody ObjectNode data, @PathVariable Long accountAId, @PathVariable Long accountBId) throws ResourceNotFoundException, BadRequestException{

        if (accountAId == accountBId){
            throw new BadRequestException("You cant transfer money for yourself");
        }

        Account accountA = accountRepository.findById(accountAId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountA not found for this id :: " + accountAId));

        Account accountB = accountRepository.findById(accountBId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountB not found for this id :: " + accountBId));

        Double value = transformJsonToDoble(data);

        if (value <= 0){
            throw new BadRequestException("Deposited amount must be greater than 0");
        }

        Double newBalanceA = accountA.getBalance() - value;

        if (newBalanceA < 0){
            throw new BadRequestException("Insufficient balance for transfer");
        }

        Double newBalanceB = value + accountB.getBalance();

        accountA.setBalance(newBalanceA);
        accountB.setBalance(newBalanceB);
        accountRepository.save(accountA);
        accountRepository.save(accountB);
        return accountA;
    }

    @GetMapping("/accounts/balance/{accountId}")
    public Account showBalance(@PathVariable Long accountId) throws ResourceNotFoundException{
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));

        return accountRepository.save(account);
    }

    private Double transformJsonToDoble(ObjectNode data){
        JsonNode value = data.findValue("value");
        Double newValue = value.doubleValue();
        return newValue;
    }

}
