CREATE TABLE IF NOT EXISTS users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       cpf VARCHAR(11) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       type VARCHAR(30) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
                              id SERIAL PRIMARY KEY,
                              user_id_from INT NOT NULL,
                              user_id_to INT NOT NULL,
                              amount NUMERIC(10,2) NOT NULL CHECK (amount > 0),
                              description TEXT,
                              status VARCHAR(30) DEFAULT 'PENDING',
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (user_id_from) REFERENCES users(id) ON DELETE CASCADE,
                              FOREIGN KEY (user_id_to) REFERENCES users(id) ON DELETE CASCADE
);
