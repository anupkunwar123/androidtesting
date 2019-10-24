package com.anupkunwar.servicelocator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anupkunwar.servicelocator.component.ApiComponent
import com.anupkunwar.servicelocator.component.AppComponent
import com.anupkunwar.servicelocator.component.ViewModelComponent
import com.anupkunwar.servicelocator.component.ViewModelComponentImpl
import com.anupkunwar.servicelocator.model.Person
import com.anupkunwar.servicelocator.repo.GetUserRepository
import com.anupkunwar.servicelocator.ui.MainActivity
import com.anupkunwar.servicelocator.ui.MainActivityViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

   private  val mockUserRepository: GetUserRepository = mock()

    @Before
    fun initField() {
        val viewModels = mutableMapOf<Class<out ViewModel>, ViewModel>()
        viewModels[MainActivityViewModel::class.java] =
            MainActivityViewModel(userInfoRepository = mockUserRepository)
        val appComponent = mock<AppComponent>()
        whenever(appComponent.viewModelComponent).thenReturn(ViewModelComponentImpl(viewModels))
        AppComponent.instance = appComponent
    }


    @Test
    //here progressbar shouldn't be displayed and textView should display success data
    fun testSuccess() {
        val liveData = MutableLiveData<Result<Person>>()
        liveData.postValue(
            Result.Success(
                Person(
                    id = 1,
                    email = "anupkunwar@gmail.com",
                    firstName = "Anup",
                    lastName = "kunwar",
                    avatar = "hello world"
                )
            )
        )
        doReturn(liveData).`when`(mockUserRepository).getPerson()
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.textViewFirstName)).check(matches(withText("Anup")))
        onView(withId(R.id.textViewLastName)).check(matches(withText("kunwar")))

    }

    @Test
    //here progressbar needs to be displayed
    fun testLoading() {
        val liveData = MutableLiveData<Result<Person>>()
        liveData.postValue(Result.Loading)
        doReturn(liveData).`when`(mockUserRepository).getPerson()
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

    }

    @Test
    //here progressbar shouldn't be displayed
    fun testError() {
        val liveData = MutableLiveData<Result<Person>>()
        liveData.postValue(Result.Error(1, "Something happened"))
        doReturn(liveData).`when`(mockUserRepository).getPerson()
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))

    }

    class TestComponent : AppComponent {
        override val apiComponent: ApiComponent
            get() = mock()
        override val viewModelComponent: ViewModelComponent
            get() = mock()

    }

}
