package com.example.plants

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.SyncStateContract.Helpers.update
import android.text.TextUtils.indexOf
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.plants.databinding.ActivityGameBinding
import kotlin.properties.Delegates


open class GameActivity : AppCompatActivity() {
    companion object {
        var sun by Delegates.notNull<Int>()
        var projectiles = mutableListOf<Projectile>()
    }

    init {
        sun = 5000
    }
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sunDisplayValue = binding.sunDisplayValue
        sunDisplayValue.text = sun.toString()

        var purchasePlantSelected = 0
        var gameCounter = 0
        var zombieCounter = 0
        var previousProjectile = Projectile(0)

        val row1 = arrayOf(binding.plantSlot1, binding.plantSlot6, binding.plantSlot11, binding.plantSlot16, binding.plantSlot21, binding.plantSlot26, binding.plantSlot31, binding.plantSlot36, binding.plantSlot41)
        val row2 = arrayOf(binding.plantSlot2, binding.plantSlot7, binding.plantSlot12, binding.plantSlot17, binding.plantSlot22, binding.plantSlot27, binding.plantSlot32, binding.plantSlot37, binding.plantSlot42)
        val row3 = arrayOf(binding.plantSlot3, binding.plantSlot8, binding.plantSlot13, binding.plantSlot18, binding.plantSlot23, binding.plantSlot28, binding.plantSlot33, binding.plantSlot38, binding.plantSlot43)
        val row4 = arrayOf(binding.plantSlot4, binding.plantSlot9, binding.plantSlot14, binding.plantSlot19, binding.plantSlot24, binding.plantSlot29, binding.plantSlot34, binding.plantSlot39, binding.plantSlot44)
        val row5 = arrayOf(binding.plantSlot5, binding.plantSlot10, binding.plantSlot15, binding.plantSlot20, binding.plantSlot25, binding.plantSlot30, binding.plantSlot35, binding.plantSlot40, binding.plantSlot45)
        val plantSlots = arrayOf(row1, row2, row3, row4, row5)

        var zombieSpawners = arrayOf(binding.zombieSpawner1, binding.zombieSpawner2, binding.zombieSpawner3, binding.zombieSpawner4, binding.zombieSpawner5)

        val backgrounds = binding.background
        backgrounds.setImageResource(R.drawable.background)

        val purchaseSunflowerButton = binding.purchaseSunflowerButton
        val purchasePeashooterButton = binding.purchasePeashooterButton

        purchaseSunflowerButton.setOnClickListener {
            purchasePlantSelected = 1
        }

        purchasePeashooterButton.setOnClickListener {
            purchasePlantSelected = 2
        }

        var plants = mutableListOf<Plant>()
        val zombies = mutableListOf<Zombie>()

        fun clicked(plant: ImageView, lane: Int) {
            if (purchasePlantSelected != 0) {
                var duplicate = true
                for (i in plants) {
                    if (i.imageView == plant) {
                        duplicate = false
                        break
                    }
                }
                if (duplicate) {
                    if ((purchasePlantSelected == 1) && (sun >= 50)) {
                        sun -= 50
                        val newPlant = Plant(purchasePlantSelected, plant, (lane + 1))
                        plants.add(newPlant)
                    }
                    else if ((purchasePlantSelected == 2) && (sun >= 100)) {
                        sun -= 100
                        val newPlant = Plant(purchasePlantSelected, plant, (lane + 1))
                        plants.add(newPlant)
                    }
                    purchasePlantSelected = 0
                }
            }
        }

        for (i in 0..4) {
            for (j in 0..8) {
                plantSlots[i][j].setOnClickListener {
                    clicked(plantSlots[i][j], i)
                }
            }
        }

        fun addProjectile(projectile: Projectile) {
            Log.d("GameActivity", "ADDED PROJECTILE")
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(50, 50)
            imageView.translationX = 2000f
            imageView.translationY = ((1..5).random() * 250f) + 50f

            imageView.setImageResource(R.drawable.sun)

            binding.GameLayout.addView(imageView)
            projectile.imageView = imageView
        }

        fun update(time: Int) {
            for (plant in plants) {
                plant.update(time)
            }
            for (zombie in zombies) {
                zombie.update(time)
            }
            sunDisplayValue.text = sun.toString()
            gameCounter += 1

            if (projectiles.indexOf(previousProjectile) != projectiles.lastIndex) {
                if (projectiles.indexOf(previousProjectile) != -1) {
                    for (index in projectiles.indexOf(previousProjectile)..(projectiles.lastIndex-1)) {
                        addProjectile(projectiles[index])
                    }
                }
                previousProjectile = projectiles.last()
            }
        }

        fun addZombie(lane : Int) {
            if ((lane >= 1) && (lane <= 5)) {
                val imageView = ImageView(this)
                imageView.layoutParams = LinearLayout.LayoutParams(200, 300)
                imageView.translationX = (2700 + (-25..25).random()).toFloat()
                imageView.translationY = (((lane - 1) * 250) + 50 + (-15..15).random()).toFloat()

                imageView.setImageResource(R.drawable.zombie)

                binding.GameLayout.addView(imageView)

                zombieCounter += 1
                val newZombie = Zombie(imageView, lane, zombieCounter)
                zombies.add(newZombie)
            }
        }

        fun checkCollisions() {
            for (plant in plants) {
                //Log.d("GameActivity", "Top Left Plant Position: ${plant.imageView.x} and ${plant.imageView.y}")
                for (zombie in zombies) {
                    if (zombie.lane == plant.lane) {
                        if ((zombie.imageView.x <= plant.imageView.x + 100) && (zombie.imageView.x >= plant.imageView.x - 100)) {
                            if (!zombie.isEating) {
                                if (plant.health > 0) {
                                   zombie.eat(plant)
                                }
                                else {
                                    plant.imageView.setImageResource(android.R.color.transparent)
                                    plants = plants.filter { (it != plant)} as MutableList<Plant>
                                }
                            }
                        }
                    }
                }
            }
        }

        fun gameLoop() {

            update(40)

            if (gameCounter%50 == 0) { // multiply this by 10
                addZombie((1..5).random())
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
