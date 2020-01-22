# i-spy-api

Java Spring Boot Backend With A ReactJS Frontend

#### Backend Environment Variables

ADMIN_PASSWORD - set your own  
ADMIN_USERNAME - set your own  
SIGNER - set your own Used to encode and decode authentication (jwt) tokens.

DATABASE_URL  
e.g. `postgres://[user]:[password]@localhost:[port]/[database]`

replace with postgres credentials   
    `[user]` username  
    `[password]` password  
    `[port]` port number  
    `[database]` database name  

#### Frontend Environment Variables
`set "REACT_APP_API_URL=http://localhost:8080" && npm start`

#### Running Postgres from Docker
`docker pull postgres`  
`docker run -p 5431:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=1234 -d postgres`