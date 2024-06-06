package by.it_academy.jd2.classifier_service.config;

import by.it_academy.jd2.classifier_service.controller.converter.CategoryEntityToDTOConverter;
import by.it_academy.jd2.classifier_service.controller.converter.CurrencyEntityToDTOConverter;
import by.it_academy.jd2.classifier_service.service.converter.CategoryCUDTOToEntityConverter;
import by.it_academy.jd2.classifier_service.service.converter.CurrencyCUDTOToEntityConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Configuration
public class ConverterConfig {

    @Bean
    public CategoryEntityToDTOConverter userEntityToDTOConverter() {
        return new CategoryEntityToDTOConverter();
    }

    @Bean
    public CurrencyEntityToDTOConverter mailDTOtoEntityConverter() {
        return new CurrencyEntityToDTOConverter();
    }

    @Bean
    public CategoryCUDTOToEntityConverter userCUDTOToEntityConverter() {
        return new CategoryCUDTOToEntityConverter();
    }

    @Bean
    public CurrencyCUDTOToEntityConverter userRegistrationCUDTOtoUserCUDTOConverter() {
        return new CurrencyCUDTOToEntityConverter();
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
