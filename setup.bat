@echo off
REM Cria o banco de dados db/dados.db usando o esquema db/esquema.sql

IF NOT EXIST backend\db (
    mkdir backend\db
)

sqlite3 backend\db\dados.db < backend\db\esquema.sql

echo Banco de dados criado com sucesso em backend\db\dados.db
pause