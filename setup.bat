@echo off
REM Cria o banco de dados db/dados.db usando o esquema db/esquema.sql

IF NOT EXIST db (
    mkdir db
)

sqlite3 db\dados.db < db\esquema.sql

echo Banco de dados criado com sucesso em db\dados.db
pause