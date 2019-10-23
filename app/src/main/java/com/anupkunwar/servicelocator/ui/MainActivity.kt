package com.anupkunwar.servicelocator.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anupkunwar.servicelocator.R
import com.anupkunwar.servicelocator.Result
import com.anupkunwar.servicelocator.component.getApp
import com.anupkunwar.servicelocator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var viewModelFactory = getApp().viewModelComponent.getViewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        binding.viewModel = viewModel
        viewModel.personLiveData.observe(this, Observer {
            when (it) {
                is Result.Success -> binding.person = it.data
            }
            print(it)
        })
        viewModel.getUserInfo(id = 1)

    }
}
