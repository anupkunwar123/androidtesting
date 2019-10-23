package com.anupkunwar.servicelocator.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun getViewModelComponent(viewModels: MutableMap<Class<out ViewModel>, ViewModel>): ViewModelComponent =
    ViewModelComponentImpl(viewModels)

interface ViewModelComponent {
    fun getViewModelFactory(): ViewModelProvider.Factory

    companion object {
        lateinit var instance: ViewModelComponent
    }
}

class ViewModelComponentImpl(private val viewModels: MutableMap<Class<out ViewModel>, ViewModel>) :
    ViewModelComponent {
    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModels[modelClass] as T
            }
        }
    }
}
