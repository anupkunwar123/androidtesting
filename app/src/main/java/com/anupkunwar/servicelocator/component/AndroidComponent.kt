package com.anupkunwar.servicelocator.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anupkunwar.servicelocator.ui.MainActivityViewModel

fun getViewModelComponent(): ViewModelComponent {
    val viewModels = mutableMapOf<Class<out ViewModel>, ViewModel>()
    viewModels[MainActivityViewModel::class.java] = MainActivityViewModel()
    return ViewModelComponentImpl(viewModels)
}

interface ViewModelComponent {
    fun getViewModelFactory(): ViewModelProvider.Factory

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
