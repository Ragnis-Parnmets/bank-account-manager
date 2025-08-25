-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-08-13 06:19:35.491

-- tables
-- Table: account
CREATE TABLE account (
    id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
    account_number char(20)  NOT NULL,
    account_holder_id int  NOT NULL,
    account_type_id int  NOT NULL,
    balance decimal(15,2)  NOT NULL,
    created_at timestamp  NOT NULL,
    status varchar(20) DEFAULT 'ACTIVE' NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY (id)
);

-- Table: account_holder
CREATE TABLE account_holder (
    id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
    first_name varchar(100)  NOT NULL,
    last_name varchar(100)  NOT NULL,
    created_at timestamp  NOT NULL,
    status varchar(20) DEFAULT 'ACTIVE' NOT NULL,
    CONSTRAINT account_holder_pk PRIMARY KEY (id)
);

-- Table: account_type
CREATE TABLE account_type (
    id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
    type_name varchar(100)  NOT NULL,
    description varchar(255)  NOT NULL,
    status varchar(20) DEFAULT 'ACTIVE' NOT NULL,
    CONSTRAINT account_type_pk PRIMARY KEY (id)
);

-- Table: transfer
CREATE TABLE transfer (
    id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
    from_account char(20) NOT NULL,
    to_account char(20) NOT NULL,
    amount decimal(15,2) NOT NULL,
    description varchar(255) NOT NULL,
    created_at timestamp NOT NULL,
    CONSTRAINT transfer_pk PRIMARY KEY (id)
);

-- Ensure uniqueness of the account number
ALTER TABLE account ADD CONSTRAINT uk_account_number UNIQUE (account_number);

-- foreign keys
-- Reference: account_account_holder (table: account)
ALTER TABLE account ADD CONSTRAINT account_account_holder
    FOREIGN KEY (account_holder_id)
    REFERENCES account_holder (id);

-- Reference: account_account_type (table: account)
ALTER TABLE account ADD CONSTRAINT account_account_type
    FOREIGN KEY (account_type_id)
    REFERENCES account_type (id);

-- End of a file.
