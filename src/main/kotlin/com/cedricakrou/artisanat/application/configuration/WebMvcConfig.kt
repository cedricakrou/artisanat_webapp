package com.cedricakrou.artisanat.application.configuration

import com.cedricakrou.artisanat.application.interceptor.AuthenticationUserInterception
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.handler.MappedInterceptor
import java.util.*


@Configuration
class WebMvcConfig(private val authenticationUserInterception: AuthenticationUserInterception)  : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)

        registry.addInterceptor(MappedInterceptor(arrayOf("/**"), authenticationUserInterception))

    }


    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)

        registry!!.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")

        registry.addResourceHandler("/css/**")
            .addResourceLocations("classpath:/static/css/")

        registry.addResourceHandler("/js/**")
            .addResourceLocations("classpath:/static/js/")

    }


    override fun configureContentNegotiation(
            configurer: ContentNegotiationConfigurer) {
        val parameterMap: MutableMap<String, String> = HashMap()
        parameterMap["charset"] = "utf-8"
        configurer.defaultContentType(MediaType(
                MediaType.APPLICATION_JSON, parameterMap))
    }

}