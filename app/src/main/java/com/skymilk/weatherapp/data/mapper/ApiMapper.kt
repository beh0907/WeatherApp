package com.skymilk.weatherapp.data.mapper

interface ApiMapper<Domain, Entity> {

    fun mapToDomain(apiEntity: Entity): Domain
}