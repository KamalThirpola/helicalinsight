# MongoDB connectivity

This change adds MongoDB JDBC support to the Helical Insight datasource connection pipeline.

## What changed

- Added the official `org.mongodb:mongodb-jdbc` dependency to `server/core`.
- Updated `MongoConnectionFactory` so it delegates to the existing `DatabaseConnectionFactory` instead of returning a `DriverConnection` with a `null` connection.
- Resource-backed and temporary datasource handling continue to use the existing Helical Insight connection flow.

## Datasource configuration

Configure the datasource with the MongoDB JDBC driver class:

`mongodb.jdbc.MongoDriver`

Use the MongoDB JDBC connection string format, which begins with the `jdbc:` prefix. Example:

`jdbc:mongodb://<host-or-cluster>/<database>`

Authentication and TLS options should be supplied through the connection string or datasource configuration supported by the target MongoDB SQL deployment.

The connection must be configured in the same datasource mechanism used by the existing Helical Insight JDBC drivers, with `MongoConnectionFactory` selected as the connection provider where the application configuration expects a provider class.

## Build note

The project must be built with a JDK version supported by the project's Maven compiler configuration. If Maven reports an `invalid target release` error, use the JDK configured for the project or adjust the compiler target before validating the full build.

## Validation

1. Refresh Maven dependencies.
2. Configure a MongoDB JDBC datasource.
3. Test the datasource connection from Helical Insight.
4. Run the relevant Maven module build and tests.

## Important

MongoDB's JDBC driver is intended for the MongoDB SQL interface and compatible deployments. A regular MongoDB Java driver connection is not interchangeable with `java.sql.Connection`, which is why this integration uses the JDBC driver in Helical Insight's existing JDBC-based datasource pipeline.
