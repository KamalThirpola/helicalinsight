# MongoDB Driver Integration - Assessment Round 2

## Objective

This assessment adds MongoDB connectivity to the existing Helical Insight application without changing the existing relational database connection flow.

## Existing integration points

Helical Insight already contains a `MongoConnectionFactory` under:

`server/core/src/main/java/com/helicalinsight/datasource/MongoConnectionFactory.java`

The factory recognizes the MongoDB JDBC driver and routes the connection through the application's datasource workflow.

## MongoDB connection configuration

Create or edit a datasource connection using the following values when using the MongoDB JDBC driver supported by the deployment:

- Driver class: `mongodb.jdbc.MongoDriver`
- Connection URL: `jdbc:mongodb://<host>:<port>/<database>`
- Default MongoDB port: `27017`
- Username: optional, depending on MongoDB authentication
- Password: optional, depending on MongoDB authentication

Example:

`jdbc:mongodb://localhost:27017/sampledb`

## Important driver requirement

The Java dependency `org.mongodb:mongo-java-driver` is the native MongoDB Java driver. It does not provide the JDBC class `mongodb.jdbc.MongoDriver`.

Therefore, a MongoDB JDBC bridge JAR that actually provides `mongodb.jdbc.MongoDriver` must be available to the Helical Insight driver/plugin loader when this driver class is selected. The application already has a configurable driver directory at:

`server/hi-repository/System/Drivers`

After adding the JDBC bridge JAR to the configured driver location, use the application's driver refresh/load flow and then configure the MongoDB connection.

## Verification checklist

1. Start MongoDB.
2. Make the MongoDB JDBC bridge JAR available to the Helical Insight driver loader.
3. Refresh or load the driver from the Helical Insight datasource driver management flow.
4. Create a MongoDB datasource using `mongodb.jdbc.MongoDriver`.
5. Test the connection.
6. Verify that existing MySQL, PostgreSQL and other datasource configurations still work.

## Assessment scope completed in codebase review

The review confirmed the relevant integration points:

- MongoDB URL patterns are present in `databaseDrivers.properties`.
- `MongoConnectionFactory` is present in the datasource module.
- Workflow metadata configuration references `MongoConnectionFactory`.
- The server Maven configuration includes the native MongoDB Java driver.

## Remaining environment-dependent validation

A final end-to-end confirmation requires a real MongoDB instance and the JDBC bridge JAR that exposes `mongodb.jdbc.MongoDriver`. These are runtime environment requirements and are not stored in this repository.
