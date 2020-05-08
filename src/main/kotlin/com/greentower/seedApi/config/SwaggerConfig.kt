package com.greentower.seedApi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun docket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
                .apis(RequestHandlerSelectors.basePackage("com.greentower.seedApi.rest.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(arrayListOf(securityContext()))
                .securitySchemes(arrayListOf(apiKey()))
                .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Seed Springboot kotlin")
                .description("Project is a seed to start new api.")
                .version("0.0.1")
                .contact(contact())
                .build()
    }

    private fun contact(): Contact {
        return Contact("Gabriel Longarete Mandelli", "https://github.com/gabriellmandelli", "gabriel-mandelli@hotmail.com")
    }

    fun apiKey(): ApiKey {
        return ApiKey("JWT", "Authorization", "header")
    }

    private fun securityContext() : SecurityContext{
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build()
    }

    private fun defaultAuth():List<SecurityReference>{
        val authorizationScope = AuthorizationScope("global", "acessEverything")
        val scopes = arrayOf(authorizationScope)
        val reference = SecurityReference("JWT", scopes)
        return arrayListOf(reference)
    }
}