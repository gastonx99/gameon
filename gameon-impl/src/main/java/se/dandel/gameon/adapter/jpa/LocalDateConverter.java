package se.dandel.gameon.adapter.jpa;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.AttributeConverter;

public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
