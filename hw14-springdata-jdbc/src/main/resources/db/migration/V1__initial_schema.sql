create table client
(
    id   bigint not null primary key,
    name varchar(50)
);

create sequence client_id_seq start with 1 increment by 1 owned by client.id;


create table address
(
    id        bigint generated by default as identity,
    street    varchar(255),
    client_id bigint unique,
    primary key (id)
);

create table phone
(
    id        bigint generated by default as identity,
    number    varchar(255),
    client_id bigint,
    primary key (id)
);

alter table if exists address
    add constraint clientIdFKinAddress
        foreign key (client_id)
            references client;

alter table if exists phone
    add constraint clientIdFKinPhones
        foreign key (client_id)
            references client;
