package com.milosev.googlemapstestsandbox

import retrofit2.Converter

interface IConverterType {
    fun getFactory(): Converter.Factory
}