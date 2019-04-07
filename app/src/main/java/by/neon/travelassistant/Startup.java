package by.neon.travelassistant;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.os.Build;

import by.neon.travelassistant.config.sqlite.DbConstants;
import by.neon.travelassistant.config.sqlite.TravelDbContext;

/**
 * Main entry point of this application. Every class of this application runs after Startup.
 */
public class Startup extends Application {
    /**
     * Static instance of {@link Startup}
     */
    private static Startup startup;
    /**
     * Database context singleton
     */
    private TravelDbContext dbContext;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     *
     * <p>Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.</p>
     *
     * <p>If you override this method, be sure to call {@code super.onCreate()}.</p>
     *
     * <p class="note">Be aware that direct boot may also affect callback order on
     * Android {@link Build.VERSION_CODES#N} and later devices.
     * Until the user unlocks the device, only direct boot aware components are
     * allowed to run. You should consider that all direct boot unaware
     * components, including such {@link ContentProvider}, are
     * disabled until user unlock happens, especially when component callback
     * order matters.</p>
     */
    @Override
    public void onCreate() {
        super.onCreate();
        startup = this;
        dbContext = Room.databaseBuilder(this, TravelDbContext.class, DbConstants.DATABASE).build();
    }

    /**
     * Gets the startup class of this application
     *
     * @return {@link Startup}
     */
    public static Startup getStartup() {
        return startup;
    }

    /**
     * Gets the SQLite database context
     *
     * @return {@link TravelDbContext}
     */
    public TravelDbContext getDbContext() {
        return dbContext;
    }
}
