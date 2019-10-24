package com.anupkunwar.servicelocator.component

import com.anupkunwar.servicelocator.repo.GetUserRepository
import com.anupkunwar.servicelocator.repo.ResponseData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


fun getApi() = ApiModule

interface ApiComponent {
    val apiService: ApiService
    val okHttpClient: OkHttpClient
    val userRepository: GetUserRepository

}


object ApiModule : ApiComponent {

    override val userRepository: GetUserRepository
        get() = GetUserRepository(apiService)

    override val okHttpClient: OkHttpClient = OkHttpClient()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override val apiService: ApiService
        get() = retrofit.create(ApiService::class.java)

}

interface ApiService {

    @GET("users/2")
    fun getUser(): Call<ResponseData>
}


