package com.anupkunwar.servicelocator

import android.app.Application
import com.anupkunwar.servicelocator.component.AppComponent
import com.anupkunwar.servicelocator.component.AppComponentImpl

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponent.instance = AppComponentImpl()
    }
}