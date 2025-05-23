echo "Criando banco local..."
createdb meu_banco_local
psql -d meu_banco_local -f db/schema.sql
echo "Banco criado com sucesso!"
