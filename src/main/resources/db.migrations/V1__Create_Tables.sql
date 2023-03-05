CREATE TABLE IF NOT EXISTS custom_user(
    id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW(),
    email VARCHAR(150) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,

    CONSTRAINT custom_user_pk PRIMARY KEY(id)

);

CREATE TABLE IF NOT EXISTS user_code(
    id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW(),
    code VARCHAR(10) NOT NULL,
    is_valid BOOLEAN DEFAULT TRUE,
    user_id VARCHAR(36) NOT NULL,

    CONSTRAINT user_code_pk PRIMARY KEY(id),
    CONSTRAINT user_code_fk_custom_user FOREIGN KEY(user_id) REFERENCES custom_user(id)

);

CREATE TABLE IF NOT EXISTS category(
    id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW(),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    image TEXT,
    user_id VARCHAR(36) NOT NULL,

    CONSTRAINT category_pk PRIMARY KEY(id),
    CONSTRAINT category_fk_custom_user FOREIGN KEY(user_id) REFERENCES custom_user(id)

);

CREATE TABLE IF NOT EXISTS bank(
    id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW(),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    image TEXT,
    user_id VARCHAR(36) NOT NULL,

    CONSTRAINT bank_pk PRIMARY KEY(id),
    CONSTRAINT bank_fk_custom_user FOREIGN KEY(user_id) REFERENCES custom_user(id)

);


CREATE TABLE IF NOT EXISTS custom_release(
    id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW(),
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    due_date DATETIME NOT NULL DEFAULT NOW(),
    status_release VARCHAR(15) NOT NULL CHECK(status_release = 'PENDING' OR status_release = 'PAID'),
    type_release VARCHAR(15) NOT NULL CHECK(type_release = 'RECEP' OR type_release = 'EXPENSE'),
    value TEXT,

    category_id VARCHAR(36) NOT NULL,
    bank_id VARCHAR(36),
    user_id VARCHAR(36) NOT NULL,

    CONSTRAINT custom_release_pk PRIMARY KEY(id),
    CONSTRAINT custom_release_fk_category FOREIGN KEY(category_id) REFERENCES category(id),
    CONSTRAINT custom_release_fk_bank FOREIGN KEY(bank_id) REFERENCES bank(id),
    CONSTRAINT custom_release_fk_custom_user FOREIGN KEY(user_id) REFERENCES custom_user(id)

);