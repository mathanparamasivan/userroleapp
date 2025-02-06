@echo off

@echo off
rem 1. Set the VAULT_ADDR environment variable (for Vault's server)
set VAULT_ADDR=http://127.0.0.1:8200

rem 2. Start Vault (Make sure Vault binary is in your PATH or use full path to vault.exe)
echo Starting Vault...
start /B vault server -dev

rem 3. Wait for Vault to start (give it some time to initialize)
timeout /t 10

rem 4. Set the VAULT_TOKEN (This is default for dev mode; change if using a different setup)
set VAULT_TOKEN=root

rem 5. Enable the MySQL secrets engine
echo Enabling MySQL Vault secrets engine...
vault secrets enable database

rem 6. Configure MySQL connection (replace with your MySQL connection details)
echo Configuring MySQL connection...
vault write database/config/mysql_vault \
    plugin_name=mysql-database-plugin \
    connection_url="root:password@tcp(127.0.0.1:3306)/" \
    allowed_roles="mysql_role" \
    username="root" \
    password="password"

rem 7. Create a secret with username and password
echo Creating a MySQL secret with username and password...
vault kv put secret/mysql_vault username="myuser" password="mypassword"

rem 8. Display the created secret
echo Retrieving the secret stored in Vault...
vault kv get secret/mysql_vault

echo Vault setup complete.
pause
