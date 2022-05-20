package com.example.plants

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.plants.databinding.ActivityGameBinding
import kotlin.properties.Delegates


class GameActivity : AppCompatActivity() {
    companion object {
        var sun by Delegates.notNull<Int>()
    }

    init {
        sun = 50
    }
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sunDisplayValue = binding.sunDisplayValue
        sunDisplayValue.text = sun.toString()

        var purchasePlantSelected = 0
        var gameCounter = 0

        var row1 = arrayOf(binding.plantSlot1, binding.plantSlot6, binding.plantSlot11, binding.plantSlot16, binding.plantSlot21, binding.plantSlot26, binding.plantSlot31, binding.plantSlot36, binding.plantSlot41)
        var row2 = arrayOf(binding.plantSlot2, binding.plantSlot7, binding.plantSlot12, binding.plantSlot17, binding.plantSlot22, binding.plantSlot27, binding.plantSlot32, binding.plantSlot37, binding.plantSlot42)
        var row3 = arrayOf(binding.plantSlot3, binding.plantSlot8, binding.plantSlot13, binding.plantSlot18, binding.plantSlot23, binding.plantSlot28, binding.plantSlot33, binding.plantSlot38, binding.plantSlot43)
        var row4 = arrayOf(binding.plantSlot4, binding.plantSlot9, binding.plantSlot14, binding.plantSlot19, binding.plantSlot24, binding.plantSlot29, binding.plantSlot34, binding.plantSlot39, binding.plantSlot44)
        var row5 = arrayOf(binding.plantSlot5, binding.plantSlot10, binding.plantSlot15, binding.plantSlot20, binding.plantSlot25, binding.plantSlot30, binding.plantSlot35, binding.plantSlot40, binding.plantSlot45)
        var plantSlots = arrayOf(row1, row2, row3, row4, row5)

        var zombieSpawners = arrayOf(binding.zombieSpawner1, binding.zombieSpawner2, binding.zombieSpawner3, binding.zombieSpawner4, binding.zombieSpawner5)

        var backgrounds = binding.background
        backgrounds.setImageResource(R.drawable.background)

        var purchaseSunflowerButton = binding.purchaseSunflowerButton
        var purchasePeashooterButton = binding.purchasePeashooterButton

        purchaseSunflowerButton.setOnClickListener {
            purchasePlantSelected = 1
        }

        purchasePeashooterButton.setOnClickListener {
            purchasePlantSelected = 2
        }

        var plants = mutableListOf<Plant>()
        var zombies = mutableListOf<Zombie>()

        fun clicked(plant: ImageView) {
            if (purchasePlantSelected != 0) {
                var temp = true
                for (i in plants) {
                    if (i.imageView == plant) {
                        temp = false
                        break
                    }
                }
                if (temp) {
                    if ((purchasePlantSelected == 1) && (sun >= 50)) {
                        sun -= 50
                        var newPlant = Plant(purchasePlantSelected, plant)
                        plants.add(newPlant)
                    }
                    else if ((purchasePlantSelected == 2) && (sun >= 100)) {
                        sun -= 100
                        var newPlant = Plant(purchasePlantSelected, plant)
                        plants.add(newPlant)
                    }
                    purchasePlantSelected = 0
                }
            }
        }

        for (i in 0..4) {
            for (j in 0..8) {
                plantSlots[i][j].setOnClickListener {
                    clicked(plantSlots[i][j])
                }
            }
        }

        fun update(time: Int) {
            for (i in plants) {
                i.update(time)
            }
            for (i in zombies) {
                i.update(time)
            }
            sunDisplayValue.text = sun.toString()
            gameCounter += 1
        }

        fun addZombie(lane : Int) {
            if ((lane >= 1) && (lane <= 5)) {
                val imageView = ImageView(this)
                imageView.layoutParams = LinearLayout.LayoutParams(200, 300)
                imageView.translationX = (2700 + (-25..25).random()).toFloat()
                imageView.translationY = (((lane - 1) * 250) + 50 + (-15..15).random()).toFloat()

                val imgResId = R.drawable.zombie
                imageView.setImageResource(imgResId)

                binding.GameLayout.addView(imageView)

                var newZombie = Zombie(imageView)
                zombies.add(newZombie)
            }
        }
        //addZombie(1)
        //addZombie(2)
        //addZombie(3)
        //addZombie(4)
        //addZombie(5)

        fun checkCollisions() {
            for (plant in plants) {
                for (zombie in zombies) {
                    if ((zombie.imageView.x <= plant.imageView.x + 100) && (zombie.imageView.x >= plant.imageView.x - 100)) {
                        if ((zombie.imageView.y <= plant.imageView.y + 100) && (zombie.imageView.y >= plant.imageView.y - 100)) {
                            zombie.eat()
                        }
                        else {
                            zombie.isEating = false
                        }
                    }
                    else {
                        zombie.isEating = false
                    }
                }
            }
        }

        fun gameLoop() {

            update(40)

            if (gameCounter == 50) { // multiply this by 10
                addZombie((1..5).random())
                //Log.d("GameActivity", "Top Left Plant Position: ${plantSlots[0][0].x} and ${plantSlots[0][0].y}")
            }

            if (gameCounter%10 == 0) {
                checkCollisions()
            }

            Handler(Looper.getMainLooper()).postDelayed({
                gameLoop()
            }, 40)
        }
        gameLoop()
    }

}
