# Helical Insight Round 2 Assessment Submission

## Task

Add MongoDB driver/connectivity support to the existing Helical Insight application.

## Implementation summary

### MongoDB JDBC driver

The `server/core` Maven module includes the MongoDB JDBC dependency required by Helical Insight's existing `java.sql.Connection` based datasource pipeline.

### Connection factory

`MongoConnectionFactory` extends `DatabaseConnectionFactory` and delegates connection creation to the existing datasource implementation. This avoids maintaining a separate MongoDB connection path and allows temporary and resource-backed datasource handling to follow the same application flow.

### Configuration

A MongoDB datasource is configured through the existing Helical Insight datasource mechanism with:

- Driver class: `com.mongodb.jdbc.MongoDriver`
- A valid MongoDB SQL Interface JDBC URL beginning with `jdbc:mongodb://`
- Deployment-specific authentication, TLS, and database settings

### Validation

`MongoJdbcDriverAvailabilityTest` verifies that the configured MongoDB JDBC driver can be loaded and implements `java.sql.Driver`.

A live connection test still requires access to a valid MongoDB SQL-compatible deployment and valid credentials. The repository documentation includes the manual steps for that environment-dependent verification.

## Files changed for the assessment

- `server/core/pom.xml`
- `server/core/src/main/java/com/helicalinsight/datasource/MongoConnectionFactory.java`
- `server/core/src/test/java/com/helicalinsight/datasource/MongoJdbcDriverAvailabilityTest.java`
- `docs/MONGODB_CONNECTIVITY.md`

## Scope control

The assessment changes are limited to MongoDB connectivity, validation, and documentation. An assessment-specific Maven Java-version override was removed so the repository keeps its original JDK configuration.

## Submission note

The source code is ready for review. Before final runtime sign-off, run the relevant Maven tests using the JDK required by the repository and perform the datasource connection test against a valid MongoDB SQL-compatible environment.