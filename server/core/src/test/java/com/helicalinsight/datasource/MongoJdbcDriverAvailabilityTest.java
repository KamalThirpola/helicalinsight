package com.helicalinsight.datasource;

import org.junit.Test;

import java.sql.Driver;

import static org.junit.Assert.assertTrue;

/**
 * Smoke test for the MongoDB JDBC dependency used by the datasource pipeline.
 * This test does not require a running MongoDB deployment; it verifies that
 * the configured JDBC driver is available on the module classpath.
 */
public class MongoJdbcDriverAvailabilityTest {

    @Test
    public void mongoJdbcDriverIsAvailable() throws Exception {
        Class<?> driverClass = Class.forName("com.mongodb.jdbc.MongoDriver");
        assertTrue(Driver.class.isAssignableFrom(driverClass));
    }
}
