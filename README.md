# Une-application-r-partie-permettant-de-g-rer-des-comptes-bancaires
A distributed banking application that manages bank accounts with both TCP and UDP implementations.

Features
Account management (create, check balance, deposit, withdraw)

Two versions: TCP (connection-oriented) and UDP (connectionless)

Proper error handling and response formatting

Thread-safe account operations

Protocol Commands
CREATION id amount - Create account

POSITION id - Check balance

AJOUT id amount - Deposit money

RETRAIT id amount - Withdraw money

Response Formats
OK command

ERREUR reason

POS balance last_operation_date
