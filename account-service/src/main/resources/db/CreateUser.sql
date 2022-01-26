create schema if not exists ad;
create sequence if not exists ad.user_id_seq;
create sequence if not exists ad.address_id_seq;

create table if not exists ad.address (
                                          id integer unique not null default nextval('ad.address_id_seq'),
                                          country varchar,
                                          city varchar,
                                          street varchar,
                                          home varchar,
                                          PRIMARY KEY (id)
);

create table if not exists ad.users (
    id integer unique not null default nextval('ad.user_id_seq'),
    name varchar not null,
    email varchar,
    address integer,
    PRIMARY KEY (id),
    FOREIGN KEY (address) REFERENCES ad.address(id)
);

create schema if not exists dict;
create sequence if not exists dict.currency_id_seq;
create table if not exists dict.currency(
    id integer unique not null default nextval('dict.currency_id_seq'),
    name varchar not null,
    PRIMARY KEY (id)
);

insert into dict.currency (name)
values ('USD'), ('EUR'), ('RUB'), ('GBP');

create schema if not exists bank;
create sequence if not exists bank.bank_book_id_seq;
create table if not exists bank.bank_book (
    id integer unique not null default nextval('bank.bank_book_id_seq'),
    user_id integer not null,
    number varchar not null ,
    amount numeric not null ,
    currency integer not null ,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES ad.users(id),
    FOREIGN KEY (currency) REFERENCES dict.currency(id)
);

create sequence if not exists ad.group_id_seq;
create table if not exists ad.group (
    id integer unique not null default nextval('ad.group_id_seq'),
    name varchar not null,
    PRIMARY KEY (id)
);

create table if not exists ad.users_groups (
    user_id integer not null,
    group_id integer not null,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES ad.users(id),
    FOREIGN KEY (group_id) REFERENCES ad.group(id)
);