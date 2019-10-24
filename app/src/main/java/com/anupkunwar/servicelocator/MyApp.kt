package com.anupkunwar.servicelocator

import android.app.Application
import com.anupkunwar.servicelocator.component.AppComponent
import com.anupkunwar.servicelocator.component.AppComponentImpl
import com.anupkunwar.servicelocator.component.getApi
import com.anupkunwar.servicelocator.component.getViewModelComponent

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponent.instance = AppComponentImpl(
            apiComponent = getApi(),
            viewModelComponent = getViewModelComponent()
        )

    }


}