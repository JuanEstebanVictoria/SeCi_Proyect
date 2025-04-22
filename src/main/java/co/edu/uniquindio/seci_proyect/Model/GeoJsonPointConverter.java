package co.edu.uniquindio.seci_proyect.Model;

import org.springframework.data.geo.Point;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@WritingConverter
public class GeoJsonPointConverter implements Converter<Point, GeoJsonPoint> {
    @Override
    public GeoJsonPoint convert(Point source) {
        return new GeoJsonPoint(source.getX(), source.getY());
    }
}