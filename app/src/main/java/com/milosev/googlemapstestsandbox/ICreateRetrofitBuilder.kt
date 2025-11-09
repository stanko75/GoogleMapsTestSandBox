package com.milosev.googlemapstestsandbox

import retrofit2.Retrofit

interface ICreateRetrofitBuilder {
    fun createRetrofitBuilder(baseUrl: String, converterType: IConverterType = ScalarsConverter()): Retrofit
}