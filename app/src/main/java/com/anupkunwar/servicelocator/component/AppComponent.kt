package com.anupkunwar.servicelocator.component

import androidx.lifecycle.ViewModel
import com.anupkunwar.servicelocator.ui.MainActivityViewModel

fun getApp() = AppComponent.instance

interface AppComponent {
    val apiComponent: ApiComponent
    val viewModelComponent: ViewModelComponent

    companion object {
        lateinit var instance: AppComponent
    }
}

open class AppComponentImpl : AppComponent {
    override val viewModelComponent: ViewModelComponent
        get() {
            val viewModels = mutableMapOf<Class<out ViewModel>, ViewModel>()
            viewModels.put(MainActivityViewModel::class.java, MainActivityViewModel())
            return getViewModelComponent(viewModels)
        }
    override val apiComponent: ApiComponent
        get() = getApi()

}



