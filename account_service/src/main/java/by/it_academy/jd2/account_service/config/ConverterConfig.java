package by.it_academy.jd2.account_service.config;

import by.it_academy.jd2.account_service.core.converters.AccountEntityToDTOConverter;
import by.it_academy.jd2.account_service.core.converters.OperationEntityToDTOConverter;
import by.it_academy.jd2.account_service.core.converters.AccountCUDTOToEntityConverter;
import by.it_academy.jd2.account_service.core.converters.OperationCUDTOToEntityConverter;
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
    public AccountEntityToDTOConverter accountEntityToDTOConverter() {
        return new AccountEntityToDTOConverter();
    }

    @Bean
    public OperationEntityToDTOConverter operationEntityToDTOConverter() {
        return new OperationEntityToDTOConverter();
    }
    @Bean
    public AccountCUDTOToEntityConverter accountCUDTOToEntityConverter() {
        return new AccountCUDTOToEntityConverter();
    }
    @Bean
    public OperationCUDTOToEntityConverter operationCUDTOToEntityConverter() {
        return new OperationCUDTOToEntityConverter();
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
        converters.add(accountEntityToDTOConverter());
        converters.add(operationEntityToDTOConverter());
        converters.add(accountCUDTOToEntityConverter());
        converters.add(operationCUDTOToEntityConverter());

        return converters;
    }

    @Bean
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        return new ConversionServiceFactoryBean();
    }
}
