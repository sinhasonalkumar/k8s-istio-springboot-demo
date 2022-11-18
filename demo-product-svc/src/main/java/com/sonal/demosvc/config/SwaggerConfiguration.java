package com.sonal.demosvc.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  private ApiInfo apiInfo() {
    return new ApiInfo("Demo REST API",
        "Persist JWT In Swagger UI Across All REST API Calls",
        "1.0",
        "Terms of service",
        new Contact("Your Name", "Your Web", "Tour EmailId"),
        "License of API",
        "API license URL",
        Collections.emptyList());
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.sonal.demo.productssvc.controller"))
        .paths(PathSelectors.any())
        .build();
  }
  
  @Bean
  public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
          ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
          EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
          WebEndpointProperties webEndpointProperties, Environment environment) {
      List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
      Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
      allEndpoints.addAll(webEndpoints);
      allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
      allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
      String basePath = webEndpointProperties.getBasePath();
      EndpointMapping endpointMapping = new EndpointMapping(basePath);
      boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment,
              basePath);
      return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
              corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
              shouldRegisterLinksMapping, null);
  }

  private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment,
          String basePath) {
      return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath)
          || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }

}
