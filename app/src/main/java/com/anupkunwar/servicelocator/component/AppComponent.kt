package com.anupkunwar.servicelocator.component

fun getApp() = AppComponent.instance

interface AppComponent {
    val apiComponent: ApiComponent
    val viewModelComponent: ViewModelComponent

    companion object {
        lateinit var instance: AppComponent
    }
}

class AppComponentImpl(
    override val apiComponent: ApiComponent,
    override val viewModelComponent: ViewModelComponent
) : AppComponent



