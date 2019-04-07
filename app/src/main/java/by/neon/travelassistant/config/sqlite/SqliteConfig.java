package by.neon.travelassistant.config.sqlite;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.Config;

public final class SqliteConfig extends Config {
    private final TravelDbContext context;

    public SqliteConfig() {
        this.context = Startup.getStartup().getDbContext();
    }
}
