package spring.first.fitness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfoAll()).select()
                .apis(RequestHandlerSelectors.basePackage("spring.first.fitness")).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfoAll() {
        return new ApiInfoBuilder().title("REST API documentation").version("1.0.1").description("Bachelor diploma project </a>").contact(new Contact("Aigerim Tumbayeva", "https://github.com/Aika8/fitness-app", "aigerimt922@gmail.com")).build();
    }

}