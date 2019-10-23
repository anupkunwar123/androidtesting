package com.anupkunwar.servicelocator


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.anupkunwar.servicelocator.model.Person
import com.anupkunwar.servicelocator.repo.GetUserRepository
import com.anupkunwar.servicelocator.ui.MainActivityViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun testWhenLoading() {
        val viewModel = makeData(Result.Loading)
        viewModel.getUserInfo(1)
        val result = LiveDataTestUtil.getValue(viewModel.personLiveData)
        assert(viewModel.isLoading.get()!!)
        assert(result is Result.Loading)

    }

    private fun makeData(result: Result<Person>):
            MainActivityViewModel {
        val data = MutableLiveData<Result<Person>>().apply {
            value = result
        }
        val userRepository = spy(GetUserRepository(mock()))
        doReturn(data).`when`(userRepository).getPerson()
        return MainActivityViewModel(userInfoRepository = userRepository)
    }

    @Test
    fun testWhenError() {
        val viewModel = makeData(Result.Error(101,
            "Something is wrong"))
        viewModel.getUserInfo(1)
        val result = LiveDataTestUtil.getValue(viewModel.personLiveData)
        assert(result is Result.Error)
        assert(!viewModel.isLoading.get()!!)
    }

    @Test
    fun testWhenSuccess() {
        val viewModel = makeData(Result.Success(mock()))
        viewModel.getUserInfo(1)
        val result = LiveDataTestUtil.getValue(viewModel.personLiveData)
        assert(result is Result.Success)
        assert(!viewModel.isLoading.get()!!)
    }
}