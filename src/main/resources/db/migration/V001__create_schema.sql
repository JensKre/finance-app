-- V001__create_schema.sql

CREATE SEQUENCE app_user_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE app_user (
    id BIGINT DEFAULT nextval('app_user_seq') PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE
);

CREATE SEQUENCE institute_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE institute (
    id BIGINT DEFAULT nextval('institute_seq') PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE SEQUENCE category_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE category (
    id BIGINT DEFAULT nextval('category_seq') PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE SEQUENCE transaction_entry_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE transaction_entry (
    id BIGINT DEFAULT nextval('transaction_entry_seq') PRIMARY KEY,
    user_id BIGINT NOT NULL,
    institute_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    entry_date DATE NOT NULL,
    CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES app_user(id),
    CONSTRAINT fk_transaction_institute FOREIGN KEY (institute_id) REFERENCES institute(id),
    CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Seed initial data
INSERT INTO app_user (username) VALUES ('Jens');
INSERT INTO app_user (username) VALUES ('Annika');

INSERT INTO institute (name) VALUES ('Trade Republic');
INSERT INTO institute (name) VALUES ('Sparkasse');
INSERT INTO institute (name) VALUES ('Binance');

INSERT INTO category (name) VALUES ('Girokonto');
INSERT INTO category (name) VALUES ('Tagesgeldkonto');
INSERT INTO category (name) VALUES ('ETF');
INSERT INTO category (name) VALUES ('Gold');
INSERT INTO category (name) VALUES ('Krypto');
