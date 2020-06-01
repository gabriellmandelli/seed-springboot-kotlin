package com.greentower.seedApi.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*
import javax.servlet.http.HttpServletRequest

@Configuration
class InternationalizationConfig : AcceptHeaderLocaleResolver(), WebMvcConfigurer {
    override fun resolveLocale(request: HttpServletRequest): Locale {
        val headerLang = request.getHeader("Accept-Language")

        return if (headerLang == null || headerLang.isEmpty()){
            Locale.getDefault()
        } else {
            Locale.lookup(Locale.LanguageRange.parse(headerLang), arrayListOf(Locale(headerLang)))
        }
    }

    @Bean
    fun messageSource() : ResourceBundleMessageSource {
        val resourceSource = ResourceBundleMessageSource()
        resourceSource.setBasename("messages")
        resourceSource.setDefaultEncoding("ISO-8859-1")
        resourceSource.setUseCodeAsDefaultMessage(true)
        return resourceSource
    }
}