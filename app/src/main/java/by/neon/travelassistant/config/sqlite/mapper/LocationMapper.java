package by.neon.travelassistant.config.sqlite.mapper;

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

public class LocationMapper extends BaseMapper<Location, String> {
    @Override
    public String from(Location source) {
        CoordinateArraySequence sequence = new CoordinateArraySequence(new Coordinate[] {new Coordinate(source.getLatitude(), source.getLongitude())});
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel());
        Point point = new Point(sequence, geometryFactory);
        WKTWriter writer = new WKTWriter();
        return writer.write(point);
    }

    @Override
    public Location to(String source) {
        WKTReader reader = new WKTReader();
        Point point = null;
        try {
            point = (Point) reader.read(source);
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
