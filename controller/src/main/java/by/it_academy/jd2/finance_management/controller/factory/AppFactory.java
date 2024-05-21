package by.it_academy.jd2.finance_management.controller.factory;

import by.it_academy.jd2.finance_management.controller.utils.LocalDateTimeSerializer;
import by.it_academy.jd2.finance_management.controller.utils.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class AppFactory {
    private static final ObjectMapper objectMapper;

    static {

        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());

        objectMapper.registerModule(module);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
