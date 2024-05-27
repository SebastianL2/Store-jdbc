# Java JDBC Project

## Overview

A Java 18+ application using JDBC to connect to a MySQL database.

## Requirements

- Java 18+
- Maven
- MySQL server

## MySQL Setup

1. Create a database named `tienda`.
2. Use the following connection parameters:
   ```java
   String url = "jdbc:mysql://localhost:3306/tienda";
   String username = "root";
   String password = "";