package com.anupkunwar.servicelocator.component

import com.anupkunwar.servicelocator.repo.GetUserRepository
import com.anupkunwar.servicelocator.repo.ResponseData
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.lang.reflect.Type


fun getApi(): ApiComponent = ApiModule()

interface ApiComponent {
    val apiService: ApiService
    val userApiComponent: UserApiComponent
}

interface UserApiComponent {
    val userRepository: GetUserRepository
}


class UserApiComponentImpl : UserApiComponent {
    override val userRepository: GetUserRepository
        get() = GetUserRepository()

}


open class ApiModule : ApiComponent {
    override val userApiComponent: UserApiComponent
        get() = UserApiComponentImpl()


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

internal class ToStringConverterFactory : Converter.Factory() {

    fun fromResponseBody(type: Type, annotations: Array<Annotation>): Converter<ResponseBody, *>? {
        return if (String::class.java == type) {
            Converter<ResponseBody, String> { value -> value.string() }
        } else null
    }

    fun toRequestBody(type: Type, annotations: Array<Annotation>): Converter<*, RequestBody>? {
        return if (String::class.java == type) {
            Converter<String, RequestBody> { value -> RequestBody.create(MEDIA_TYPE, value) }
        } else null
    }

    companion object {
        private val MEDIA_TYPE = MediaType.parse("text/plain")
    }
}

