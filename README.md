# Spring-Data-Connection-Per-User
Youtube Video - Application with no predefined DB connection. Users choose a Database to connect instead.

Watch the video [here](https://www.youtube.com/watch?v=mcHoaPIiL6w).

# Project Overview
Simple TODO app but with the difference that the back end does not have configured database connection. Instead, users have to provide credentials to their database and then they can choose which schema they want to use or even create one right from the app.

Some of the features in the project include:

 - Custom Session Management
 - Ability to connect to MySQL or Microsoft SQL Server
 - Ability to validate whether a database is compatible with the app (using Flyway to attempt to migrate the SQL migrations we can compare the checksum of the files and guarantee DB compatibility.
 - Ability to create databases
 - Ability for Spring Data to choose which connection to use based on the current session.
 - Spring Security integrated with the Custom Session Management

The tricky part really was to make spring distinguish which connection is from MySQL and which is from MS SQL Server. It would execute a query to MySQL with MS SQL Server dialect which would cause a syntax error, which I managed to fix by creating custom bean for the EntityManagerFactory. I created a proxy of that interface and a concrete instance for each Database provider (in that case just two) and used the data in the session to determine which instance of the EMF to use at runtime.
