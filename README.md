# Use Azure KeyVault in Spring Boot Application
This project provides sample on how to use Azure KeyVault as a PropertySource in Spring boot application. 

## Instruction
This sample contains a simple Spring Boot rest web app which read data from MySQL database `moviedb` table `movies`.
MySQL url, username and password were saved as secrets in Azure KeyVault. Azure KeyVault will be added as one of Spring boot PropertySource. When Spring boot application starts, it will read MySQL connection credentials from Azure KeyVault.

### Prequisite
- MySQL server (version >= 5.6).
- Azure CLI is optional if you'd like to use Azure Portal for Azure resource management. Instruction to install [Azure CLI 2.0](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli). 

### Populate MySQL data
Once your MySQL server is up. Populate data as below.
- edit database\pom.xml, set your MySQL server, username and password in url, username and password fields.
- populate data by mvn plugin.
```
cd database
mvn sql:execute
```

### Setup Azure KeyVault
1. Create one azure service principle by Azure CLI or through [Azure Portal](https://docs.microsoft.com/en-us/azure/azure-resource-manager/resource-group-create-service-principal-portal). Save your service principle id and password, for later use.
az cli commands as below.
```bash
az login
az account set --subscription <your_subscription_id>

# create azure service principle by azure cli
az ad app create --display-name your_app_name --identifier-uris http://test.com/test --homepage http://test.com/test
# save the appId from output
az ad sp create --id <app_id_created_from_above_step> 
```
Save the service principle id and password in output.

2. Create Azure KeyVault by Azure CLI or through [Azure Portal](http://www.rahulpnath.com/blog/managing-key-vault-through-azure-portal/). Give permission to service principle created at step 1.
```bash
az keyvault create --name <your_keyvault_name> --resource-group <your_resource_group> --location <location> --enabled-for-deployment true --enabled-for-disk-encryption true --enabled-for-template-deployment true --sku standard
az keyvault set-policy --name <your_keyvault_name> --secret-permission all --object-id <your_sp_id_create_in_step1>
```
Save the KeyVault uri in output for later use.

3. Set MySQL url, username, password as secrets in Azure KeyVault by Azure CLI or through Azure Portal.
```bash
az keyvault secret set --name spring-datasource-url --value jdbc:mysql://<your_mysql_address>:3306/moviedb --vault-name <your_keyvault_name>
az keyvault secret set --name spring-datasource-username --value <your_mysql_username> --vault-name <your_keyvault_name>
az keyvault secret set --name spring-datasource-password --value <your_mysql_password> --vault-name <your_keyvault_name>
```


### Add Azure KeyVault as PropertySource 
- File AzureKeyVaultClient.java, extends [KeyVaultCredentials](https://azure.github.io/azure-sdk-for-java/com/microsoft/azure/keyvault/authentication/KeyVaultCredentials.html), which acquires access token for your service principle.
- File KeyVaultOperation.java, which reads secrets from Azure KeyVault.
- File KeyVaultPropertySource.java, extends Spring [EnumerablePropertySource](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/core/env/EnumerablePropertySource.html). EnumerablePropertySource is Spring framework PropertySource implementation. Implementing your own EnumerablePropertySource, to read properties from Azure KeyVault through KeyVaultOperation class.
- File KeyVaultPropertyIntializer.java, implements Spring ApplicationContextInitializer, to register KeyVaultPropertySource as one of PropertySource.

### Build and Run
- **Configuration**

In application.properties, set Azure service principle id and password, Azure KeyVault url as below.
```properties
# service principle id as client id
azure.clientid=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
# service principle password as client key
azure.clientkey=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
azure.keyvault.uri=https://yourKeyVaultName.vault.azure.net
```


- **Build and Run**

Build the project with maven
```
mvn clean package
```

Run it locally
```
java -jar .\target\azure-keyvault-sample-0.0.1-SNAPSHOT.jar
```

- **Verify**

Verify the application is started locally by accessing rest API http://localhost:8080/api/v1/movies.
