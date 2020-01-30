# CSYE 6225 - Spring 2020
# webapp
Building a Bill Tracking application. It will allow users to create, update, delete, and read bills.

## Technology Stack
- Java SpringBoot for REST API
- IntelliJ
- MariaDB - MySQL for Database
- Postman to test REST endpoints
- Circle CI for continous Integration

## Build Instructions
1. Clone repository
2. Import maven project **restapi** in the **webapp** directory into eclipse
3. Right Click restapi > Maven > Update Maven Project > OK

## Deploy Instructions

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
   
## CI/CD
- Currenlt using Cirle CI tools to run pr_check in config.yml on each PR raised from fork master branch to orag master and build_deploy on every merge to master.
