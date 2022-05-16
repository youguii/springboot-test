# User API
A simple API that allows to register a user, get all registered users, in addition to other CRUD operations, it includes an embedded tomcat server and h2 database.
## Packages Description
```
├── advice
├── aspect
├── controller
├── exception
├── model
├── repository
└── service
```

* advice : handle exceptions/errors
* aspect : handle code logging
* SpringbootTestApplication.java : java class used to launch the backend


There are 5 entrypoints in our Backend, with different usage:

| Entrypoint      | Usage                        | Type   |
|-----------------|------------------------------|--------|
| /api/users      | get all the registered users | GET    | 
| /api/users{id}  | get a user by its id         | GET    |
| /api/users      | create a new user            | POST   | 
| /api/users/{id} | update a user information    | UPDATE | 
| /api/users/{id} | delete a user                | DELETE | 



### Deployment from code (Generating jar file/execution)
0. Run maven command : mvn clean install
1. A jar file will be generated inside target folder 
2. Run the jar using Java command : java -jar "name of the jar file"
3. The application will run on http://localhost:8080/
