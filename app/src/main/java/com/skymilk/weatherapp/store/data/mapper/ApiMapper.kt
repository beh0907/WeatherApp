package com.skymilk.weatherapp.store.data.mapper

interface ApiMapper<Domain, Entity> {

    fun mapToDomain(apiEntity: Entity): Domain
}