package antessio.wallet.infrastructure.persistence;

import antessio.eventsourcing.jsonconversion.JsonConverter;
import antessio.wallet.infrastructure.JacksonJsonConverter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;

@Converter(autoApply = true)
public class DatabaseJsonConverter implements AttributeConverter<Map<String, Object>, String> {

    private final JsonConverter objectMapper = new JacksonJsonConverter();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {

        return objectMapper.toJson(attribute);

    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {

        return objectMapper.fromJson(dbData, Map.class);

    }

}
