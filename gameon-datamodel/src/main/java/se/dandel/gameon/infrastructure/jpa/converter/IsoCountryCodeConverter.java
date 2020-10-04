package se.dandel.gameon.infrastructure.jpa.converter;


import javax.persistence.AttributeConverter;
import java.util.Locale;

public class IsoCountryCodeConverter implements AttributeConverter<Locale.IsoCountryCode, String> {
    @Override
    public String convertToDatabaseColumn(Locale.IsoCountryCode isoCountryCode) {
        return isoCountryCode == null ? null : String.valueOf(isoCountryCode);
    }

    @Override
    public Locale.IsoCountryCode convertToEntityAttribute(String str) {
        return str == null ? null : Locale.IsoCountryCode.valueOf(str);
    }
}
