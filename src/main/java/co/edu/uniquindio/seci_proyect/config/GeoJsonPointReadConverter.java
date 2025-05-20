package co.edu.uniquindio.seci_proyect.config;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

@ReadingConverter
public class GeoJsonPointReadConverter implements Converter<Document, GeoJsonPoint> {

    @Override
    public GeoJsonPoint convert(Document source) {
        List<Double> coordinates = (List<Double>) source.get("coordinates");
        if (coordinates == null || coordinates.size() != 2) {
            throw new IllegalArgumentException("Invalid GeoJSON coordinates: " + source);
        }
        return new GeoJsonPoint(coordinates.get(0), coordinates.get(1));
    }
}
