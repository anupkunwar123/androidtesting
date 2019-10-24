package com.anupkunwar.servicelocator.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anupkunwar.servicelocator.Result
import com.anupkunwar.servicelocator.component.getApi
import com.anupkunwar.servicelocator.repo.GetUserRepository

class MainActivityViewModel(
    private val userInfoRepository: GetUserRepository
    = getApi().userRepository
) :
    ViewModel() {


    private var personQuery = MutableLiveData<Int>()

    val isLoading = ObservableField(false)

    private val personResponse =
        Transformations.switchMap(personQuery) {
            userInfoRepository.getPerson()
        }

    val personLiveData =
        Transformations.map(personResponse) {
            when (it) {
                is Result.Loading -> isLoading.set(true)
                else -> isLoading.set(false)
            }
            it
        }

    fun getUserInfo(id: Int) {
        personQuery.postValue(id)
    }


}