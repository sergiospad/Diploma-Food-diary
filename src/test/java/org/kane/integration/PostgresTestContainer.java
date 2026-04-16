package org.kane.integration;

import org.testcontainers.containers.PostgreSQLContainer;

public final class PostgresTestContainer {
    private static final PostgreSQLContainer<?> INSTANCE = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    static {
        INSTANCE.start();
    }

    private PostgresTestContainer() {
    }

    public static PostgreSQLContainer<?> getInstance() {
        return INSTANCE;
    }
}
