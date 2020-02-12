Apply IAM passwork policy
 student has setup organization
 
 # CSYE 6225 - Spring 2020
# webapp
Building a Bill Tracking application. It will allow users to create, update, delete, attach and read bills.

## Technology Stack
- Java SpringBoot for REST API
- IntelliJ
- MariaDB - MySQL for Database
- Postman to test REST endpoints
- Circle CI for continous Integration

## Build Instructions
1. Clone repository
2. Import maven project in the **webapp** directory into intelliJ
3. Run command mvn clean and mvn install

## Deploy Instructions
1. Create a new database named 'mysql' in MariaDB.
   ```
   create database mysql
   ```
2. Add below mysql user to allow application to connect to the database 
   ```
   grant all privileges on mysql.* to 'username'@'localhost'
   ```
3. Connect to MySQL databse.
4. Add the datasource connection URL in application.yml
5. Add username and password in application.yml to connect to the database.
6. Run IntelliJ project imported in above steps as **SpringBoot Application**

## Test Instructions
1. Run command ```mvn test``` to run all tests

## API Guidelines
### Public endpoints 
1. **Register New user**
   This api registers a new user in the system.
   ```
   POST v1//users {users object}
   ```
### Authenticated
1. **Retreive user from the system**
   This api retreives user details logged in user.
   ```
   GET v1/user/self
   ```
2. **Update user in the system**
   This api updates user details logged in user.
   ```
   PUT v1/user/self
   ```
3. **Create New Bill**
   This api creates new bill for the logged in user.
   ```
   POST v1/bill/ {bills object}
   ```
4. **Get Bills**
   This api gets all bills for the logged in user.
   ```
   GET v1/bills
   ```  
   This api gets a specific bill for the logged in user.
   ```
   GET v1/bill/{id}
   ```  
5. **Delete a Bill**
   This api creates deletes a bill for the logged in user.
   ```
   DELETE v1/bill/{id} 
   ```   
6. **Update a Bill**
   This api updates a bill for the logged in user.
   ```
   PUT v1/bill/{id}
   ```      
7. **Attach a file to Bill**
   This api attaches a new file for a bill for the logged in user.
   ```
   POST /v1/bill/{id}/file
   ```
8. **Get a file attached to a Bill**
   This api gets file for a bill for the logged in user.
   ```
   GET /v1/bill/{billId}/file/{fileId}
   ```
7. **Delete a file attached to a Bill**
   This api deleted file for a bill for the logged in user.
   ```
   DELETE /v1/bill/{billId}/file/{fileId}
   ```
7. **Update a file attached to a Bill**
   This api updates file for a bill for the logged in user.
   ```
   PUT /v1/bill/{billId}/file/
   ```
   
## CI/CD
- Currenlty using Cirle CI tools to run pr_check in config.yml on each PR raised from fork master branch to organization master and build_deploy on every merge to master.
