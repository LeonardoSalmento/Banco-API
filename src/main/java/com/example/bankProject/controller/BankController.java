package com.example.bankProject.controller;

import com.example.bankProject.exception.ResourceNotFoundException;
import com.example.bankProject.model.Bank;
import com.example.bankProject.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/banks")
public class BankController {
    @Autowired
    private BankRepository bankRepository;

    @GetMapping("/")
    public List<Bank> getAllBanks(){
        return this.bankRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable(value="id") Long bankId) throws ResourceNotFoundException {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new ResourceNotFoundException("Bank not found for this :: " + bankId));

        return ResponseEntity.ok().body(bank);
    }

    @PostMapping("/")
    public Bank createBank(@RequestBody Bank bank){
        return this.bankRepository.save(bank);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable(value = "id") Long bankId,
                                                   @Validated @RequestBody Bank bankDetails) throws ResourceNotFoundException {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found for this id :: " + bankId));

        bank.setCompensationCode(bankDetails.getCompensationCode());
        bank.setName(bankDetails.getName());
        final Bank updatedBank = bankRepository.save(bank);
        return ResponseEntity.ok(updatedBank);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteBank(@PathVariable(value = "id") Long bankId)
            throws ResourceNotFoundException {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found for this id :: " + bankId));

        bankRepository.delete(bank);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
