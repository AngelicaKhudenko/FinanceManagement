package by.it_academy.jd2.audit_service.config;

import by.it_academy.jd2.audit_service.controller.converter.AuditEntityToDTOConverter;
import by.it_academy.jd2.audit_service.service.converter.AuditCUDTOToEntityConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Configuration
public class ConverterConfig {

    @Bean
    public AuditEntityToDTOConverter userEntityToDTOConverter() {
        return new AuditEntityToDTOConverter();
    }

    @Bean
    public AuditCUDTOToEntityConverter mailDTOtoEntityConverter() {
        return new AuditCUDTOToEntityConverter();
    }
    @Bean
    public ConversionService conversionService(Set<Converter<?, ?>> converters,
                                               ConversionServiceFactoryBean factory) {
        factory.setConverters(converters);
        return factory.getObject();
    }

    @Bean
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        return new ConversionServiceFactoryBean();
    }
}
