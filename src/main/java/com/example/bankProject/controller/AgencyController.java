package com.example.bankProject.controller;

import com.example.bankProject.exception.ResourceNotFoundException;
import com.example.bankProject.model.Agency;
import com.example.bankProject.model.Bank;
import com.example.bankProject.repository.AgencyRepository;
import com.example.bankProject.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AgencyController {
    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private BankRepository bankRepository;

    @GetMapping("/banks/{bankId}/agencies/")
    public List<Agency> getAgenciesByBankId(@PathVariable Long bankId){
        return this.agencyRepository.findByBankId(bankId);
    }


    @PostMapping("/banks/{bankId}/agencies/")
    public Agency createAgency(@PathVariable Long bankId, @Validated @RequestBody Agency agency) throws ResourceNotFoundException {
        return this.bankRepository.findById(bankId).map(bank -> {
            agency.setBank(bank);
            return this.agencyRepository.save(agency);
        }).orElseThrow(() -> new ResourceNotFoundException("Bank not found with id :: " + bankId));
    }

    @PutMapping("/questions/{questionId}/answers/{agencyId}")
    public Agency updateAgency(@PathVariable Long bankId,
                               @PathVariable Long agencyId,
                               @Validated @RequestBody Agency agencyRequest) throws ResourceNotFoundException {
        if(!bankRepository.existsById(bankId)) {
            throw new ResourceNotFoundException("Bank not found with id " + bankId);
        }

        return agencyRepository.findById(agencyId)
                .map(agency -> {
                    agency.setAgencyCode(agencyRequest.getAgencyCode());
                    agency.setBank(agencyRequest.getBank());
                    return agencyRepository.save(agency);
                }).orElseThrow(() -> new ResourceNotFoundException("Agency not found with id " + agencyId));
    }


    @DeleteMapping("/banks/{bankId}/agencies/{agencyId}")
    public Map<String, Boolean> deleteAgency(@PathVariable Long agencyId)
            throws ResourceNotFoundException {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found for this id :: " + agencyId));

        agencyRepository.delete(agency);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
