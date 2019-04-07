package by.neon.travelassistant;

import org.junit.Assert;
import org.junit.Test;

import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Country;

public class SqliteTest {
    @Test
    public void sqlite_InsertCountry_Success() {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        Country country = new Country();
        country.setCountryCode("BY");
        country.setCountryName("Belarus");
        country.setId(1);
        long id = dbContext.countryDao().insert(country);
        Assert.assertEquals(country.getId(), id);
    }
}
