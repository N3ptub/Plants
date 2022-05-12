package com.example.plants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.SyncStateContract.Helpers.update
import android.widget.Button
import android.widget.ImageView
import com.example.plants.databinding.ActivityGameBinding
import com.example.plants.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class GameActivity : AppCompatActivity() {
    companion object{
        var sun by Delegates.notNull<Int>()
        lateinit var plantSlots : Array<Array<ImageView>>
    }
    private lateinit var binding: ActivityGameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var sunDisplayValue = binding.sunDisplayValue
        var row1 = arrayOf(binding.plantSlot1, binding.plantSlot6, binding.plantSlot11, binding.plantSlot16, binding.plantSlot21, binding.plantSlot26, binding.plantSlot31, binding.plantSlot36, binding.plantSlot41)
        var row2 = arrayOf(binding.plantSlot2, binding.plantSlot7, binding.plantSlot12, binding.plantSlot17, binding.plantSlot22, binding.plantSlot27, binding.plantSlot32, binding.plantSlot37, binding.plantSlot42)
        var row3 = arrayOf(binding.plantSlot3, binding.plantSlot8, binding.plantSlot13, binding.plantSlot18, binding.plantSlot23, binding.plantSlot28, binding.plantSlot33, binding.plantSlot38, binding.plantSlot43)
        var row4 = arrayOf(binding.plantSlot4, binding.plantSlot9, binding.plantSlot14, binding.plantSlot19, binding.plantSlot24, binding.plantSlot29, binding.plantSlot34, binding.plantSlot39, binding.plantSlot44)
        var row5 = arrayOf(binding.plantSlot5, binding.plantSlot10, binding.plantSlot15, binding.plantSlot20, binding.plantSlot25, binding.plantSlot30, binding.plantSlot35, binding.plantSlot40, binding.plantSlot45)
        var plantSlots = arrayOf(row1, row2, row3, row4, row5)
        var plant1 = SunflowerPlant()

        fun update() {
            plant1.update(40)
        }

        fun gameLoop() {

            update()

            Handler(Looper.getMainLooper()).postDelayed({
                gameLoop()
            }, 40)
        }
        gameLoop()
    }
}