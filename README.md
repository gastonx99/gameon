# README

## Mock-server

```
mvn mockserver:run -f gameon-web/
mvn mockserver:stop -f gameon-web/
mvn mockserver:runForked -f gameon-web/
mvn mockserver:stopForked -f gameon-web/
```

Mockserver dashboard: http://localhost:9080/mockserver/dashboard

## Jetty

```
mvn jetty:run -f gameon-web/
```

## Database

### MySQL

```
mvn -f gameon-datamodel liquibase:update -Pmysql
```

#### Useful commands

```
select user from mysql.user;
create user 'gameon'@'localhost' identified by 'gameon';
show databases;
create database gameon;
use gameon;
grant all on gameon.* to 'gameon'@'localhost';
show tables;
describe fixture;
drop database gameon;
```

#### SQL Developer

- Add 3rd party driver %$USERPROFILE%\.m2\repository\mysql\mysql-connector-java\8.0.23\mysql-connector-java-8.0.23.jar

### Hsqldb

```
export CLASSPATH=$USERPROFILE/.m2/repository/org/hsqldb/hsqldb/2.5.0/hsqldb-2.5.0.jar
mvn -f gameon-datamodel liquibase:update -Phsqldb
java org.hsqldb.server.Server --database.0 file:D:/database/hsqldb/gameon/gameon --dbname.0 gameon-xdb export
```

### Postgres

```
mvn -f gameon-datamodel liquibase:update -Ppostgres
```

## Initialize empty database

- Start mock-server and run Api1MockServerExpectionsTestRunner.mockAll
- Run FetchDataFromApi1CliRunner.all

## Links

https://medium.com/@jasim/declarative-router-with-web-components-43ddcebc9dbc

[Mock-server dashboard]: http://localhost:9080/mockserver/dashboard