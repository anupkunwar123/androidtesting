package com.anupkunwar.servicelocator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.anupkunwar.servicelocator.databinding.ActivityMainBinding
import com.anupkunwar.servicelocator.databinding.ActivityNextBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class NextActivity : AppCompatActivity() {

    private val firstName = mutableListOf("Jack", "Mack")
    private val lastName = mutableListOf("Ma", "Miller")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityNextBinding>(this, R.layout.activity_next)
        val user = UserImpl
        binding.user = user
        button.setOnClickListener {
            user.firstName = firstName[Random.nextInt(0, 2)]
            user.lastName = lastName[Random.nextInt(0, 2)]
        }
    }
}
