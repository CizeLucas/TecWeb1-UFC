CREATE TABLE usuarios (
    login VARCHAR(50) PRIMARY KEY,
    senha_hash VARCHAR(40) NOT NULL
);

-- login: admin, senha: admin
INSERT INTO usuarios (login, senha) VALUES
('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997');
