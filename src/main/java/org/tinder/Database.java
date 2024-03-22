package org.tinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Database {

  public static Connection mkConn() throws SQLException {
    String jdbcDatabaseUrl = System.getenv("JDBC_DATABASE_URL");
    if(Objects.isNull(jdbcDatabaseUrl) ) {
      return DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/tinderDB",
              "postgres",
              "pg123456"
      );
    }
    return DriverManager.getConnection(jdbcDatabaseUrl);
  }

}
