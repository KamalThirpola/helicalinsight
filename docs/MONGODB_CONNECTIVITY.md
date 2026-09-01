# MongoDB connectivity

This change adds MongoDB JDBC support to the Helical Insight datasource connection pipeline.

## Assessment requirements covered

- MongoDB database driver/connectivity support is added to the application.
- MongoDB uses the existing JDBC datasource connection pipeline rather than a separate parallel implementation.
- MongoDB can be configured using Helical Insight's existing datasource connection mechanism.
- Temporary and resource-backed datasource handling is preserved.
- Configuration and validation steps are documented below.

## What changed

- Added the `org.mongodb:mongodb-jdbc` dependency to `server/core`.
- Updated `MongoConnectionFactory` so it delegates to the existing `DatabaseConnectionFactory` instead of returning a `DriverConnection` with a `null` connection.
- Added a smoke test that verifies the MongoDB JDBC driver is available and implements `java.sql.Driver`.
- Removed an assessment-specific Java 17 Maven override so the repository continues to use the JDK version defined by its parent Maven configuration.

## Datasource configuration

Configure a datasource using the MongoDB JDBC driver class:

`com.mongodb.jdbc.MongoDriver`

Use a MongoDB SQL Interface JDBC connection string beginning with the `jdbc:` prefix. Example:

`jdbc:mongodb://[username:password]@[host].a.query.mongodb.net/<databaseName>`

The exact URL, authentication, TLS, and database properties depend on the MongoDB SQL deployment being used. Supply credentials and TLS options through the connection string or datasource configuration supported by that deployment. Special characters in connection strings must be URL encoded.

The connection must be configured through Helical Insight's existing datasource mechanism, using the configured JDBC URL, username, password, and driver class in the same way as other JDBC-backed datasources.

## Validation performed in code

The test `MongoJdbcDriverAvailabilityTest` verifies that:

1. `com.mongodb.jdbc.MongoDriver` is present on the `hi-core` test classpath.
2. The loaded class implements `java.sql.Driver`.

This test does not require a live MongoDB deployment and therefore does not prove network connectivity to a database.

## Manual validation before submission

Use a JDK supported by the repository's parent Maven configuration, then:

1. Refresh Maven dependencies.
2. Run the `hi-core` module test containing `MongoJdbcDriverAvailabilityTest`.
3. Configure a MongoDB SQL Interface JDBC datasource with driver class `com.mongodb.jdbc.MongoDriver`.
4. Use a valid `jdbc:mongodb://...` connection string for the target deployment.
5. Test the datasource connection from the Helical Insight application.
6. Verify that an existing JDBC datasource still connects successfully.

## Important

MongoDB's JDBC driver is intended for MongoDB SQL-compatible deployments. A regular MongoDB Java driver connection is not interchangeable with `java.sql.Connection`, which is why this implementation uses the JDBC driver in Helical Insight's existing JDBC-based datasource pipeline.

## Submission summary

The implementation changes are intentionally limited to MongoDB connectivity and its supporting documentation/test. No unrelated application behavior is modified by this assessment change.