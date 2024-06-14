package by.it_academy.jd2.user_service.controller.token.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public class GrantedAuthoritySerializer extends JsonSerializer<Collection<? extends GrantedAuthority>> {
    @Override
    public void serialize(Collection<? extends GrantedAuthority> value,
                          JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {

        for (GrantedAuthority authority : value) {
            gen.writeString(authority.getAuthority());
        }
    }
}