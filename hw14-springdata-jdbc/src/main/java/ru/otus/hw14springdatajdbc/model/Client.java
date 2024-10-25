package ru.otus.hw14springdatajdbc.model;

import java.util.Set;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;

@ToString
public class Client implements Persistable<Long> {
    @Id
    private final Long id;

    @Getter
    private final String name;

    @Getter
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @Getter
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @Transient
    private final boolean newEntiy;

    public Client(Long id, String name, Address address, Set<Phone> phones, boolean newEntiy) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.newEntiy = newEntiy;
    }

    @PersistenceCreator
    private Client(Long id, String name, Address address, Set<Phone> phones) {
        this(id, name, address, phones, false);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.newEntiy;
    }
}
