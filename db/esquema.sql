CREATE TABLE usuarios (
    login VARCHAR(50) PRIMARY KEY,
    senha_hash VARCHAR(40) NOT NULL,
    admin BOOLEAN DEFAULT FALSE,
    numero INT DEFAULT 0,
    personal_text TEXT
);

-- login: admin, senha: admin
INSERT INTO usuarios (login, senha_hash, admin) VALUES
('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1);
