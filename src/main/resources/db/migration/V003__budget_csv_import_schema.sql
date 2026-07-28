-- V003__budget_csv_import_schema.sql

CREATE SEQUENCE budget_category_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE budget_category (
    id BIGINT DEFAULT nextval('budget_category_seq') PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE SEQUENCE budget_transaction_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE budget_transaction (
    id BIGINT DEFAULT nextval('budget_transaction_seq') PRIMARY KEY,
    tx_date DATE NOT NULL,
    tx_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_budget_tx_category FOREIGN KEY (category_id) REFERENCES budget_category(id),
    CONSTRAINT fk_budget_tx_user FOREIGN KEY (user_id) REFERENCES app_user(id),
    CONSTRAINT uk_budget_tx UNIQUE (tx_date, user_id, category_id, amount, description)
);

CREATE TABLE import_metadata (
    id INT PRIMARY KEY,
    last_filename VARCHAR(255) NOT NULL,
    upload_timestamp TIMESTAMP NOT NULL
);
