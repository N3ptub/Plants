package com.example.plants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.plants.databinding.ActivityGameBinding
import com.example.plants.databinding.ActivityMainBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    //private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //while (running == true) {

        //}
    }
}