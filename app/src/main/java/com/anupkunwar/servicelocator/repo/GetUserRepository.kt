package com.anupkunwar.servicelocator.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anupkunwar.servicelocator.Result
import com.anupkunwar.servicelocator.component.ApiService
import com.anupkunwar.servicelocator.component.getApi
import com.anupkunwar.servicelocator.model.Person
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

open class GetUserRepository(private val apiService: ApiService = getApi().apiService) {

    open fun getPerson(): LiveData<Result<Person>> {
        val result = MutableLiveData<Result<Person>>()
        result.postValue(Result.Loading)
        apiService.getUser().enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> Result.Error(101, "Cannot connect to internet")
                    else -> Result.Error(102, "Something is wrong!!")
                }
            }

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!
                    result.postValue(
                        Result.Success(
                            Person(
                                id = data.data.id,
                                firstName = data.data.firstName,
                                lastName = data.data.lastName,
                                email = data.data.email,
                                avatar = data.data.avatar
                            )
                        )
                    )
                } else {
                    result.postValue(Result.Error(response.code(), response.errorBody().toString()))
                }
            }

        })
        return result
    }
}

data class ResponseData(@SerializedName("data") val data: PersonResponse)

data class PersonResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("avatar") val avatar: String
)