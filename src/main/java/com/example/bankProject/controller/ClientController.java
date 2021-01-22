package com.example.bankProject.controller;

import com.example.bankProject.exception.ResourceNotFoundException;
import com.example.bankProject.model.Account;
import com.example.bankProject.model.Agency;
import com.example.bankProject.model.Client;
import com.example.bankProject.repository.AccountRepository;
import com.example.bankProject.repository.AgencyRepository;
import com.example.bankProject.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @GetMapping("/clients/")
    public List<Client> getAllClients(){
        return this.clientRepository.findAll();
    }


    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value="id") Long clientId) throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client not found for this :: " + clientId));

        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/accounts/clients/{agencyId}")
    public Client createClient(@PathVariable Long agencyId ,@RequestBody Client client) throws ResourceNotFoundException {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found for this id :: " + agencyId));

        Client newClient = this.clientRepository.save(client);
        Account newAccount = createAccount(client, agency);
        return this.clientRepository.save(client);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Long clientId,
                                               @Validated @RequestBody Client clientDetails) throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        client.setName(clientDetails.getName());
        client.setEmail(clientDetails.getEmail());
        final Client updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/clients/{id}")
    public Map<String, Boolean> deleteClient(@PathVariable(value = "id") Long clientId)
            throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private Account createAccount(Client client, Agency agency){
        Account account = new Account();
        account.setBalance(0.0);
        account.setClient(client);
        account.setAgency(agency);

        return this.accountRepository.save(account);
    }

}
