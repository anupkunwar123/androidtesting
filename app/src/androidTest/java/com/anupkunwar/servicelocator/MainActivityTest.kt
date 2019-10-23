package com.anupkunwar.servicelocator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.anupkunwar.servicelocator.component.AppComponent
import com.anupkunwar.servicelocator.ui.MainActivity
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)



    @Before
    fun initField(){
        AppComponent.instance = TestAppComponent()
    }


    @Test
    fun testApi(){
//        whenever(AppComponent.instance.apiComponent.apiService.getUser()).thenReturn(CallFake<String>(
//            Response.success("Hello")))
        Thread.sleep(3000)
        onView(withId(R.id.textView)).check(matches(withText("Helli")))
    }

}

class CallFake<T>(
    private val response: Response<T>)
    : Call<T> {

    companion object {
        inline fun <reified T> buildSuccess(body: T): CallFake<T> {
            return CallFake(Response.success(body))
        }

        inline fun <reified T> buildHttpError(
            errorCode: Int,
            contentType: String,
            content: String
        ): CallFake<T> {
            return CallFake(
                Response.error(
                    errorCode,
                    ResponseBody.create(MediaType.parse(contentType), content)
                )
            )
        }
    }


    override fun execute(): Response<T> = response

    override fun enqueue(callback: Callback<T>?) {}

    override fun isExecuted(): Boolean = false

    override fun clone(): Call<T> = this

    override fun isCanceled(): Boolean = false

    override fun cancel() {}

    override fun request(): Request? = null
}