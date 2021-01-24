# Banco-API
API para gerenciamento de contas - Desenvolvida em java com Spring boot, JPA e PostgreSQL.

## Funcionalidades
### Banco
:small_blue_diamond: Inserção de dados através da URL - http://localhost:8080/banks/

Ex: 
```
{
	"compensationCode": "1",
	"name":"Banco do Brasil S.A."
}
```

:small_blue_diamond: Listagem de todos os bancos através da URL - http://localhost:8080/banks/

:small_blue_diamond: Consulta de banco por id através da URL - http://localhost:8080/banks/:id

:small_blue_diamond: Edição de dados através da URL - http://localhost:8080/banks/:id

:small_blue_diamond: Exclusão de dados através da URL - http://localhost:8080/banks/:id

### Agência

:small_blue_diamond: Inserção de dados através da URL - http://localhost:8080/banks/:bankId/agencies/

Ex: 
```
{
	"agencyCode": "01"
}
```

:small_blue_diamond: Listagem de todas as agencias por banco através da URL - http://localhost:8080/banks/:bankId/agencies/

:small_blue_diamond: Exclusão de dados através da URL - http://localhost:8080/banks/:bankId/agencies/:agencyId

### Cliente
:small_blue_diamond: Criação de usuário e conta simultânea através da URL - http://localhost:8080/accounts/clients/:agencyId

Ex: 
```
{
	"name":"leo",
	"email": "leo@teste.com",
	"password": "123"
}
```

:small_blue_diamond: Listagem de todos os clientes através da URL - http://localhost:8080/clients/

:small_blue_diamond: Consulta de cliente por id através da URL - http://localhost:8080/clients/:clientId

:small_blue_diamond: Exclusão de dados através da URL - http://localhost:8080/clients/:clientId

### Conta

:small_blue_diamond: Realizar um depósito através da URL - http://localhost:8080/accounts/deposit/:accountId

Ex: 
```
{
	"value": 1000
}
```

:small_blue_diamond: Realizar um saque através da URL - http://localhost:8080/accounts/withdraw/:accountId

Ex: 
```
{
	"value": 500
}
```

:small_blue_diamond: Realizar uma transferencia através da URL - http://localhost:8080/accounts/transfer/:accountAId/:accountBId

Ex: 
```
{
	"value": 500
}
```

:small_blue_diamond: Listagem de todas as contas através da URL - http://localhost:8080/accounts/

:small_blue_diamond: Extrato bancário através da URL - http://localhost:8080/accounts/BankStament/:accountId

:small_blue_diamond: Consultar saldo através da URL - http://localhost:8080/accounts/balance/:accountId


## Pré-requisitos:
Banco de dados já criado no PostgreSQL

## Instalação
Após clonar o repositório, acessar a pasta *Banco-API/src/main/resources* e abrir o arquivo ***application.properties***, preencher as variáveis conforme os dados do seu banco:

### Ex:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/banco-db
spring.datasource.username=postgres
spring.datasource.password=postgres
```
Você substituirá o **banco-db** pelo nome do seu banco de dados.
E também o **username** e o **password** que você usa para acessar o seu servidor do banco.

Após isso, já é possível fazer o build do projeto.


## Banco de dados
O banco deve possuir 5 tabelas com as seguintes colunas.


### banks

id | codigo_compensacao  | name
---|---------------------|------
Bigint|String|String

### agencies

id | agency_code  | bank_id
---|--------------|--------
Bigint|String|Bigint

### clients

id | name  | email | password
---|-------|-------|---------
Bigint|String|String|String

### accounts

id | balance  | agency_id | client_id
---|----------|-----------|----------
Bigint|Double|Bigint|Bigint


### bank_statement

id | after_balance  | before_balance | operation | operation_date | account_id
---|----------------|----------------|-----------|----------------|-----------
Bigint|Double|Double|String|Date|Bigint
