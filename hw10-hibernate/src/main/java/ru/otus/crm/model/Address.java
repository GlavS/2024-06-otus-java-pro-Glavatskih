package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @OneToOne
    private Client client;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }
}
