package com.cedricakrou.artisanat.application.configuration.swagger

import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

//@Configuration
//@EnableSwagger2
class SwaggerConfig {


//    @Bean
    fun config() : Docket = Docket( DocumentationType.SWAGGER_2 )
                                .select()
                                .apis( RequestHandlerSelectors.basePackage( "com.cedricakrou.artisanat.infrastructure.remote.web_services" ) )
                                .paths( PathSelectors.any() )
                                .build()


}