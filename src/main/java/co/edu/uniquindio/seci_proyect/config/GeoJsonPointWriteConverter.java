package co.edu.uniquindio.seci_proyect.config;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

@WritingConverter
public class GeoJsonPointWriteConverter implements Converter<GeoJsonPoint, Document> {
    @Override
    public Document convert(GeoJsonPoint source) {
        Document document = new Document();
        document.put("type", "Point");
        document.put("coordinates", List.of(source.getX(), source.getY()));
        return document;
    }


}
