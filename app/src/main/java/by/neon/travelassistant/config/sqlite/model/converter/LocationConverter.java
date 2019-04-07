package by.neon.travelassistant.config.sqlite.model.converter;

import android.arch.persistence.room.TypeConverter;
import android.location.Location;
import android.location.LocationManager;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

public class LocationConverter {
    @TypeConverter
    public String toWktString(Location location) {
        CoordinateArraySequence sequence = new CoordinateArraySequence(new Coordinate[] {new Coordinate(location.getLatitude(), location.getLongitude())});
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel());
        Point point = new Point(sequence, geometryFactory);
        WKTWriter writer = new WKTWriter();
        return writer.write(point);
    }

    @TypeConverter
    public Location fromWktString(String wkt) {
        WKTReader reader = new WKTReader();
        Point point = null;
        try {
            point = (Point) reader.read(wkt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Location location = new Location(LocationManager.GPS_PROVIDER);
        assert point != null;
        location.setLatitude(point.getX());
        location.setLongitude(point.getY());
        return location;
    }
}
