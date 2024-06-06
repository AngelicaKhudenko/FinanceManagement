package by.it_academy.jd2.account_service.config;

import by.it_academy.jd2.account_service.controller.converter.AccountEntityToDTOConverter;
import by.it_academy.jd2.account_service.controller.converter.OperationEntityToDTOConverter;
import by.it_academy.jd2.account_service.service.converter.AccountCUDTOToEntityConverter;
import by.it_academy.jd2.account_service.service.converter.OperationCUDTOToEntityConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Configuration
public class ConverterConfig {

    @Bean
    public AccountEntityToDTOConverter userEntityToDTOConverter() {
        return new AccountEntityToDTOConverter();
    }

    @Bean
    public OperationEntityToDTOConverter mailDTOtoEntityConverter() {
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
