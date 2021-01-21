package com.example.bankProject.model;

import javax.persistence.*;

@Entity
@Table(name="banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name="compensation_code")
    private String compensationCode;

    public Bank() {
    }

    public Bank(long id, String name, String compensationCode) {
        this.id = id;
        this.name = name;
        this.compensationCode = compensationCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompensationCode() {
        return compensationCode;
    }

    public void setCompensationCode(String compensationCode) {
        this.compensationCode = compensationCode;
    }
}
