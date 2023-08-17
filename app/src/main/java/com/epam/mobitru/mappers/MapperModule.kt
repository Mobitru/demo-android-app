package com.epam.mobitru.mappers

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapperModule = module {
    factoryOf(::OrderMapper)
    factoryOf(::ProductMapper)
    factoryOf(::UserMapper)
}