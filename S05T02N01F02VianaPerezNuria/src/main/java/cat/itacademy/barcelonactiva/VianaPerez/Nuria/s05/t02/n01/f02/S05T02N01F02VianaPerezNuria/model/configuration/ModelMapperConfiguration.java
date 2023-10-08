package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
