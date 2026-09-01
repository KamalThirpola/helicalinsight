# MongoDB connectivity

This change adds MongoDB JDBC support to the Helical Insight datasource connection pipeline.

## What changed

- Added the official `org.mongodb:mongodb-jdbc` dependency to `server/core`.
- Updated `MongoConnectionFactory` so it delegates to the existing `DatabaseConnectionFactory` instead of returning a `DriverConnection` with a `null` connection.
- Resource-backed and temporary datasource handling continue to use the existing Helical Insight connection flow.

## Datasource configuration

Configure the datasource with the MongoDB JDBC driver class:

`com.mongodb.jdbc.MongoDriver`

Use a MongoDB SQL Interface JDBC connection string beginning with the `jdbc:` prefix. Example:

`jdbc:mongodb://[username:password]@[host].a.query.mongodb.net/<databaseName>`

For the MongoDB JDBC driver, the target database may also need to be supplied as a connection property, for example:

```java
Properties properties = new Properties();
properties.setProperty("database", "<databaseName>");
Connection connection = DriverManager.getConnection("<connectionString>", properties);
```

Authentication and TLS options should be supplied through the connection string or datasource configuration supported by the target MongoDB SQL deployment. Special characters in connection strings must be URL encoded.

The connection must be configured through Helical Insight's existing datasource mechanism, using the configured connection provider and JDBC driver class in the same way as other JDBC-backed datasources.

## Validation

1. Refresh Maven dependencies.
2. Configure a MongoDB SQL Interface JDBC datasource using `com.mongodb.jdbc.MongoDriver`.
3. Verify that the connection string begins with `jdbc:mongodb://`.
4. Test the datasource connection from Helical Insight.
5. Run the relevant Maven module build and tests with a JDK supported by the project's Maven compiler configuration.

## Important

MongoDB's JDBC driver is intended for the MongoDB SQL Interface and compatible deployments. A regular MongoDB Java driver connection is not interchangeable with `java.sql.Connection`, which is why this integration uses the JDBC driver in Helical Insight's existing JDBC-based datasource pipeline.
