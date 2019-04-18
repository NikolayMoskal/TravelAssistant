package by.neon.travelassistant.config.sqlite.mapper;

import by.neon.travelassistant.config.sqlite.model.AirportDb;
import by.neon.travelassistant.model.Airport;

public final class AirportMapper extends BaseMapper<Airport, AirportDb> {
    @Override
    public AirportDb from(Airport source) {
        LocationMapper mapper = new LocationMapper();
        CityMapper cityMapper = new CityMapper();

        AirportDb airportDb = new AirportDb();
        airportDb.setId(source.getId());
        airportDb.setName(source.getAirportName());
        airportDb.setLocation(mapper.from(source.getLocation()));
        airportDb.setIcaoCode(source.getIcaoCode());
        airportDb.setIataCode(source.getIataCode());
        airportDb.setCityId(source.getCityId());
        airportDb.setCityDb(cityMapper.from(source.getCity()));
        return airportDb;
    }

    @Override
    public Airport to(AirportDb source) {
        LocationMapper mapper = new LocationMapper();
        CityMapper cityMapper = new CityMapper();

        Airport airport = new Airport();
        airport.setId(source.getId());
        airport.setAirportName(source.getName());
        airport.setLocation(mapper.to(source.getLocation()));
        airport.setCity(cityMapper.to(source.getCityDb()));
        airport.setCityId(source.getCityId());
        airport.setIataCode(source.getIataCode());
        airport.setIcaoCode(source.getIcaoCode());
        return airport;
    }
}
