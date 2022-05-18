package com.example.plants

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.plants.databinding.ActivityGameBinding
import kotlin.properties.Delegates


class GameActivity : AppCompatActivity() {
    companion object{
        var sun by Delegates.notNull<Int>()
    }

    init {
        sun = 100
    }
    private lateinit var binding: ActivityGameBinding
    private lateinit var rocketAnimation: AnimationDrawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sunDisplayValue = binding.sunDisplayValue
        sunDisplayValue.text = sun.toString()

        var purchasePlantSelected = 0

        var row1 = arrayOf(binding.plantSlot1, binding.plantSlot6, binding.plantSlot11, binding.plantSlot16, binding.plantSlot21, binding.plantSlot26, binding.plantSlot31, binding.plantSlot36, binding.plantSlot41)
        var row2 = arrayOf(binding.plantSlot2, binding.plantSlot7, binding.plantSlot12, binding.plantSlot17, binding.plantSlot22, binding.plantSlot27, binding.plantSlot32, binding.plantSlot37, binding.plantSlot42)
        var row3 = arrayOf(binding.plantSlot3, binding.plantSlot8, binding.plantSlot13, binding.plantSlot18, binding.plantSlot23, binding.plantSlot28, binding.plantSlot33, binding.plantSlot38, binding.plantSlot43)
        var row4 = arrayOf(binding.plantSlot4, binding.plantSlot9, binding.plantSlot14, binding.plantSlot19, binding.plantSlot24, binding.plantSlot29, binding.plantSlot34, binding.plantSlot39, binding.plantSlot44)
        var row5 = arrayOf(binding.plantSlot5, binding.plantSlot10, binding.plantSlot15, binding.plantSlot20, binding.plantSlot25, binding.plantSlot30, binding.plantSlot35, binding.plantSlot40, binding.plantSlot45)
        var plantSlots = arrayOf(row1, row2, row3, row4, row5)

        var backgrounds = binding.background
        backgrounds.setImageResource(R.drawable.background)

        var purchaseSunflowerButton = binding.purchaseSunflowerButton
        var purchasePeashooterButton = binding.purchasePeashooterButton

        purchaseSunflowerButton.setOnClickListener {
            purchasePlantSelected = 1
            //purchaseSunflowerButton.setTextColor(Color.rgb(200, 150, 150))
        }

        purchasePeashooterButton.setOnClickListener {
            purchasePlantSelected = 2
        }

        var plants = mutableListOf<Plant>()

        fun clicked(plant: ImageView, posX: Int, posY: Int) {
            if (purchasePlantSelected != 0) {
                var temp = true
                for (i in plants) {
                    if (i.imageView == plant) {
                        temp = false
                        break
                    }
                }
                if (temp) {
                    if ((purchasePlantSelected == 1) && (sun >= 100)) {
                        sun -= 100
                        var newPlant = Plant(purchasePlantSelected, plant, posX, posY)
                        plants.add(newPlant)
                    }
                    else if ((purchasePlantSelected == 2) && (sun >= 200)) {
                        sun -= 200
                        var newPlant = Plant(purchasePlantSelected, plant, posX, posY)
                        plants.add(newPlant)
                    }
                    purchasePlantSelected = 0
                }
            }
        }

        for (i in 0..4) {
            for (j in 0..8) {
                plantSlots[i][j].setOnClickListener {
                    clicked(plantSlots[i][j], i, j)
                }
            }
        }

        fun update(time: Int) {
            for (i in plants) {
                i.update(time)
            }
            sunDisplayValue.text = sun.toString()
        }

        fun gameLoop() {

            update(40)

            Handler(Looper.getMainLooper()).postDelayed({
                gameLoop()
            }, 40)
        }
        gameLoop()
    }
}