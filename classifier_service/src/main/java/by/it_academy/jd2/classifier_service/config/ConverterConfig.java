package by.it_academy.jd2.classifier_service.config;

import by.it_academy.jd2.classifier_service.core.converters.CategoryEntityToDTOConverter;
import by.it_academy.jd2.classifier_service.core.converters.CurrencyEntityToDTOConverter;
import by.it_academy.jd2.classifier_service.core.converters.CategoryCUDTOToEntityConverter;
import by.it_academy.jd2.classifier_service.core.converters.CurrencyCUDTOToEntityConverter;
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
    public CategoryEntityToDTOConverter categoryEntityToDTOConverter() {
        return new CategoryEntityToDTOConverter();
    }

    @Bean
    public CurrencyEntityToDTOConverter currencyEntityToDTOConverter() {
        return new CurrencyEntityToDTOConverter();
    }

    @Bean
    public CategoryCUDTOToEntityConverter categoryCUDTOToEntityConverter() {
        return new CategoryCUDTOToEntityConverter();
    }

    @Bean
    public CurrencyCUDTOToEntityConverter currencyCUDTOToEntityConverter() {
        return new CurrencyCUDTOToEntityConverter();
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
        converters.add(categoryEntityToDTOConverter());
        converters.add(currencyEntityToDTOConverter());
        converters.add(categoryCUDTOToEntityConverter());
        converters.add(currencyCUDTOToEntityConverter());

        return converters;
    }

    @Bean
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        return new ConversionServiceFactoryBean();
    }
}
