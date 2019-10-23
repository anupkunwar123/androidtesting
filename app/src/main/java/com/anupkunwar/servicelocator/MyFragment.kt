package com.anupkunwar.servicelocator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.anupkunwar.servicelocator.databinding.FragmentMyBinding
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.fragment_my.view.*
import kotlin.random.Random

class MyFragment : Fragment() {
    private val firstName = mutableListOf("Jack", "Mack")
    private val lastName = mutableListOf("Ma", "Miller")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMyBinding>(
            inflater,
            R.layout.fragment_my,
            container,
            false
        )
        binding.user = UserImpl
        val User:User = UserImpl
        binding.root.button.setOnClickListener {
            User.firstName = firstName[Random.nextInt(0, 2)]
            User.lastName = lastName[Random.nextInt(0, 2)]
        }
        binding.root.nextActivity.setOnClickListener {
            Intent(context, NextActivity::class.java).apply {
                startActivity(this)
            }
        }
        return binding.root
    }


}