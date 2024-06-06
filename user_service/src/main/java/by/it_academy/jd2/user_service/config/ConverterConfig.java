package by.it_academy.jd2.user_service.config;

import by.it_academy.jd2.user_service.controller.converter.UserEntityToDTOConverter;
import by.it_academy.jd2.user_service.service.converter.MailDTOtoEntityConverter;
import by.it_academy.jd2.user_service.service.converter.UserCUDTOToEntityConverter;
import by.it_academy.jd2.user_service.service.converter.UserRegistrationCUDTOtoUserCUDTOConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Configuration
public class ConverterConfig {

    @Bean
    public UserEntityToDTOConverter userEntityToDTOConverter() {
        return new UserEntityToDTOConverter();
    }

    @Bean
    public MailDTOtoEntityConverter mailDTOtoEntityConverter() {
        return new MailDTOtoEntityConverter();
    }

    @Bean
    public UserCUDTOToEntityConverter userCUDTOToEntityConverter() {
        return new UserCUDTOToEntityConverter();
    }

    @Bean
    public UserRegistrationCUDTOtoUserCUDTOConverter userRegistrationCUDTOtoUserCUDTOConverter() {
        return new UserRegistrationCUDTOtoUserCUDTOConverter();
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
