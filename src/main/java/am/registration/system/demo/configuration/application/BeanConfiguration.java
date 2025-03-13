package am.registration.system.demo.configuration.application;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

/**
 * Configuration class for defining and managing application-wide beans.
 * <p>
 * This class centralizes the bean creation for commonly used components,
 * promoting consistency and reusability throughout the application.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 15.02.25
 */
@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    /**
     * Creates and configures a ModelMapper bean.
     * <p>
     * The ModelMapper is set to use the STRICT matching strategy to enforce
     * a precise mapping between source and destination objects, reducing the
     * risk of incorrect mappings.
     * </p>
     *
     * @return a configured instance of ModelMapper
     */
    @Bean
    protected ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(STRICT);
        return modelMapper;
    }
}