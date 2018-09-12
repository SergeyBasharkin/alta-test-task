package ru.alta.main.integration.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.camel.Converter;
import org.springframework.stereotype.Component;
import ru.alta.main.model.Vacancy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Converter
@Component
public final class VacancyConverter {

    private VacancyConverter(){}

    @Converter
    public static InputStream toInputStream(Vacancy vacancy) throws JsonProcessingException, UnsupportedEncodingException {
        XmlMapper xmlMapper = new XmlMapper();
        return new ByteArrayInputStream(xmlMapper.writeValueAsString(vacancy).getBytes("UTF-8"));
    }
}
