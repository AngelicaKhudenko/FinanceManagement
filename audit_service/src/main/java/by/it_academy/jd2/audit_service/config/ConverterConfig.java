package by.it_academy.jd2.audit_service.config;

import by.it_academy.jd2.audit_service.core.converters.AuditEntityToDTOConverter;
import by.it_academy.jd2.audit_service.core.converters.AuditCUDTOToEntityConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConverterConfig {

    @Bean
    public AuditEntityToDTOConverter auditEntityToDTOConverter() {
        return new AuditEntityToDTOConverter();
    }

    @Bean
    public AuditCUDTOToEntityConverter auditCUDTOToEntityConverter() {
        return new AuditCUDTOToEntityConverter();
    }
    @Bean
    public ConversionService conversionService() {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        factory.setConverters(getConverters());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Set<Converter<?, ?>> getConverters() {
        Set<Converter<?, ?>> converters = new HashSet<>();
        converters.add(auditEntityToDTOConverter());
        converters.add(auditCUDTOToEntityConverter());
        return converters;
    }

    @Bean
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        return new ConversionServiceFactoryBean();
    }

}
