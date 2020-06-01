package com.greentower.seedApi.infrastructure.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Component

@Component
class TranslatorUtil @Autowired internal constructor(messageSource : ResourceBundleMessageSource){
    companion object {
        private lateinit var messageSource: ResourceBundleMessageSource

        fun toLocale(messageCode: String) : String {
            val locale = LocaleContextHolder.getLocale()
            return messageSource.getMessage(messageCode, null, locale)
        }
    }

    init {
        Companion.messageSource = messageSource
    }
}