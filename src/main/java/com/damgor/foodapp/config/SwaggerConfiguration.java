package com.damgor.foodapp.config;


import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.*;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Configuration
public class SwaggerConfiguration {

    public Docket getSwaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Principal.class, Link.class, Links.class, LinkRelation.class, TemplateVariables.class,
                        URL.class, UriTemplate.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.damgor.foodapp"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails () {
        return new ApiInfo(
                "Food-App API",
                "The application is designed to facilitate diet by calculating basic indicators (e.g. BMI), determining caloric" +
                        "demand, searching for recipes and products (from external databases), which can then be added to the nutrition " +
                        "diary, which allows you to track currently consumed meals in relation to for the nutritional purposes set by " +
                        "the user.",
                "1.0",
                "Free access",
                new springfox.documentation.service.Contact("Damian", "www.null.com", "damian.gorka94@gmail.com"),
                "API License",
                "www.null.com",
                Collections.emptyList());
    }

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }
}
